Paging-Allocation-Simulator
===========================

Simulates Demand Paging done by an operating system.

Aimed to be a teaching tool in which you can add a page allocation algorithm and simulate it.
*Still needs to work on how to plug a new algorithm into the GUI*

To compile this application you will need JDK 7




## Simulator Readme
======================================

The purpose of this application is to simulate different Operating System
paging allocation methods and to compare their results. The following allocation
schemes are used in this program:
    1. FIFO (First In First Out)
    2. OPT (Optimal Algorithm)
    3. LRU (Least Recently Used)

Demand paging is the process of loading only needed data (data requested by some process)
into memory. Therefore, other data that the program does not currently need to execute is
not loaded into memory. This helps to conserve memory space for other processes to use. Since
demand paging continuously moves data from disk into memory, there is a need for an allocation
method. There have been many proposed allocation methods, in particular: FIFO (First In
First Out), LRU (Least Recently Used), SC (Second-Chance). These methods all have their
positives and their negatives. In order to examine which method is best for certain situations,
this program compares results of different demand paging allocation methods.


### TO USE THIS APPLICATION:

Next to the 'Generate' button is a text field where the user may supply the total length of
a string of randomly generated numbers. This string contains 7 - 99 numbers within it. The user
may toggle the button to select how many randomly generated numbers they would like within the
string. On the other hand, the user may enter a string of their choice, thereby testing their
own string of numbers. *The string entered must be separeted by commas*

After generating or creating your own string, the user may use the control buttons namely:

<table>
<tr>
<th>Button</th>
<th>Shortcut</th>
</tr>
<tr>
<td>Run</td>
<td>CTRL+R</td>
</tr>
<tr>
<td>Step</td>
<td>CTRL+S</td>
</tr>
<tr>
<td>Pause</td>
<td>None</td>
</tr>
<tr>
<td>Reset</td>
<td>CTRL+Z</td>
</tr>
</table>

**To control the simulation's execution.**

The simulation consists of a table and its respective allocation algorithm. Each column represents 
state of the frames at the time the reference its made.

A page fault its represented by coloring a column in which the page fault happened. 

A cell containing an 'X' is equal to a frame that has not been allocated.
 
To the right of each table the user may view how many page faults have occurred and the page
fault rate for each allocation method for the given input (string of numbers, frame count,
range of numbers in the string).
