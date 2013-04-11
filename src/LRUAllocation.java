
import java.util.Arrays;

public class LRUAllocation implements AllocationStrategy {
	private int page_faults;

	// Constructor.
	public LRUAllocation() {
	}

	// Returns the two-dimensional array of allocated frames.
	public int[][] allocation(int[] references, int frames) {
		int[][] allocation = allocate_frames(references, frames);
		// add blank column at front row to coincide with other algorithms
		int[][] temp = new int[references.length + 1][frames];

		for (int i = 0; i < frames; i++) {
			temp[0][i] = -1;
		}

		for (int i = 1; i <= references.length; i++) {
			for (int j = 0; j < frames; j++) {
				temp[i][j] = allocation[i - 1][j];
			}
		}

		return temp;
	}

	// Returns the two-dimensional array of allocated frames.
	public int faults() {
		return page_faults;
	}

	public double faultRate(int references, int frames) {
		return ((double) page_faults / references) * 100;
	}

	// Performs the LRU page allocation algorithm.
	private int[][] allocate_frames(int[] references, int frames) {
		int[][] current_frames = new int[references.length][frames];
		int[] frame_age = new int[frames];

		// Initialize all frame values to -1.
		for (int c = 0; c < references.length; c++) {
			Arrays.fill(current_frames[c], -1);
		}
		log("All frames initialized to -1...");

		// Iterate through each reference handling page faults.
		for (int i = 0; i < references.length; i++) {
			int empty_frame = -1;
			int page_reference = references[i];

			// Base case - Always a page fault.
			if (i == 0) {
				current_frames[0][0] = page_reference;
				log("base case: " + page_reference + " inserted at 0x0.");
				page_faults++;
			} else {

				boolean no_page_fault = false;
				// Loop through our previous frames checking for our current
				// page reference.
				// If it is there then there is no page fault and we can
				// continue to the next page reference.
				for (int a = 0; a < frames; a++) {
					if (current_frames[i - 1][a] == page_reference) {
						current_frames[i] = current_frames[i - 1];
						frame_age[a] = 0;
						log("No page fault occurred, " + page_reference
								+ " is already allocated to frame " + a + ".");
						log("Frame " + a + "'s age has been reset.");
						no_page_fault = true;
					}
				}

				if (!no_page_fault) {
					// Check the age of all the current frames.
					// If any equal 0 then set 'empty_frame' to the index and
					// break the loop.
					for (int n = 0; n < frame_age.length; n++) {
						if (frame_age[n] == 0) {
							empty_frame = n;
							break;
						}
					}

					// Check to see if we found any empty frames.
					if (empty_frame != -1) {
						// We found a free frame to add and its index is stored
						// within 'empty_frame'.
						System.arraycopy(current_frames[i - 1], 0,
								current_frames[i], 0, frames);
						current_frames[i][empty_frame] = page_reference;
						log(page_reference
								+ " was inserted into an empty frame. (frame "
								+ empty_frame + ")");

					} else {
						// There were no empty frames available, now we must
						// figure out which frame to replace.
						int max_age = 0;
						int replace_frame = 0;

						// Find the frame that hasn't been used in the longest
						// time.
						for (int b = 0; b < frame_age.length; b++) {
							if (frame_age[b] >= max_age) {
								max_age = frame_age[b];
								replace_frame = b;
							}
						}

						// Replace that frame with the new page reference and
						// set it's age to 0.
						log("Frame "
								+ replace_frame
								+ " is least recently used and should be replaced.");
						System.arraycopy(current_frames[i - 1], 0,
								current_frames[i], 0, frames);
						current_frames[i][replace_frame] = page_reference;
						frame_age[replace_frame] = 0;
						log(current_frames[i - 1][replace_frame]
								+ " was replaced by " + page_reference
								+ " at frame " + replace_frame);
					}
					page_faults++;
				}
			}

			// Iterate through current_frames and increment all necessary frame
			// ages.
			for (int j = 0; j < current_frames[i].length; j++) {
				if (current_frames[i][j] != -1) {
					frame_age[j]++;
					// log("Frame "+j+"'s age incremented to "+frame_age[j]);
				}
			}
		}

		return current_frames;
	}

	// Logs all swaps if debug mode is on.
	public void log(String string) {
		// if (DEBUG_MODE)
		// System.out.println(string);
		// TODO delete
	}

	@Override
	public void clearStats() {
		page_faults = 0;

	}
}
