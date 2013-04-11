import java.util.Vector;

import javax.swing.table.DefaultTableModel;

class MyDefaultTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public MyDefaultTableModel(Object data[][], Object columnNames[]) {
		super(data, columnNames);
	}

	public Vector<?> getColumnIdentifiers() {
		return columnIdentifiers;
	}

//	@Override
//	public Class<?> getColumnClass(int columnIndex) {
//		return String.class;
//	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}
}