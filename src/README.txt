*************************************************
PAGE ALLOCATION SIMULATOR v2.00 README
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

After generating or creating your own string, the user may use the control buttons namely:
    Button:      Shortcut:
    *Run         CTRL+R
    *Pause         
    *Step        CTRL+S
    *Reset       CTRL+Z

To control the simulation's execution.

The simulation consists of a table and its respective allocation algorithm. Each column represents 
state of the frames at the time the reference its made.

A page fault its represented by coloring a column in which the page fault happened. 

A cell containing an 'X' is equal to a frame that has not been allocated.
 
To the right of each table the user may view how many page faults have occurred and the page
fault rate for each allocation method for the given input (string of numbers, frame count,
range of numbers in the string).

