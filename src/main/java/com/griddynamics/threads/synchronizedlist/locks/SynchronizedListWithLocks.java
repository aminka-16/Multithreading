package com.griddynamics.threads.synchronizedlist.locks;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.griddynamics.threads.synchronizedlist.SynchronizedList;

public class SynchronizedListWithLocks<E> implements SynchronizedList<E>{

	private final ArrayList<E> list;

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private final Lock readLock = rwl.readLock();

	private final Lock writeLock = rwl.writeLock();

	public SynchronizedListWithLocks() {
		this.list = new ArrayList<E>();
	}

	public E get(int index) {
		readLock.lock();
		try {
			return list.get(index);
		} finally {
			readLock.unlock();
		}
	}

	public E set(int index, E element) {
		writeLock.lock();
		try {
			return list.set(index, element);
		} finally {
			writeLock.unlock();
		}
	}

	public E remove(int index) {
		writeLock.lock();
		try {
			return list.remove(index);
		} finally {
			writeLock.unlock();
		}
	}

	public int size() {
		readLock.lock();
		try {
			return list.size();
		} finally {
			readLock.unlock();
		}
	}

	public boolean add(E e) {
		writeLock.lock();
		try {
			return list.add(e);
		} finally {
			writeLock.unlock();
		}
	}
}
