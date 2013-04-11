package com.github.pageallocation;

import javax.swing.SwingUtilities;

import com.github.pageallocation.gui.UserInterface;

class AllocationApp {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new UserInterface();
			}
		});
	}

}