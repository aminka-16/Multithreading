package com.griddymanics.threads.philosophers;

import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static void main(String[] args) {
		final List<Fork> forks = new ArrayList<Fork>();
		for (int i = 1; i <= 5; i++) {
			Fork fork = new Fork(i);
			forks.add(fork);
		}
		List<Philosopher> philosophers = new ArrayList<Philosopher>() {
			{
				add(new Philosopher(1, forks.get(0), forks.get(4)));
				add(new Philosopher(2, forks.get(1), forks.get(0)));
				add(new Philosopher(3, forks.get(2), forks.get(1)));
				add(new Philosopher(4, forks.get(3), forks.get(2)));
				add(new Philosopher(5, forks.get(4), forks.get(3)));
			}
		};
	
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(philosophers.get(i));
			thread.start();
		}
	}
}
