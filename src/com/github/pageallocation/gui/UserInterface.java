package com.github.pageallocation.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.github.pageallocation.algorithms.AllocationStrategy;
import com.github.pageallocation.algorithms.FIFOAllocation;
import com.github.pageallocation.gui.table.MyDefaultTableModel;
import com.github.pageallocation.simulation.SimulationManager;
import com.github.pageallocation.simulation.TableInsertion;

/*
 * This class holds the foundation of the program's Graphical
 * User Interface. The GUI is set up and displayed from this class.
 * This class also calls other classes and their methods in order to
 * display our data (such as our page allocations).
 */
public class UserInterface extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame f;
	private Container contentPane;
	private JTextArea randStrArea;
	private SpinnerNumberModel strLengthModel, frameSpinnerModel,
			rangeSpinnerModel;
	private PropertiesWindow propWin;
	private List<SimulationPanel> simulations = new ArrayList<>(3);
	private SimulationManager simManager;

	// Program Variables
	private String version = "1.00"; // v1.00 (release date)

	public UserInterface() {
		createSimulations();
		simManager = new SimulationManager(simulations);
		f = new JFrame();
		f.setTitle("Page Allocation Simulator"); // Set the title of the JFrame
		contentPane = f.getContentPane();

		// Add the components to the JFrame
		f.setJMenuBar(menuBar()); // Add the JMenuBar to the JFrame
		contentPane.add(northPanel(), BorderLayout.NORTH);
		contentPane.add(centerPanel(), BorderLayout.CENTER);

		if (getOS().contains("win"))
			f.setSize(780, 560); // Set the size of the JFrame (width, height)
		else
			f.setSize(800, 560); // Set the size of the JFrame (width, height)
		f.setIconImage(new ImageIcon(getClass().getResource(
				"images/small_icon.png")).getImage());
		f.setLocation(setFrameCentered()); // Set the location of the JFrame on
											// the screen
		f.setResizable(true); // Frame cannot be resized
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End all java
															// processes when
															// the program is
															// closed
		f.setVisible(true); // JFrame must be visible to see it
	}

	private void createSimulations() {
		simulations.add(new SimulationPanel("FIFO", "First in First Out",
				new FIFOAllocation()));
		// simulations.add(new SimulationPanel("SC", "Second-chance", new
		// SecondChanceAllocation()));
		// simulations.add(new SimulationPanel("LRU", "Least Recently Used", new
		// LRUAllocation()));
		// simulations.add(new SimulationPanel("FIFO", "First in First Out", new
		// FIFOAllocation()));
		// simulations.add(new SimulationPanel("FIFO", "First in First Out", new
		// FIFOAllocation()));
		// simulations.add(new SimulationPanel("FIFO", "First in First Out", new
		// FIFOAllocation()));
		// simulations.add(new SimulationPanel("FIFO", "First in First Out", new
		// FIFOAllocation()));

	}

	private JPanel northPanel() {
		JPanel p = new JPanel(new BorderLayout());

		p.add(northWestPanel(), BorderLayout.WEST);
		p.add(northEastPanel(), BorderLayout.EAST);

		return p;
	}

	private JPanel northEastPanel() {
		JPanel topLayer = new JPanel(new BorderLayout());
		topLayer.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15)); // top,
																			// left,
																			// bottom,
																			// right
		JPanel north = new JPanel();
		JPanel south = new JPanel();
		JPanel west = new JPanel();
		JLabel label;
		JSpinner spinner;

		randStrArea = new JTextArea();
		randStrArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(randStrArea);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(470, 80)); // Width, Height
		north.add(scrollPane);

		JButton button;

		button = new JButton();
		button.setPreferredSize(new Dimension(85, 25));
		button.setIcon(new ImageIcon(getClass().getResource("images/reset.png")));
		button.setToolTipText("Reset Simulation");
		button.setFocusPainted(false);
		button.setActionCommand("reset");
		button.addActionListener(this);
		south.add(button);

		button = new JButton();
		button.setPreferredSize(new Dimension(85, 25));
		button.setIcon(new ImageIcon(getClass().getResource("images/play.png")));
		button.setToolTipText("Run Simulation");
		button.setFocusPainted(false);
		button.setActionCommand("run");
		button.addActionListener(this);
		south.add(button);

		// TODO: Add stopping functionality
		button = new JButton();
		button.setPreferredSize(new Dimension(85, 25));
		button.setIcon(new ImageIcon(getClass().getResource("images/stop.png")));
		button.setToolTipText("Pause Simulation");
		button.setFocusPainted(false);
		button.setActionCommand("pause");
		button.addActionListener(this);
		button.setEnabled(true);
		south.add(button);

		// TODO: Add stepping functionality
		// TODO: change to pause icon
		button = new JButton();
		button.setPreferredSize(new Dimension(85, 25));
		button.setIcon(new ImageIcon(getClass().getResource("images/step.png")));
		button.setToolTipText("Step Through");
		button.setFocusPainted(false);
		button.setActionCommand("step");
		button.addActionListener(this);
		button.setEnabled(false);
		south.add(button);

		button = new JButton("Generate");
		button.setPreferredSize(new Dimension(110, 25));
		button.setToolTipText("Generate Random String of Numbers");
		button.setFocusPainted(false);
		button.setActionCommand("generate");
		button.addActionListener(this);
		south.add(button);

		label = new JLabel("# of Frames");
		west.add(label);

		frameSpinnerModel = new SpinnerNumberModel(4, 4, 100, 1); // Initial,
																	// Min, Max,
																	// Increment
		spinner = new JSpinner(frameSpinnerModel);
		spinner.setPreferredSize(new Dimension(55, 25));
		west.add(spinner);

		if (getOS().contains("mac"))
			west.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
		else if (getOS().contains("win"))
			west.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));
		else
			west.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 0));

		label = new JLabel("Range of Pages");
		label.setToolTipText("Range of Generated Numbers\n");
		west.add(label);

		rangeSpinnerModel = new SpinnerNumberModel(0, 0, 3000, 1); // Initial,
																	// Min, Max,
																	// Increment
		spinner = new JSpinner(rangeSpinnerModel);
		if (getOS().contains("mac"))
			spinner.setPreferredSize(new Dimension(75, 25));
		else
			spinner.setPreferredSize(new Dimension(55, 25));
		west.add(spinner);

		label = new JLabel("String Length");
		label.setToolTipText("Numbers to Generate");
		west.add(label);

		strLengthModel = new SpinnerNumberModel(7, 7, 99, 1); // Initial, Min,
																// Max,
																// Increment
		spinner = new JSpinner(strLengthModel);
		if (getOS().contains("mac"))
			spinner.setPreferredSize(new Dimension(45, 25)); // width, height
		else
			spinner.setPreferredSize(new Dimension(35, 25)); // width, height
		west.add(spinner);

		topLayer.add(north, BorderLayout.NORTH);
		topLayer.add(west, BorderLayout.WEST);
		topLayer.add(south, BorderLayout.SOUTH);

		return topLayer;
	}

	private JPanel northWestPanel() {
		JPanel p = new JPanel();
		ImageIcon i = new ImageIcon(getClass().getResource(
				"images/page_allocation_simulator.png"));
		JLabel label = new JLabel(i);

		JLabel buffer = new JLabel();
		buffer.setPreferredSize(new Dimension(18, 0));

		p.add(buffer);
		p.add(label);

		return p;
	}

	private JPanel centerPanel() {
		JPanel topLayer = new JPanel(new FlowLayout());
		for (SimulationPanel alg : simulations) {
			topLayer.add(alg);
		}

		return topLayer;
	}

	/*
	 * JMenuBar holding all menu options
	 */
	private JMenuBar menuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMenu, helpMenu;
		JMenuItem run, stop, reset, properties, exit, help, about;

		// JMenu's
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");

		fileMenu.setRolloverEnabled(true);
		helpMenu.setRolloverEnabled(true);

		// JMenuItem's
		run = new JMenuItem("Run");
		run.setIcon(new ImageIcon(getClass().getResource("images/play.png")));
		stop = new JMenuItem("Stop");
		stop.setEnabled(true);
		stop.setIcon(new ImageIcon(getClass().getResource("images/stop.png")));
		reset = new JMenuItem("Reset");
		reset.setIcon(new ImageIcon(getClass().getResource("images/reset.png")));
		properties = new JMenuItem("Properties");
		properties.setIcon(new ImageIcon(getClass().getResource(
				"images/properties_icon.png")));
		exit = new JMenuItem("Exit");
		help = new JMenuItem("Help");
		help.setIcon(new ImageIcon(getClass().getResource(
				"images/help_icon.png")));
		about = new JMenuItem("About");
		about.setIcon(new ImageIcon(getClass().getResource(
				"images/exclamation_icon.png")));

		// Add JMenuItem's to the JMenu's
		fileMenu.add(run);
		fileMenu.add(stop);
		fileMenu.add(reset);
		fileMenu.addSeparator();
		fileMenu.add(properties);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		helpMenu.add(help);
		helpMenu.add(about);

		// Accelerator
		run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));

		// Action Listening
		run.setActionCommand("run");
		run.addActionListener(this);
		stop.setActionCommand("stop");
		stop.addActionListener(this);
		reset.setActionCommand("reset");
		reset.addActionListener(this);
		properties.setActionCommand("properties");
		properties.addActionListener(this);
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		help.setActionCommand("help");
		help.addActionListener(this);
		about.setActionCommand("about");
		about.addActionListener(this);

		// Add JMenu's to the JMenuBar
		mb.add(fileMenu);
		mb.add(helpMenu);

		return mb;
	}

	/**
	 * Centers the JFrame on the users screen.
	 * 
	 * @return returns a Point(x, y) where the JFrame will be centered on the
	 *         users screen.
	 */
	private Point setFrameCentered() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int w = f.getSize().width;
		int h = f.getSize().height;
		int x = (d.width - w) / 2;
		int y = (d.height - h) / 2;

		Point p = new Point(x, y - 20);
		return p;
	}

	/**
	 * Generates a random string of numbers between 1 and 30 (numbers are
	 * separated by commas).
	 * 
	 * @param length
	 *            the number of random numbers to generate for the string
	 * @return the string of random numbers
	 */
	private String generateRandomPageReference(int length, int range) {
		String s = "";
		Random gen = new Random();

		for (int i = 0; i < length; i++) {
			int r = gen.nextInt(range + 1);
			if (i != length - 1)
				s = s + ("" + r + ", "); // Places commas between numbers in the
											// string
			else
				s = s + ("" + r); // End the string with no comma
		}

		return s;
	}

	/**
	 * Finds the name of the Operating System that the user is currently using.
	 * Helpful function for writing platform specific code.
	 * 
	 * @return name of the operating system that the user is currently using
	 */
	private String getOS() {
		String s = System.getProperty("os.name").toLowerCase();

		return s;
	}

	/**
	 * Add text from a specified file to the specified JTextArea
	 * 
	 * @param t
	 *            JTextArea to add the content to
	 * @param f
	 *            Text file name
	 */
	private void addContent(JTextArea t, String f) {
		String line;
		try {
			InputStream iStream = getClass().getResourceAsStream(f);
			InputStreamReader isr = new InputStreamReader(iStream);
			BufferedReader reader = new BufferedReader(isr);

			while ((line = reader.readLine()) != null) {
				t.append(line);
				t.append("\n");
			}
			iStream.close();
			isr.close();
			reader.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// /**
	// * Changes the JTable column header values
	// *
	// * @param t
	// * the JTable to modify
	// * @param index
	// * the column of the JTable 't' to modify
	// * @param newName
	// * the new value to set at 'index' of 't'
	// */
	// private void updateColumnName(JTable t, int index, String newName) {
	// JTableHeader th = t.getTableHeader();
	// TableColumnModel tcm = th.getColumnModel();
	// TableColumn tc = tcm.getColumn(index);
	// tc.setHeaderValue(newName);
	// th.repaint();
	// }

	/*
	 * Takes the string of numbers from the randStrArea and places the numbers
	 * into an integer array. Converts from String to int.
	 */
	private int[] refStringToArray() {
		String s = randStrArea.getText();
		String[] s2 = s.split(",\\s+", s.length());
		int[] i = new int[s2.length];

		for (int m = 0; m < s2.length; m++) {
			if (s.length() > 0)
				i[m] = Integer.parseInt(s2[m]);
		}

		return i;
	}

	private void populateSimulationTable(SimulationPanel simulation, int[] s) {
		AllocationStrategy strategy = simulation.strategy;
		strategy.clearStats();
		int frames = frameSpinnerModel.getNumber().intValue();
		int columns = simulation.getTable().getColumnCount();

		if (s == null)
			return;

		int[][] result = strategy.allocation(s, frames);
		int faults = strategy.faults();

		simulation.setTableInsertion(new TableInsertion(simulation.getTable(),
				simulation.getModel(), propWin, result, frames, columns, 0));

		DecimalFormat fmt = new DecimalFormat("###.##");
		simulation.getFaults().setText(Integer.toString(faults));
		simulation.getFaultRate().setText(
				fmt.format(strategy.faultRate(s.length, faults)) + "%");

	}

	/**
	 * This method adds a new column to the specified JTable. Use this over the
	 * default addColumn() method provided by Java's API.
	 * 
	 * @param t
	 *            JTable to add the column to
	 * @param h
	 *            Header for the new column
	 * @param v
	 *            Values to be stored in the column
	 */
	public void addColumn(JTable t, Object h, Object[] v) {
		DefaultTableModel m = (DefaultTableModel) t.getModel();
		TableColumn c = new TableColumn(m.getColumnCount());

		c.setHeaderValue(h);
		t.addColumn(c);
		m.addColumn(h.toString(), v);
	}

	/*
	 * This method takes the randomly generated string and adds each number to a
	 * the column header.
	 */
	private void updateSimulationTablesColumns(int[] s) {
		Object[] header = makeHeaderArray(s);
		for (SimulationPanel sims : simulations) {
			updateSimulationColumns(s, sims, header);

		}
	}

	private void updateSimulationColumns(int[] s, SimulationPanel sims,
			Object[] headers) {

		int columns = sims.getTable().getColumnCount() - 1;
		int tempLength = s.length;
		int header = columns;

		if (tempLength > columns) {
			int i = 1;
			while (tempLength > columns) {
				addColumn(sims.getTable(), header + i, null);
				tempLength--;
				i++;
			}
		} else if (tempLength < columns) {
			while (tempLength < columns) {
				removeColumnAndData(sims.getTable(), columns);
				columns--;
			}
		}
		sims.getModel().setColumnIdentifiers(headers);
		// for (int i = 1; i <= s.length; i++) {
		// updateColumnName(sims.table, i, Integer.toString(s[i - 1]));
		//
		// }
	}

	/*
	 * This method either adds rows or deletes rows depending on the frame
	 * number that the user has set.
	 */
	private void updateSimulationTablesRows() {
		int frames = frameSpinnerModel.getNumber().intValue();
		for (SimulationPanel sims : simulations) {
			int rows = sims.getTable().getRowCount();
			int tempRowCount = rows;

			if (frames > rows) {
				while (frames > rows) {
					sims.getModel().addRow(new Object[] { tempRowCount });
					frames--;
					tempRowCount++;
				}
			} else if (frames < rows) {
				while (frames < rows) {
					sims.getModel().removeRow(rows - 1);
					rows--;
				}
			} else {
				// No changes need to be made to the JTable
			}

		}
	}

	public void removeColumnAndData(JTable t, int cIndex) {
		MyDefaultTableModel m = (MyDefaultTableModel) t.getModel();
		TableColumn c = t.getColumnModel().getColumn(cIndex);
		int columnModelIndex = c.getModelIndex();
		Vector<?> data = m.getDataVector();
		Vector<?> cIds = m.getColumnIdentifiers();

		t.removeColumn(c);
		cIds.removeElementAt(columnModelIndex);

		for (int r = 0; r < data.size(); r++) {
			Vector<?> row = (Vector<?>) data.get(r);
			row.removeElementAt(columnModelIndex);
		}
		m.setDataVector(data, cIds);

		Enumeration<TableColumn> e = t.getColumnModel().getColumns();
		while (e.hasMoreElements()) {
			TableColumn tc = e.nextElement();

			if (tc.getModelIndex() >= columnModelIndex)
				tc.setModelIndex(tc.getModelIndex() - 1);
		}
		m.fireTableStructureChanged();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		// Menubar
		if (actionCommand.equals("exit")) {
			System.exit(0);
		} else if (actionCommand.equals("help")) {
			JTextArea textArea = new JTextArea();
			addContent(textArea, "README.txt");
			textArea.setEditable(false);
			textArea.setCaretPosition(NORMAL);
			JScrollPane sc = new JScrollPane(textArea);
			sc.setPreferredSize(new Dimension(634, 250)); // Width, Height
			sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			JOptionPane.showMessageDialog(f, sc, "Help",
					JOptionPane.QUESTION_MESSAGE);
		} else if (actionCommand.equals("about")) {
			JOptionPane.showMessageDialog(f, "Version: " + version + "\n"
					+ "Author: Victor J. Reventos" + "\n"
					+ "Desing Based on:    \n" + "    Adam Childs\n"
					+ "    Shawn Craine\n" + "    Dylan Meyer\n", "About",
					JOptionPane.INFORMATION_MESSAGE);
		}

		// Buttons
		else if (actionCommand.equals("reset")) {
			resetGui();
		} else if (actionCommand.equals("properties")) {
			propWin = new PropertiesWindow();
		} else if (actionCommand.equals("run")) {
			runSimulation();
		} else if (actionCommand.equals("stop")) {
			stopSimulation();

		} else if (actionCommand.equals("step")) {

		} else if (actionCommand.equals("generate")) {
			/*
			 * Here we grab the value in the JSpinner which signifies the amount
			 * of numbers that the individual wants for the string. Then we
			 * generate that amount of random numbers with the
			 * generateRandomNumbers() function and then place that string into
			 * the JTextArea.
			 */
			int i = strLengthModel.getNumber().intValue();
			randStrArea.setText(generateRandomPageReference(i,
					rangeSpinnerModel.getNumber().intValue()));
		} else if (actionCommand.equals("pause")) {
			pauseSimulation();
		}
	}

	private void pauseSimulation() {
		simManager.pause();

	}

	private void stopSimulation() {
		// TODO

	}

	private void runSimulation() {
		/*
		 * If no string has been generated or entered we display an error
		 * message prompting the user to enter a string of numbers. If a string
		 * has been provided, we continue with the program execution.
		 */
		if (randStrArea.getText().equals("")) {
			JOptionPane.showMessageDialog(f,
					"Error! No string detected. Please\n"
							+ "generate or supply a string of numbers\n"
							+ "with a comma and space between each\n"
							+ "number.", "String Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (simManager.isRunning()) {
				System.out.println("simulator running");
				simManager.play();
				return;
			}

			int[] s = refStringToArray();
			if (!(s == null)) {
				if (!(s.length < 7)) {
					updateSimulationTablesColumns(s);
					updateSimulationTablesRows();

					for (SimulationPanel sim : simulations) {
						populateSimulationTable(sim, s);
					}

					simManager.start();

				} else {
					JOptionPane.showMessageDialog(f,
							"You must supply a string of\n"
									+ "at least 7 numbers. Remember\n"
									+ "to separate each number by a\n"
									+ "comma and a space.", "String Error",
							JOptionPane.ERROR_MESSAGE);
				}
				s = null;
			}

		}
	}

	private void resetGui() {
		/*
		 * Set all fields back to their default values
		 */
		randStrArea.setText("");
		strLengthModel.setValue(Integer.valueOf(7));
		frameSpinnerModel.setValue(Integer.valueOf(4));
		rangeSpinnerModel.setValue(Integer.valueOf(0));
		simManager.stop();
		for (SimulationPanel sim : simulations) {
			sim.clear();
		}
	}

	private Object[] makeHeaderArray(int[] s) {
		int length = s.length + 1;
		Object[] header = new Object[length];
		header[0] = "Frames";
		for (int i = 0; i < s.length; i++) {
			header[i + 1] = s[i];
		}
		return header;
	}
}