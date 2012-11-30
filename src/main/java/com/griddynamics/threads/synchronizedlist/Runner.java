package com.griddynamics.threads.synchronizedlist;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.griddynamics.threads.synchronizedlist.locks.SynchronizedListWithLocks;

public class Runner {
	
	private static final int COUNT_OF_READERS = 1000;

	private static final int COUNT_OF_WRITERS = 10;
	
	private static final int LOOP_COUNT = 1000;

	public static void execute(SynchronizedList list, String listName) {

		long time = System.currentTimeMillis();
		
		for (int i = 0; i < 10000; i++)
			list.add(i);

		AtomicLong totalReadMillis = new AtomicLong(0);
		AtomicLong totalWriteMillis = new AtomicLong(0);
	
		ExecutorService readers = Executors.newFixedThreadPool(COUNT_OF_READERS);
		
		ExecutorService writers = Executors.newFixedThreadPool(COUNT_OF_WRITERS);
		
		for (int i = 0; i < COUNT_OF_WRITERS; i++) {
			for (int j = 0; j < COUNT_OF_READERS / COUNT_OF_WRITERS; j++) {
				readers.submit(new Reader(list, totalReadMillis, LOOP_COUNT));
			}
			writers.submit(new Writer(list, totalWriteMillis, LOOP_COUNT));
		}

		readers.shutdown();
		writers.shutdown();
		try {
			readers.awaitTermination(1, TimeUnit.SECONDS);
			writers.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			
		}

		System.out.printf("%s readers: %d \n", listName, totalReadMillis.get());
		System.out.printf("%s writers: %d \n", listName, totalWriteMillis.get());
		System.out.println("total time: " + (System.currentTimeMillis() - time));
	}

	public static void main(String[] args) {

		SynchronizedList syncList = new SynchronizedListSimple();
		Runner.execute(syncList, "simple list");

		SynchronizedList lockedList = new SynchronizedListWithLocks();
		Runner.execute(lockedList, "locked list");

	}
}
