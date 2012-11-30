package com.griddymanics.threads.philosophers.monitor;

import java.util.Random;
import java.util.concurrent.locks.Lock;

import com.griddymanics.threads.philosophers.Fork;

public class Philosopher implements Runnable {

	private final int id;
	private final Fork leftFork;
	private final Fork rightFork;
	private final Object lock;

	public Philosopher(int id, Fork leftFork, Fork rightFork, Object lock) {
		this.id = id;
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.lock = lock;
	}

	public void run() {
		boolean isInterupted = false;
		while (!isInterupted) {
			if (checkAvailableResources()) {
				startEating();
				finishEating();
				try {
//					Thread.sleep(new Random().nextInt(5000));
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
					isInterupted = true;
				}
			} else {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						isInterupted = true;
					}
				}
			}
		}
	}

	private boolean checkAvailableResources() {
		synchronized (lock) {
			if (!leftFork.isOccupied()) {
				leftFork.occupy();
				if (!rightFork.isOccupied()) {
					rightFork.occupy();
					return true;
				} else {
					leftFork.free();
					return false;
				}
			}
			return false;
		}
	}

	private void startEating() {
		System.out.printf("%d Philosopher %d starts eating\n", millis(), id);
		try {
			Thread.sleep(new Random().nextInt(10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void finishEating() {
		rightFork.free();
		leftFork.free();
		System.out.printf("%d Philosopher %d ends eating\n", millis(), id);
		synchronized (lock) {
			lock.notifyAll();
		}
		
	}

	private static long millis() {
		return System.currentTimeMillis() % (24*3600*1000l);
	}
	
	@Override
	public String toString() {
		return "Philosopher [id=" + id + ", leftFork=" + leftFork
				+ ", rightFork=" + rightFork + "]";
	}

}
