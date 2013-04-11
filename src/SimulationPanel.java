import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SimulationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
	String algorithm;
	String tooltipText;
	JScrollPane sc;
	JLabel label;
	AllocationStrategy strategy;
	JTextField faults, faultRate;
	TableInsertion tableInsertion;
	static String[] columnNames = { "Frames", "A", "B", "C", "D", "E", "F", "G" };
	static String[][] data = new String[4][8]; // Rows, Columns
	static {
		for (int i = 0; i < 4; i++)
			data[i][0] = "" + i;
	}
	MyDefaultTableModel model;

	public SimulationPanel(String algorithmName, String tooltipText,
			AllocationStrategy strategy) {
		init(algorithmName, tooltipText, strategy);

	}

	private void init(String algorithmName, String toolTipText,
			AllocationStrategy strategy) {
		this.algorithm = algorithmName;
		this.strategy = strategy;

		label = new JLabel(algorithmName);
		label.setToolTipText(tooltipText);
		label.setPreferredSize(new Dimension(50, 25));
		this.add(label);

		model = new MyDefaultTableModel(data, columnNames);
		table = new JTable(model);
		table.setDefaultRenderer(Object.class, new PageFaultRenderer());
		table.setCellSelectionEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// table.setAutoCreateColumnsFromModel(false);
		table.setEnabled(false);
		sc = new JScrollPane(table);
		sc.setPreferredSize(new Dimension(600, 98));
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(sc);
		this.add(pageFaultsDisplay(), BorderLayout.EAST);

	}

	private JPanel pageFaultsDisplay() {
		JLabel label;
		JPanel panelTotal = new JPanel();
		panelTotal.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());

		label = new JLabel("Page Faults:");
		panelTop.add(label, BorderLayout.NORTH);

		faults = new JTextField();
		faults.setEditable(false);
		faults.setBackground(Color.LIGHT_GRAY);
		panelTop.add(faults, BorderLayout.CENTER);

		JPanel panelBot = new JPanel();
		panelBot.setLayout(new BorderLayout());
		label = new JLabel("Fault Rate:");
		panelBot.add(label, BorderLayout.NORTH);

		faultRate = new JTextField();
		faultRate.setEditable(false);
		faultRate.setBackground(Color.LIGHT_GRAY);
		panelBot.add(faultRate, BorderLayout.CENTER);

		panelTotal.add(panelTop, BorderLayout.NORTH);
		panelTotal.add(panelBot, BorderLayout.CENTER);

		return panelTotal;
	}

	public void clear() {
		faults.setText("");
		faultRate.setText("");
		model.setDataVector(data, columnNames);
		model.fireTableDataChanged();
	}

}
