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

	public boolean hasMoreSteps() {
		return numberOfSteps > 0;
	}

	public void step() {
		if (hasMoreSteps()) {

			scrollToVisible(table, k, i);

			if (result[i][k] == -1)
				table.getModel().setValueAt("X", k, i);
			else
				table.getModel().setValueAt("" + result[i][k], k, i);

			if (k == frames - 1) {
				k = 0;
				i++;
			} else {
				k++;
			}

			numberOfSteps--;
			model.fireTableDataChanged();
		}

	}

	public void setParams(int[][] r, int f, int c) {
		result = r;
		frames = f;
		columns = c;
		numberOfSteps = (columns - 1) * frames;
		i = 1;
		k = 0;
	}

	public void clearParams() {
		setParams(null, 0, 0);
	}

}
