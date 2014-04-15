package de.traso.threading;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExample2 {
//    implements java.util.concurrent.Callable now
    static class WorkerThread implements Callable<String> {

        private final String command;

        public WorkerThread(final String command) {
            this.command = command;
        }

//        method signature changed from run to call
        @Override
        public String call() {
            System.out.println(Thread.currentThread().getName() + " Start. Command = " + this.command);
            processCommand();
            System.out.println(Thread.currentThread().getName() + " End.");
//            returns command + random boolean
            return this.command + "-" + String.valueOf(new Random().nextBoolean());
        }

        private void processCommand() {
            try {
                TimeUnit.MILLISECONDS.sleep(Util.getARandomLongToSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(final String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
//            some new magic
        Callable<String> worker1 = new WorkerThread("1");
        Callable<String> worker2 = new WorkerThread("2");
//        not that we call submit, not execute.
        Future<String> future1 = executor.submit(worker1);
        Future<String> future2 = executor.submit(worker2);

        System.out.println("Future2: " + future2.get());
        System.out.println("Future1: " + future1.get());

        System.out.println("Requesting shutdown");
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("Finished all threads");
    }
}
