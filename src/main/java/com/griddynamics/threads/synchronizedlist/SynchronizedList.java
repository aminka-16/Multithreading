package com.griddynamics.threads.synchronizedlist;

public interface SynchronizedList<E> {

	public E get(int index);

	public E set(int index, E element);

	public E remove(int index);

	public int size();

	public boolean add(E e);
}
