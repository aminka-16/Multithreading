package com.griddynamics.threads.synchronizedlist;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Reader implements Runnable {

	private AtomicLong totalReadMillis;

	private SynchronizedList list;

	private int loopCount;
	
	public Reader(SynchronizedList list, AtomicLong totalReadMillis, int loopCount) {
		this.list = list;
		this.totalReadMillis = totalReadMillis;
		this.loopCount = loopCount;
	}

	@Override
	public void run() {
		for (int i = 0; i < loopCount; i++) {
			long time = System.currentTimeMillis();
			list.get(new Random().nextInt(list.size()));
			time = System.currentTimeMillis() - time;
			long old = 0;
			do {
				old = totalReadMillis.get();
			} while (!totalReadMillis.compareAndSet(old, time + old));
		}
	}
}
