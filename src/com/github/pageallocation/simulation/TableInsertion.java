package com.github.pageallocation.simulation;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

import com.github.pageallocation.gui.PropertiesWindow;
import com.github.pageallocation.thread.PausableStopabbleThread;

/*
 * This class handles adding data to the JTables. We use this class
 * because it allows us to run data insertions for each JTable on a separate
 * thread, independent of the main program thread. This way, we can modify
 * the delay by calling the sleep() method. We allow the user to set the delay
 * so that they can see, in real time, the data being added to the table.
 */
public class TableInsertion extends PausableStopabbleThread {
	private JTable table;
	private DefaultTableModel model;
	private PropertiesWindow propWin;
	private final long sleepTime;
	private int frames, columns;
	private int[][] result;

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
	public TableInsertion(JTable t, DefaultTableModel m, PropertiesWindow p,
			int[][] r, int f, int c, int ms) {
		table = t;
		model = m;
		propWin = p;
		result = r;
		frames = f;
		columns = c;
		sleepTime = ms;
	}

	@Override
	public void run() {
		for (int i = 1; i < columns; i++) {
			for (int k = 0; k < frames; k++) {
				if (stopRequested()) {
					return;
				}
				scrollToVisible(table, k, i);

				if (result[i][k] == -1)
					table.getModel().setValueAt("X", k, i);
				else
					table.getModel().setValueAt("" + result[i][k], k, i);
				// table.getModel().setValueAt(+ result[i][k], k, i); changed to
				// string for the CellRenderer
				// TODO RETURN TO ORIGINAL VALUE A INTEGER
				try {
					if (!(propWin == null) && propWin.getDelay() > 0)
						sleep(propWin.getDelay());
					else
						sleep(sleepTime); // Default sleep time

					pausePoint();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
					System.out.println("Thread.sleep() throwing an exception.");
				}
				model.fireTableDataChanged();

			}
		}
	}

	/*
	 * Moves the table's scrollbar to show the current column and row having
	 * data added to it.
	 */
	private void scrollToVisible(JTable table, int row, int col) {
		if (!(table.getParent() instanceof JViewport))
			return;

		JViewport viewport = (JViewport) table.getParent();
		Rectangle rect = table.getCellRect(row, col, true);
		Point p = viewport.getViewPosition();

		rect.setLocation(rect.x - p.x, rect.y - p.y);
		viewport.scrollRectToVisible(rect);
	}

}