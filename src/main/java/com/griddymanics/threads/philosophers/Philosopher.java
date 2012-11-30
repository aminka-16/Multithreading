package com.griddymanics.threads.philosophers;

import java.util.Random;

public class Philosopher implements Runnable {

	private final int id;
	private final Fork leftFork;
	private final Fork rightFork;

	public Philosopher(int id, Fork leftFork, Fork rightFork) {
		this.id = id;
		this.leftFork = leftFork;
		this.rightFork = rightFork;
	}

	public void run() {
		boolean isInterupted = false;
		while (!isInterupted) {
			if (checkAvailableResources()) {
				startEating();
				finishEating();
			} else {
				try {
					Thread.sleep(new Random().nextInt(5));
				} catch (InterruptedException e) {
					e.printStackTrace();
					isInterupted = true;
				}
			}
		}
	}

	private boolean checkAvailableResources() {
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
	
	private void startEating() {
		System.out.printf("Philosopher %d starts eating\n", id);
		try {
			Thread.sleep(new Random().nextInt(50));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void finishEating() {
		rightFork.free();
		leftFork.free();
		System.out.printf("Philosopher %d ends eating\n", id);
		try {
			Thread.sleep(new Random().nextInt(50));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Philosopher [id=" + id + ", leftFork=" + leftFork
				+ ", rightFork=" + rightFork + "]";
	}

}
