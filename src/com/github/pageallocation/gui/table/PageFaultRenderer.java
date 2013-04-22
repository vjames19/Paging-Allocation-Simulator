package com.github.pageallocation.gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.github.pageallocation.util.Util;

/**
 * Renderer used to depict a Page Fault. If the current reference was not in the
 * previous column then a Page Fault occurred.
 * 
 * <p>
 * For this render to not depict the columns as a page fault, the header of the
 * table must be other than an integer. That way the renderer will not color any
 * column of the table.
 * 
 * <p>
 * It will only color columns with the header being an integer. If a page fault
 * occurs.
 * 
 * @author Victor J.
 * 
 */
public class PageFaultRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component renderer = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		boolean pageFault = pageFaultOccurred(
				(DefaultTableModel) table.getModel(), column);
		if (pageFault) {
			renderer.setBackground(Color.CYAN);
		} else {
			renderer.setBackground(Color.WHITE);
		}
		return renderer;
	}

	private boolean pageFaultOccurred(final DefaultTableModel model,
			final int column) {
		String columnName = model.getColumnName(column);

		if (column == 0) {// Frames column
			return false;
		}
		if (column == 1) {// First reference and its not in the initial state
			return Util.isInteger(columnName);

		} else if (column > 1 && Util.isInteger(columnName)) {

			int rows = model.getRowCount();
			if (isColumnEmpty(model, column, rows)) {
				return false;
			}
			/*
			 * Search the value in the past column. If the value is not in the
			 * past column then a page fault occurred.
			 */
			int searchColumn = column - 1;

			return (!columnContains(model, rows, searchColumn, columnName) && columnContains(
					model, rows, column, columnName));
		}

		return false;

	}

	private boolean columnContains(DefaultTableModel model, int rows,
			int column, String columnName) {
		for (int i = 0; i < rows; i++) {// Search the value
			Object value = model.getValueAt(i, column);
			if (value == null) {
				continue;
			} else {
				String v = (String) value;
				if (columnName.equals(v)) {// found it
					return true;
				}
			}
		}

		return false;

	}

	/**
	 * Verifies if a column is empty. Either all columns are "" or null.
	 * 
	 * @param model
	 * @param column
	 * @param rows
	 * @return
	 */
	private boolean isColumnEmpty(TableModel model, int column, int rows) {
		for (int i = 0; i < rows; i++) {
			Object val = model.getValueAt(i, column);
			if (val != null) {
				if (!((String) val).isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

}
