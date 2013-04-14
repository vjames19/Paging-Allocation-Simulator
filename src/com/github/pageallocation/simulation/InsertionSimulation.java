package com.github.pageallocation.simulation;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

public class InsertionSimulation implements Simulation {
	private JTable table;
	private DefaultTableModel model;
	private int frames, columns;
	private int[][] result;
	private long numberOfSteps;
	private int i = 1, k = 0;

	/**
	 * When this thread is started, it inserts the specified data into the
	 * specified JTable at the specified speed (in milliseconds).
	 * 
	 * @param t
	 *            the JTable to insert the data into
	 * @param m
	 *            the DefaultTableModel that needs to display the changed data
	 * @param p
	 *            the PropertiesWindow where we get the user's specified delay
	 *            from
	 * @param r
	 *            the 2d array where our data is stored
	 * @param f
	 *            the number of frames the user has selected
	 * @param c
	 *            the number of columns the user has selected
	 * @param ms
	 *            the delay (in ms)
	 */

	public InsertionSimulation(JTable t, DefaultTableModel m) {
		this(t, m, null, 0, 0);
	}

	public InsertionSimulation(JTable t, DefaultTableModel m, int[][] r, int f,
			int c) {
		table = t;
		model = m;
		setParams(r, f, c);

	}

	/*
	 * Moves the table's scrollbar to show the current column and row having
	 * data added to it.
	 */
	private static void scrollToVisible(JTable table, int row, int col) {
		if (!(table.getParent() instanceof JViewport))
			return;

		JViewport viewport = (JViewport) table.getParent();
		Rectangle rect = table.getCellRect(row, col, true);
		Point p = viewport.getViewPosition();

		rect.setLocation(rect.x - p.x, rect.y - p.y);
		viewport.scrollRectToVisible(rect);
	}

	public synchronized boolean hasMoreSteps() {
		System.out.println("InsertionSimulation.hasMoreSteps() " + numberOfSteps);
		return numberOfSteps > 0;
	}

	public synchronized void step() {
		if (hasMoreSteps()) {

			scrollToVisible(table, k, i);

			if (result[i][k] == -1)
				table.getModel().setValueAt("X", k, i);
			else
				table.getModel().setValueAt("" + result[i][k], k, i);
			// table.getModel().setValueAt(+ result[i][k], k, i); changed to
			// string for the CellRenderer
			if (k == frames - 1) {
				k = 0;
				i++;
			} else {
				k++;
			}

			numberOfSteps--;
			System.out.printf("i %d k %d %s", i, k, table);
			model.fireTableDataChanged();
		}

	}

	public synchronized void setParams(int[][] r, int f, int c) {
		result = r;
		frames = f;
		columns = c;
		numberOfSteps = (columns - 1) * frames;
		i =1;
		k = 0;
	}

	public synchronized void clearParams() {
		setParams(null, 0, 0);
	}

}
