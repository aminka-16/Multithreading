package com.griddynamics.threads.increment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Runner {

	public static void main(String[] args) {

		final Incrementor inc = new Incrementor();
		final int numberOfThreads = 1000;
		final int numberOfIterations = 10000;
		ExecutorService threads = Executors.newFixedThreadPool(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			threads.submit(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < numberOfIterations; i++)
						inc.getNewId();
				}
			});
		}
		threads.shutdown();
		try {
			threads.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		long expected = numberOfThreads * numberOfIterations;
		long actual = inc.getLastId();
		System.out.printf("%s Expected number: %d, actual number: %d", expected == actual ? "SUCCESS" : "FAILURE", expected, actual);
	}
	
}
