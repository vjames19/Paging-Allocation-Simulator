package com.github.pageallocation.algorithms;

//This class was created to support the implementation of the First-In-First-Out and
//Second-Chance paging allocation algorithms for Operating Systems. It was create

class Queue {
	// Variables
	private int[] arr;
	private int nElems;
	private int front;
	private int rear;

	// Constructor
	public Queue(int sz) {
		arr = new int[sz];
		nElems = 0;
		front = 0;
		rear = -1;
	}

	// This function inserts an element at the end of the Queue
	public void insert(int i) {
		if (isFull()) {
			System.out.println("1 QUEUE IS FULL");
			return;
		}

		else if (rear != arr.length - 1) {
			arr[rear + 1] = i;
			rear++;
		}

		else {
			arr[0] = i;
			rear = 0;
		}

		nElems++;
	}

	// This function removes and element from the beginning of the Queue
	public void remove() {
		if (isEmpty()) {
			System.out.println("1 QUEUE IS EMPTY");
			return;
		}

		else if (front != arr.length - 1)
			front++;

		else
			front = 0;

		nElems--;
	}

	// This function search for a particular value in the Queue
	public boolean search(int element) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == element) {
				return true;
			}
		}

		return false;
	}

	// This method returns the location of an element in the Queue
	// It is created for searchng for values in the Queue, replacement
	// in SC_Allocation.java
	public int find(int element) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == element) {
				return i;
			}
		}

		return -1;
	}

	// This method updates the value of a reference's dirty bit
	public void updateDirty(int location, int value) {
		arr[location] = value;
	}

	public int elementAt(int location) {
		return arr[location];
	}

	// This method remakes the dirty Queue to all zeros.
	public Queue makeZero(int frames) {
		Queue temp = new Queue(frames);

		for (int i = 0; i < frames; i++) {
			temp.insert(0);
		}

		return temp;
	}

	// This method finds the location of the first dirty bit with a
	// value of 0, to determine what element should be replaced.
	// Only used for the dirty Queue in SC_Allocation
	public int findReplacement(int frames) {
		int replace = -1;

		for (int i = 0; i < frames; i++) {
			if (arr[i] == 0) {
				replace = i;
				break;
			}

			else if (i == frames - 1) {
				replace = -1;
			}

		}

		return replace;
	}

	// This method is used for removing an element at a specific location
	// within and returning the updated Queue.
	public Queue updateQueue(Queue replace, int location, int frames) {
		Queue temp = new Queue(frames);

		for (int i = 0; i < frames; i++) {
			if (i == location) {
				replace.remove();
			}

			else {
				temp.insert(replace.front());
				replace.remove();
			}
		}

		return temp;
	}

	// Returns the element at the front of the array.
	public int front() {
		return arr[front];
	}

	// Checks to see if the Queue is full
	public boolean isFull() {
		return (nElems == arr.length);
	}

	// Checks to see if the Queue is empty
	public boolean isEmpty() {
		return (nElems == 0);
	}

}