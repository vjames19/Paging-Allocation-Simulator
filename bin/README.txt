*************************************************
PAGE ALLOCATION SIMULATOR v1.00 README
By:
    Adam Childs - adchilds@eckerd.edu
    Shawn Craine - spcraine@eckerd.edu
    Dylan Meyer - meyerdw@eckerd.edu
*************************************************

The purpose of this application is to simulate different Operating System
paging allocation methods and to compare their results. The following allocation
schemes are used in this program:
    - FIFO (First In First Out)
    - LRU (Least Recently Used)
    - SC (Second-Chance)

Demand paging is the process of loading only needed data (data requested by some process)
into memory. Therefore, other data that the program does not currently need to execute is
not loaded into memory. This helps to conserve memory space for other processes to use. Since
demand paging continuously moves data from disk into memory, there is a need for an allocation
method. There have been many proposed allocation methods, in particular: FIFO (First In
First Out), LRU (Least Recently Used), SC (Second-Chance). These methods all have their
positives and their negatives. In order to examine which method is best for certain situations,
this program compares results of different demand paging allocation methods.

*************************************************
TO USE THIS APPLICATION:
*************************************************
Next to the 'Generate' button is a text field where the user may supply the total length of
a string of randomly generated numbers. This string contains 7 - 99 numbers within it. The user
may toggle the button to select how many randomly generated numbers they would like within the
string. On the other hand, the user may enter a string of their choice, thereby testing their
own string of numbers.

After generating or creating your own string, the user may press the 'Run' button which then
simulates the paging allocation methods (FIFO, LRU, SC) on the specified string. Alternatively,
the user may stop execution by pressing the 'Stop' button.

After pressing the 'Stop' button, the user may control the execution by stepping through each
process by pressing the 'Step' button.

To the right of each table the user may view how many page faults have occurred and the page
fault rate for each allocation method for the given input (string of numbers, frame count,
range of numbers in the string).

If the user would like to start over, they may click the reset button, which removes all values
anywhere on the screen, and resets the string size to 7, page frame number to 4, and the range
of randomly generated values to 0.