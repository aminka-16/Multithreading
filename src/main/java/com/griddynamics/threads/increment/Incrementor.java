package com.griddynamics.threads.increment;

import java.util.concurrent.atomic.AtomicLong;

public class Incrementor {

	private AtomicLong id = new AtomicLong(0);

	public long getNewId() {
		for (;;) {
			long old = id.get();
			long next = old + 1;
			if (id.compareAndSet(old, next))
				return next;
		}
	}
	
	public long getLastId() {
		return id.get();
	}

}
