package com.griddymanics.threads.philosophers.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.griddymanics.threads.philosophers.Fork;
import com.griddymanics.threads.philosophers.monitor.Philosopher;

public class Runner {

	public static void main(String[] args) {
		final List<Fork> forks = new ArrayList<Fork>();
		for (int i = 1; i <= 5; i++) {
			Fork fork = new Fork(i);
			forks.add(fork);
		}
		final Object lock = new Object();
		ExecutorService pool = Executors.newFixedThreadPool(5);
		pool.submit(new Philosopher(1, forks.get(0), forks.get(4), lock));
		pool.submit(new Philosopher(2, forks.get(1), forks.get(0), lock));
		pool.submit(new Philosopher(3, forks.get(2), forks.get(1), lock));
		pool.submit(new Philosopher(4, forks.get(3), forks.get(2), lock));
		pool.submit(new Philosopher(5, forks.get(4), forks.get(3), lock));
	}
}
