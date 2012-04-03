import java.util.Vector;

import javax.swing.table.DefaultTableModel;

class MyDefaultTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = 1L;

	public MyDefaultTableModel(Object data[][], Object columnNames[])
	{
		super(data, columnNames);
	}

	public Vector<?> getColumnIdentifiers()
	{
		return columnIdentifiers;
	}
}