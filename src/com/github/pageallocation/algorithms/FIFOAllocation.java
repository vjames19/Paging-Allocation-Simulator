package com.github.pageallocation.algorithms;

/* 
 * This class implements a First-In-First-Out algorithm for page allocation. It requires the
 * input of an array of integers that represent the page references along with an integer
 * that represents the number of available frames in the system.
 * 
 * This algorithm will return a 2D array of integers that represent the frame allocation after
 * each page reference is made. It is also capable of returning the number of page faults that
 * occurred during a specific run through the algorithm or at any point during it. 
 */
public class FIFOAllocation extends AbstractStrategy {

	// The primary method performs all the algorithmic replacements and fills a
	// 2D array
	@Override
	public int[][] allocation() {

		// A 2D array is created to hold all the references for the entire
		// algorithm.
		// This will be returned to the main program.
		int[][] allocation = new int[references.length + 1][frames];

		// A Queue is created to hold what element should be replaced at any
		// particular time.
		Queue replacement = new Queue(frames);

		// The 2D array is initialized to be filled with -1 so that the program
		// can tell
		// which of the frames have been allocated and which have not.
		for (int l = 0; l <= references.length; l++) {
			for (int m = 0; m < frames; m++) {
				allocation[l][m] = -1;
			}
		}

		// The following nested for loops perform the FIFO algorithm. The first
		// one
		// runs through for as long as there are references available. The
		// second one
		// runs through all the possible frame for each reference.
		for (int i = 1; i <= references.length; i++) {
			for (int k = 0; k < frames; k++) {

				// If a frame is found to be empty, then the reference is placed
				// in the frame,
				// the reference is added to the queue, a fault is added to the
				// total, and the
				// algorithm moves to the next reference.
				if (allocation[i - 1][k] == -1) {
					allocation[i][k] = references[i - 1];
					replacement.insert(references[i - 1]);
					pageFault++;
					break;
				}

				// If the reference is found to be in the frames already then
				// all the pages in
				// the frames are kept the same by just copying them over from
				// the previous reference.
				if (allocation[i - 1][k] == references[i - 1]) {
					allocation[i][k] = allocation[i - 1][k];
					for (int j = k; j < frames; j++) {
						allocation[i][j] = allocation[i - 1][j];
					}
					break;
				}

				// If neither the element nor an empty frame are found, then the
				// page from the
				// previous reference is copied over so that the frame values
				// remain the same.
				if (allocation[i - 1][k] != references[i - 1]
						&& allocation[i - 1][k] != -1) {
					allocation[i][k] = allocation[i - 1][k];
				}

				// If the reference is not found by the last available frame and
				// the reference is
				// not found anywhere in the queue (is not in any other frame),
				// then the element
				// at the front of the queue is replaced with the reference. The
				// replaced element
				// is removed from the queue and the reference is added to it.
				if (k == frames - 1
						&& replacement.search(references[i - 1]) == false) {
					int e = replacement.front();
					pageFault++;
					for (int j = 0; j < frames; j++) {
						if (allocation[i][j] == e) {
							allocation[i][j] = references[i - 1];
							replacement.remove();
							replacement.insert(references[i - 1]);
						}
					}
				}
			}
		}

		return allocation;
	}

}