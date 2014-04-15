package de.traso.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExample {
    static class WorkerThread implements Runnable {

        private final String command;

        public WorkerThread(final String command) {
            this.command = command;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Start. Command = " + this.command);
            processCommand();
            System.out.println(Thread.currentThread().getName() + " End.");
        }

        private void processCommand() {
            try {
                TimeUnit.MILLISECONDS.sleep(Util.getARandomLongToSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(final String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread(String.valueOf(i));
            executor.execute(worker);
          }
        System.out.println("Requesting shutdown");
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("Finished all threads");
    }
}
