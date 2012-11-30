package com.griddynamics.threads.synchronizedlist;

import java.util.ArrayList;

public class SynchronizedListSimple<E> implements SynchronizedList<E>{

	private final ArrayList<E> list;

	public SynchronizedListSimple() {
		this.list = new ArrayList<E>();
	}

	public synchronized E get(int index) {
		return list.get(index);
	}

	public synchronized E set(int index, E element) {
		return list.set(index, element);
	}

	public synchronized E remove(int index) {
		return list.remove(index);
	}

	public synchronized int size() {
		return list.size();
	}

	public synchronized boolean add(E e) {
		return list.add(e);
	}
}
