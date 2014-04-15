package de.traso.threading;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExmaple {

    static class Producer implements Runnable {

        private final BlockingQueue<String> drop;
        List<String>                        messages = Arrays.asList(
                                                             "Mares eat oats",
                                                             "Does eat oats",
                                                             "Little lambs eat ivy",
                                                             "Wouldn't you eat ivy too?");

        public Producer(final BlockingQueue<String> d) {
            this.drop = d;
        }

        @Override
        public void run() {
            try {
                for (String s : this.messages) {
                    this.drop.put(s);
                }
                this.drop.put("DONE");
            } catch (InterruptedException intEx) {
                System.out.println("Interrupted! Last one out, turn out the lights!");
            }
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue<String> drop;

        public Consumer(final BlockingQueue<String> d) {
            this.drop = d;
        }

        @Override
        public void run() {
            try {
                String msg = null;
                while (!((msg = this.drop.take()).equals("DONE"))) {
                    System.out.println(msg);
                }
            } catch (InterruptedException intEx) {
                System.out.println("Interrupted! Last one out, turn out the lights!");
            }
        }
    }

    public static void main(final String[] args) {
        BlockingQueue<String> drop = new ArrayBlockingQueue<String>(1, true);
        new Thread(new Producer(drop)).start();
        new Thread(new Consumer(drop)).start();
    }
}
