package com.github.pageallocation.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.github.pageallocation.algorithms.AllocationStrategy;
import com.github.pageallocation.gui.table.MyDefaultTableModel;
import com.github.pageallocation.gui.table.PageFaultRenderer;
import com.github.pageallocation.simulation.TableInsertion;

public class SimulationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	String algorithm;
	String tooltipText;
	JScrollPane sc;
	JLabel label;
	AllocationStrategy strategy;
	private JTextField faults;
	private JTextField faultRate;
	private TableInsertion tableInsertion;
	static String[] columnNames = { "Frames", "A", "B", "C", "D", "E", "F", "G" };
	static String[][] data = new String[4][8]; // Rows, Columns
	static {
		for (int i = 0; i < 4; i++)
			data[i][0] = "" + i;
	}
	private MyDefaultTableModel model;

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

		setModel(new MyDefaultTableModel(data, columnNames));
		setTable(new JTable(getModel()));
		getTable().setDefaultRenderer(Object.class, new PageFaultRenderer());
		getTable().setCellSelectionEnabled(false);
		getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// table.setAutoCreateColumnsFromModel(false);
		getTable().setEnabled(false);
		sc = new JScrollPane(getTable());
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

		setFaults(new JTextField());
		getFaults().setEditable(false);
		getFaults().setBackground(Color.LIGHT_GRAY);
		panelTop.add(getFaults(), BorderLayout.CENTER);

		JPanel panelBot = new JPanel();
		panelBot.setLayout(new BorderLayout());
		label = new JLabel("Fault Rate:");
		panelBot.add(label, BorderLayout.NORTH);

		setFaultRate(new JTextField());
		getFaultRate().setEditable(false);
		getFaultRate().setBackground(Color.LIGHT_GRAY);
		panelBot.add(getFaultRate(), BorderLayout.CENTER);

		panelTotal.add(panelTop, BorderLayout.NORTH);
		panelTotal.add(panelBot, BorderLayout.CENTER);

		return panelTotal;
	}

	public void clear() {
		getFaults().setText("");
		getFaultRate().setText("");
		getModel().setDataVector(data, columnNames);
		getModel().fireTableDataChanged();
	}

	public MyDefaultTableModel getModel() {
		return model;
	}

	public void setModel(MyDefaultTableModel model) {
		this.model = model;
	}

	public TableInsertion getTableInsertion() {
		return tableInsertion;
	}

	public void setTableInsertion(TableInsertion tableInsertion) {
		this.tableInsertion = tableInsertion;
	}

	public JTextField getFaults() {
		return faults;
	}

	public void setFaults(JTextField faults) {
		this.faults = faults;
	}

	public JTextField getFaultRate() {
		return faultRate;
	}

	public void setFaultRate(JTextField faultRate) {
		this.faultRate = faultRate;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

}
