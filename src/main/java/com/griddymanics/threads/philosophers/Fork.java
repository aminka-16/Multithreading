package com.griddymanics.threads.philosophers;

public class Fork {

	private volatile boolean occupied;

	private int id;

	public Fork(int id) {
		this.id = id;
	}

	public void occupy() {
		occupied = true;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void free() {
		occupied = false;
	}

	@Override
	public String toString() {
		return "Fork [occupied=" + occupied + ", id=" + id + "]";
	}
}
