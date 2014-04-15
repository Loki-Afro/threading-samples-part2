package com.github.loki.afro.threading;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {

	static class MaximumFinder extends RecursiveTask<Integer> {

		private static final long serialVersionUID = 2614100472943047942L;

		private static final int SEQUENTIAL_THRESHOLD = 5;

		private final int[] data;
		private final int start;
		private final int end;

		public MaximumFinder(final int[] data, final int start, final int end) {
			this.data = data;
			this.start = start;
			this.end = end;
		}

		public MaximumFinder(final int[] data) {
			this(data, 0, data.length);
		}

		@Override
		protected Integer compute() {
			final int length = this.end - this.start;
			if (length < SEQUENTIAL_THRESHOLD) {
				return computeDirectly();
			}
			final int split = length / 2;
			final MaximumFinder left = new MaximumFinder(this.data, this.start,
					this.start + split);
			left.fork();
			final MaximumFinder right = new MaximumFinder(this.data, this.start
					+ split, this.end);
			return Math.max(right.compute(), left.join());
		}

		private Integer computeDirectly() {
			System.out.println(Thread.currentThread() + " computing: "
					+ this.start + " to " + this.end);
			int max = Integer.MIN_VALUE;
			for (int i = this.start; i < this.end; i++) {
				if (this.data[i] > max) {
					max = this.data[i];
				}
			}
			return max;
		}
	}

	public static void main(final String[] args) {
		// create a random data set
		final int[] data = new int[100];
		final Random random = new Random();
		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt(1000);
		}

		// submit the task to the pool
		final ForkJoinPool pool = new ForkJoinPool(4);
		final MaximumFinder finder = new MaximumFinder(data);
		System.out.println(pool.invoke(finder));
	}
}