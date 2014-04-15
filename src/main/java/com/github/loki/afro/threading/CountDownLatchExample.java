package com.github.loki.afro.threading;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

	private static final int NUMBER_OF_TASKS = 5;

	static class Worker implements Runnable {

		private final CountDownLatch countDownLatch;

		public Worker(final CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(Util.getARandomLongToSleep()); // sleep random time
															// to simulate long
															// running task
				System.out.println("Counting down: "
						+ Thread.currentThread().getName());
				this.countDownLatch.countDown();
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	static class WorkManager {
		private final CountDownLatch countDownLatch;

		public WorkManager() {
			this.countDownLatch = new CountDownLatch(NUMBER_OF_TASKS);
		}

		public void finishWork() {
			try {
				System.out.println("START WAITING");
				this.countDownLatch.await();
				System.out.println("DONE WAITING");
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}

		public void startWork() {
			for (int i = 0; i < NUMBER_OF_TASKS; i++) {
				new Thread(new Worker(this.countDownLatch)).start();
			}
		}
	}

	public static void main(final String[] args) {
		WorkManager workManager = new WorkManager();
		System.out.println("START WORK");
		workManager.startWork();
		System.out.println("WORK STARTED");
		workManager.finishWork();
		System.out.println("FINISHED WORK");
	}
}
