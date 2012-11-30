package com.griddynamics.threads.synchronizedlist;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Writer implements Runnable {

	private AtomicLong totalWriteMillis;

	private SynchronizedList list;
	
	private int loopCount;

	public Writer(SynchronizedList list, AtomicLong totalWriteMillis, int loopCount) {
		this.list = list;
		this.totalWriteMillis = totalWriteMillis;
		this.loopCount = loopCount;
	}

	@Override
	public void run() {
		for (int i = 0; i < loopCount; i++) {
			long time = System.currentTimeMillis();
			list.set(new Random().nextInt(list.size()),	new Random().nextInt(1000));
			list.remove(new Random().nextInt(list.size()));
			time = System.currentTimeMillis() - time;
			long old = 0;
			do {
				old = totalWriteMillis.get();
			} while (!totalWriteMillis.compareAndSet(old, time + old));
		}
	}
}
