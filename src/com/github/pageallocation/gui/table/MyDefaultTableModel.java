package com.github.pageallocation.gui.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public MyDefaultTableModel(Object data[][], Object columnNames[]) {
		super(data, columnNames);
	}

	public Vector<?> getColumnIdentifiers() {
		return columnIdentifiers;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}