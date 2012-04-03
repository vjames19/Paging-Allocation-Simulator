import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.Enumeration;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/*
 * This class holds the foundation of the program's Graphical
 * User Interface. The GUI is set up and displayed from this class.
 * This class also calls other classes and their methods in order to
 * display our data (such as our page allocations).
 */
class UserInterface extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JFrame f;
	private Container contentPane;
	private JTextArea randStrArea;
	private SpinnerNumberModel strLengthModel, frameSpinnerModel, rangeSpinnerModel;
	private DefaultTableModel FIFO_TM, LRU_TM, SC_TM;
	private JTable FIFO_Table, LRU_Table, SC_Table;
	private JTextField FIFO_faults, FIFO_fr, LRU_faults, LRU_fr, SC_faults, SC_fr;
	private PropertiesWindow propWin;
	private TableInsertion fifoTI, lruTI, scTI;
	private String version = "1.00"; // v1.00 (release date)

	public UserInterface()
	{
		f = new JFrame();
		f.setTitle( "Page Allocation Simulator" ); // Set the title of the JFrame
		contentPane = f.getContentPane();

		// Add the components to the JFrame
		f.setJMenuBar(menuBar()); // Add the JMenuBar to the JFrame
		contentPane.add(northPanel(), BorderLayout.NORTH);
		contentPane.add(centerPanel(), BorderLayout.CENTER);

		if (getOS().contains("win"))
			f.setSize(780, 560); // Set the size of the JFrame (width, height)
		else
			f.setSize(800, 560); // Set the size of the JFrame (width, height)
		f.setIconImage(new ImageIcon(getClass().getResource("images/small_icon.png")).getImage());
		f.setLocation(setFrameCentered()); // Set the location of the JFrame on the screen
		f.setResizable(false); // Frame cannot be resized
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End all java processes when the program is closed
		f.setVisible(true); // JFrame must be visible to see it
	}

	private JPanel northPanel()
	{
		JPanel p = new JPanel(new BorderLayout());

		p.add(northWestPanel(), BorderLayout.WEST);
		p.add(northEastPanel(), BorderLayout.EAST);

		return p;
	}

	private JPanel northEastPanel()
	{
		JPanel topLayer = new JPanel(new BorderLayout());
		topLayer.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15)); // top, left, bottom, right
		JPanel north = new JPanel();
		JPanel south = new JPanel();
		JPanel west = new JPanel();
		JLabel label;
		JSpinner spinner;

		randStrArea = new JTextArea();
		randStrArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(randStrArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
		button.setToolTipText("End Simulation");
		button.setFocusPainted(false);
		button.setActionCommand("stop");
		button.addActionListener(this);
		button.setEnabled(false);
		south.add(button);

		// TODO: Add stepping functionality
		button = new JButton();
		button.setPreferredSize(new Dimension(85, 25));
		button.setIcon(new ImageIcon(getClass().getResource("images/step.png")));
		button.setToolTipText("Step Through");
		button.setFocusPainted(false);
		button.setActionCommand("step");
		button.addActionListener(this);
		button.setEnabled(false);
		south.add(button);

		button = new JButton( "Generate" );
		button.setPreferredSize(new Dimension(110, 25));
		button.setToolTipText("Generate Random String of Numbers");
		button.setFocusPainted(false);
		button.setActionCommand("generate");
		button.addActionListener(this);
		south.add(button);

		label = new JLabel( "# of Frames" );
		west.add(label);

		frameSpinnerModel = new SpinnerNumberModel(4, 4, 100, 1); // Initial, Min, Max, Increment
		spinner = new JSpinner(frameSpinnerModel);
		spinner.setPreferredSize(new Dimension(55, 25));
		west.add(spinner);

		if (getOS().contains("mac"))
			west.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
		else if (getOS().contains("win"))
			west.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));
		else
			west.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 0));

		label = new JLabel( "Range of Pages" );
		label.setToolTipText( "Range of Generated Numbers\n");
		west.add(label);

		rangeSpinnerModel = new SpinnerNumberModel(0, 0, 3000, 1); // Initial, Min, Max, Increment
		spinner = new JSpinner(rangeSpinnerModel);
		if (getOS().contains("mac"))
			spinner.setPreferredSize(new Dimension(75, 25));
		else
			spinner.setPreferredSize(new Dimension(55, 25));
		west.add(spinner);

		label = new JLabel( "String Length" );
		label.setToolTipText( "Numbers to Generate" );
		west.add(label);

		strLengthModel = new SpinnerNumberModel(7, 7, 99, 1); // Initial, Min, Max, Increment
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

	private JPanel northWestPanel()
	{
		JPanel p = new JPanel();
		ImageIcon i = new ImageIcon(getClass().getResource("images/page_allocation_simulator.png"));
		JLabel label = new JLabel(i);

		JLabel buffer = new JLabel();
		buffer.setPreferredSize(new Dimension(18, 0));
		
		p.add(buffer);
		p.add(label);

		return p;
	}

	private JPanel centerPanel()
	{
		JPanel topLayer = new JPanel(new FlowLayout());
		JPanel firstPanel, secondPanel, thirdPanel;
		JScrollPane sc;
		String[][] data = new String[4][8]; // Rows, Columns
		for(int i = 0; i < 4; i++)
			data[i][0] = "" + i;
		String[] columnNames = { "Frames", "A", "B", "C", "D", "E", "F", "G" };
		JLabel label;

		// First Panel
		firstPanel = new JPanel();
		label = new JLabel( "FIFO" );
		label.setToolTipText("First In First Out");
		label.setPreferredSize(new Dimension(50, 25));
		firstPanel.add(label);

		FIFO_TM = new MyDefaultTableModel(data, columnNames);
		FIFO_Table = new JTable(FIFO_TM);
		FIFO_Table.setCellSelectionEnabled(false);
		FIFO_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		FIFO_Table.setAutoCreateColumnsFromModel(false);
		sc = new JScrollPane(FIFO_Table);
		sc.setPreferredSize(new Dimension(600, 98));
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		firstPanel.add(sc);

		// Second Panel
		secondPanel = new JPanel();
		label = new JLabel( "LRU" );
		label.setToolTipText("Least Recently Used");
		label.setPreferredSize(new Dimension(50, 25));
		secondPanel.add(label, FlowLayout.LEFT);

		LRU_TM = new MyDefaultTableModel(data, columnNames);
		LRU_Table = new JTable(LRU_TM); // Rows, Columns
		LRU_Table.setCellSelectionEnabled(false);
		LRU_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		LRU_Table.setAutoCreateColumnsFromModel(false);
		sc = new JScrollPane(LRU_Table);
		sc.setPreferredSize(new Dimension(600, 98));
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		secondPanel.add(sc);

		// Third Panel
		thirdPanel = new JPanel();
		label = new JLabel( "SC" );
		label.setToolTipText("Second-Chance");
		label.setPreferredSize(new Dimension(50, 25));
		thirdPanel.add(label, FlowLayout.LEFT);

		SC_TM = new MyDefaultTableModel(data, columnNames);
		SC_Table = new JTable(SC_TM); // Rows, Columns
		SC_Table.setCellSelectionEnabled(false);
		SC_Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		SC_Table.setAutoCreateColumnsFromModel(false);
		sc = new JScrollPane(SC_Table);
		sc.setPreferredSize(new Dimension(600, 98));
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		thirdPanel.add(sc);

		firstPanel.add(fPageFaults(), BorderLayout.EAST);
		secondPanel.add(lPageFaults(), BorderLayout.EAST);
		thirdPanel.add(sPageFaults(), BorderLayout.EAST);

		topLayer.add(firstPanel);
		topLayer.add(secondPanel);
		topLayer.add(thirdPanel);

		return topLayer;
	}

	/*
	 * Lays out the panel containing page fault and
	 * page fault rate information, next to the JTables.
	 */
	private JPanel fPageFaults()
	{
		JLabel label;
		JPanel panelTotal = new JPanel();
		panelTotal.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());

		label = new JLabel( "Page Faults:");
		panelTop.add(label, BorderLayout.NORTH);

		FIFO_faults = new JTextField();
		FIFO_faults.setEditable(false);
		FIFO_faults.setBackground(Color.LIGHT_GRAY);
		panelTop.add(FIFO_faults, BorderLayout.CENTER);

		JPanel panelBot = new JPanel();
		panelBot.setLayout(new BorderLayout());
		label = new JLabel( "Fault Rate:");
		panelBot.add(label, BorderLayout.NORTH);

		FIFO_fr = new JTextField();
		FIFO_fr.setEditable(false);
		FIFO_fr.setBackground(Color.LIGHT_GRAY);
		panelBot.add(FIFO_fr, BorderLayout.CENTER);

		panelTotal.add(panelTop, BorderLayout.NORTH);
		panelTotal.add(panelBot, BorderLayout.CENTER);
		
		return panelTotal;
	}

	/*
	 * Lays out the panel containing page fault and
	 * page fault rate information, next to the JTables.
	 */
	private JPanel lPageFaults()
	{
		JLabel label;
		JPanel panelTotal = new JPanel();
		panelTotal.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());

		label = new JLabel( "Page Faults:");
		panelTop.add(label, BorderLayout.NORTH);

		LRU_faults = new JTextField();
		LRU_faults.setEditable(false);
		LRU_faults.setBackground(Color.LIGHT_GRAY);
		panelTop.add(LRU_faults, BorderLayout.CENTER);

		JPanel panelBot = new JPanel();
		panelBot.setLayout(new BorderLayout());
		label = new JLabel( "Fault Rate:");
		panelBot.add(label, BorderLayout.NORTH);

		LRU_fr = new JTextField();
		LRU_fr.setEditable(false);
		LRU_fr.setBackground(Color.LIGHT_GRAY);
		panelBot.add(LRU_fr, BorderLayout.CENTER);

		panelTotal.add(panelTop, BorderLayout.NORTH);
		panelTotal.add(panelBot, BorderLayout.CENTER);
		
		return panelTotal;
	}

	/*
	 * Lays out the panel containing page fault and
	 * page fault rate information, next to the JTables.
	 */
	private JPanel sPageFaults()
	{
		JLabel label;
		JPanel panelTotal = new JPanel();
		panelTotal.setLayout(new BorderLayout());
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());

		label = new JLabel( "Page Faults:");
		panelTop.add(label, BorderLayout.NORTH);

		SC_faults = new JTextField();
		SC_faults.setEditable(false);
		SC_faults.setBackground(Color.LIGHT_GRAY);
		panelTop.add(SC_faults, BorderLayout.CENTER);

		JPanel panelBot = new JPanel();
		panelBot.setLayout(new BorderLayout());
		label = new JLabel( "Fault Rate:");
		panelBot.add(label, BorderLayout.NORTH);

		SC_fr = new JTextField();
		SC_fr.setEditable(false);
		SC_fr.setBackground(Color.LIGHT_GRAY);
		panelBot.add(SC_fr, BorderLayout.CENTER);

		panelTotal.add(panelTop, BorderLayout.NORTH);
		panelTotal.add(panelBot, BorderLayout.CENTER);
		
		return panelTotal;
	}

	/*
	 * JMenuBar holding all menu options
	 */
	private JMenuBar menuBar()
	{
		JMenuBar mb = new JMenuBar();
		JMenu fileMenu, helpMenu;
		JMenuItem run, stop, reset, properties, exit, help, about;

		// JMenu's
		fileMenu = new JMenu( "File" );
		helpMenu = new JMenu( "Help" );

		fileMenu.setRolloverEnabled(true);
		helpMenu.setRolloverEnabled(true);

		// JMenuItem's
		run = new JMenuItem( "Run" );
		run.setIcon(new ImageIcon(getClass().getResource("images/play.png")));
		stop = new JMenuItem( "Stop" );
		stop.setEnabled(false);
		stop.setIcon(new ImageIcon(getClass().getResource("images/stop.png")));
		reset = new JMenuItem( "Reset" );
		reset.setIcon(new ImageIcon(getClass().getResource("images/reset.png")));
		properties = new JMenuItem( "Properties" );
		properties.setIcon(new ImageIcon(getClass().getResource("images/properties_icon.png")));
		exit = new JMenuItem( "Exit" );
		help = new JMenuItem( "Help" );
		help.setIcon(new ImageIcon(getClass().getResource("images/help_icon.png")));
		about = new JMenuItem( "About" );
		about.setIcon(new ImageIcon(getClass().getResource("images/exclamation_icon.png")));

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
		run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

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
	 * @return returns a Point(x, y) where the JFrame will be centered
	 * on the users screen.
	 */
	private Point setFrameCentered()
	{		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int w = f.getSize().width;
		int h = f.getSize().height;
	    int x = (d.width - w) / 2;
	    int y = (d.height - h) / 2;
	    
	    Point p = new Point(x, y - 20);
		return p;
	}

	/**
	 * Generates a random string of numbers between 1 and 30 (numbers are separated by commas).
	 * @param length the number of random numbers to generate for the string
	 * @return the string of random numbers
	 */
	private String generateRandomNumbers(int length, int range)
	{
		String s = "";
		Random gen = new Random();

		for (int i = 0; i < length; i++)
		{
			int r = gen.nextInt(range + 1);
			if (i != length - 1)
				s = s + ("" + r + ", "); // Places commas between numbers in the string
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
	private String getOS()
	{
		String s = System.getProperty("os.name").toLowerCase();
			
		return s;
	}

	/**
	 * Add text from a specified file to the specified JTextArea
	 * @param t JTextArea to add the content to
	 * @param f Text file name
	 */
	private void addContent(JTextArea t, String f)
	{
		String line;
		try {
			InputStream iStream = getClass().getResourceAsStream(f);
			InputStreamReader isr = new InputStreamReader(iStream);
			BufferedReader reader = new BufferedReader(isr);
			
			while ((line = reader.readLine()) != null)
			{
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

	/**
	 * Changes the JTable column header values
	 * @param t the JTable to modify
	 * @param index the column of the JTable 't' to modify
	 * @param newName the new value to set at 'index' of 't'
	 */
	private void updateColumnName(JTable t, int index, String newName)
	{
		JTableHeader th = t.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		TableColumn tc = tcm.getColumn(index);
		tc.setHeaderValue( newName );
		th.repaint();
	}

	/*
	 * Takes the string of numbers from the randStrArea
	 * and places the numbers into an integer array. Converts
	 * from String to int.
	 */
	private int[] refStringToArray()
	{
		String s = randStrArea.getText();
		String[] s2 = s.split(", ", s.length());
		int[] i = new int[s2.length];

		for (int m = 0; m < s2.length; m++)
		{
			if (s.length() > 0)
				i[m] = Integer.parseInt(s2[m]);
		}

		return i;
	}

	/*
	 * Begins populating the FIFO JTable
	 */
	private void populateFIFOTable()
	{
		FIFO_Allocation fa = new FIFO_Allocation();
		int[] s = refStringToArray();
		int frames = frameSpinnerModel.getNumber().intValue();
		int columns = FIFO_Table.getColumnCount();

		if (s == null)
			return;

		int[][] result = fa.retAllocation(s, frames);
		int faults = fa.retFault();

		fifoTI = new TableInsertion(FIFO_Table, FIFO_TM, propWin, result, frames, columns, 0);
		fifoTI.start();

		DecimalFormat fmt = new DecimalFormat("###.##");
		FIFO_faults.setText(Integer.toString(faults));
		FIFO_fr.setText(fmt.format(fa.faultRate(s.length, faults)) + "%");
	}

	/*
	 * Begins populating the LRU JTable
	 */
	private void populateLRUTable()
	{
		int[] s = refStringToArray();
		int frames = frameSpinnerModel.getNumber().intValue();
		int columns = LRU_Table.getColumnCount();

		if (s == null)
			return;

		LRU_Allocation lrua = new LRU_Allocation(s, frames);
		
		int[][] result = lrua.return_allocation(); // array of values
		int faults = lrua.return_page_faults(); // number of faults occurred

		lruTI = new TableInsertion(LRU_Table, LRU_TM, propWin, result, frames, columns, 0);
		lruTI.start();

		DecimalFormat fmt = new DecimalFormat("###.##");
		LRU_faults.setText(Integer.toString(faults));
		LRU_fr.setText(fmt.format(lrua.return_fault_rate()) + "%");
	}

	/*
	 * Begins populating the SC JTable
	 */
	private void populateSCTable()
	{
		int[] s = refStringToArray();
		int frames = frameSpinnerModel.getNumber().intValue();
		int columns = SC_Table.getColumnCount();

		if (s == null)
			return;

		SC_Allocation sca = new SC_Allocation();
		int[][] result = sca.retAllocation(s, frames);
		int faults = sca.retFault();

		scTI = new TableInsertion(SC_Table, SC_TM, propWin, result, frames, columns, 0);
		scTI.start();

		DecimalFormat fmt = new DecimalFormat("###.##");
		SC_faults.setText(Integer.toString(faults));
		SC_fr.setText(fmt.format(sca.faultRate(s.length, faults)) + "%");
	}

	/**
	 * This method adds a new column to the specified JTable.
	 * Use this over the default addColumn() method provided by
	 * Java's API.
	 * 
	 * @param t JTable to add the column to
	 * @param h Header for the new column
	 * @param v Values to be stored in the column
	 */
	public void addColumn(JTable t, Object h, Object[] v)
	{
		DefaultTableModel m = (DefaultTableModel)t.getModel();
		TableColumn c = new TableColumn(m.getColumnCount());

		c.setHeaderValue(h);
		t.addColumn(c);
		m.addColumn(h.toString(), v);
	}

	/*
	 * This method takes the randomly generated string and adds
	 * each number to a the column header.
	 */
	private void updateTableColumns()
	{
		int[] s = refStringToArray();
		int columns = FIFO_Table.getColumnCount() - 1;
		int tempLength = s.length;
		int header = columns;

		if (tempLength > columns)
		{
			int i = 1;
			while (tempLength > columns)
			{
				addColumn(FIFO_Table, header + i, null);
				addColumn(LRU_Table, header + i, null);
				addColumn(SC_Table, header + i, null);
				tempLength--;
				i++;
			}
		} else if (tempLength < columns) {
			while (tempLength < columns)
			{
				removeColumnAndData(FIFO_Table, columns);
				removeColumnAndData(LRU_Table, columns);
				removeColumnAndData(SC_Table, columns);
				columns--;
			}
		}

		for (int i = 1; i <= s.length; i++)
		{
			updateColumnName(FIFO_Table, i, Integer.toString(s[i - 1]));
			updateColumnName(LRU_Table, i, Integer.toString(s[i - 1]));
			updateColumnName(SC_Table, i, Integer.toString(s[i - 1]));
		}
	}
	
	/*
	 * This method either adds rows or deletes rows depending on
	 * the frame number that the user has set.
	 */
	private void updateTableRows()
	{
		int frames = frameSpinnerModel.getNumber().intValue();
		int rows = FIFO_Table.getRowCount();
		int tempRowCount = rows;

		if (frames > rows)
		{
			while (frames > rows)
			{
				FIFO_TM.addRow(new Object[]{tempRowCount});
				LRU_TM.addRow(new Object[]{tempRowCount});
				SC_TM.addRow(new Object[]{tempRowCount});
				frames--;
				tempRowCount++;
			}
		} else if (frames < rows) {
			while (frames < rows)
			{
				FIFO_TM.removeRow(rows - 1);
				LRU_TM.removeRow(rows - 1);
				SC_TM.removeRow(rows - 1);
				rows--;
			}
		} else {
			// No changes need to be made to the JTable
		}
	}

	public void removeColumnAndData(JTable t, int cIndex)
	{
		MyDefaultTableModel m = (MyDefaultTableModel)t.getModel();
		TableColumn c = t.getColumnModel().getColumn(cIndex);
		int columnModelIndex = c.getModelIndex();
		Vector<?> data = m.getDataVector();
		Vector<?> cIds = m.getColumnIdentifiers();

		t.removeColumn(c);
		cIds.removeElementAt(columnModelIndex);

		for (int r=0; r<data.size(); r++)
		{
			Vector<?> row = (Vector<?>)data.get(r);
			row.removeElementAt(columnModelIndex);
		}
		m.setDataVector(data, cIds);

		Enumeration<TableColumn> e = t.getColumnModel().getColumns();
		while(e.hasMoreElements())
		{
			TableColumn tc = e.nextElement();

			if (tc.getModelIndex() >= columnModelIndex)
				tc.setModelIndex(tc.getModelIndex()-1);
		}
		m.fireTableStructureChanged();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println( "Command [" + e.getActionCommand() + "]");

		// Menubar
		if (e.getActionCommand().equals("exit"))
		{
			System.exit(0);
		}
		else if (e.getActionCommand().equals("help"))
		{
			JTextArea textArea = new JTextArea();
			addContent(textArea, "README.txt");
			textArea.setEditable(false);
			textArea.setCaretPosition(NORMAL);
			JScrollPane sc = new JScrollPane(textArea);
			sc.setPreferredSize(new Dimension(634, 250)); // Width, Height
			sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			JOptionPane.showMessageDialog(f, sc, "Help", JOptionPane.QUESTION_MESSAGE);
		}
		else if (e.getActionCommand().equals("about"))
		{
			JOptionPane.showMessageDialog(f, "Version: " + version + "\n" +
											 "Authors: " + "\n" +
											 "    Adam Childs\n" +
											 "    Shawn Craine\n" +
											 "    Dylan Meyer\n",
											 "About", JOptionPane.INFORMATION_MESSAGE);
		}

		// Buttons
		else if (e.getActionCommand().equals("reset"))
		{
			/*
			 * Set all fields back to their default values
			 */
			randStrArea.setText("");
			strLengthModel.setValue(Integer.valueOf(7));
			frameSpinnerModel.setValue(Integer.valueOf(4));
			rangeSpinnerModel.setValue(Integer.valueOf(0));
			
			FIFO_faults.setText("");
			FIFO_fr.setText("");
			LRU_faults.setText("");
			LRU_fr.setText("");
			SC_faults.setText("");
			SC_fr.setText("");

			int columns = FIFO_Table.getColumnCount() - 1;

			while (7 < columns)
			{
				removeColumnAndData(FIFO_Table, columns);
				removeColumnAndData(LRU_Table, columns);
				removeColumnAndData(SC_Table, columns);
				columns--;
			}

			for (int i = 1; i < FIFO_Table.getColumnCount(); i++)
			{
				for (int m = 0; m < FIFO_Table.getRowCount(); m++)
				{
					FIFO_Table.getModel().setValueAt("", m, i);
					LRU_Table.getModel().setValueAt("", m, i);
					SC_Table.getModel().setValueAt("", m, i);
				}
			}
			FIFO_TM.fireTableDataChanged();
			LRU_TM.fireTableDataChanged();
			SC_TM.fireTableDataChanged();

			String[] s = { "A", "B", "C", "D", "E", "F", "G" };
			for(int i = 0; i < s.length; i++)
			{
				updateColumnName(FIFO_Table, i + 1, "" + s[i]);
				updateColumnName(LRU_Table, i + 1, "" + s[i]);
				updateColumnName(SC_Table, i + 1, "" + s[i]);
			}
		}
		else if (e.getActionCommand().equals("properties"))
		{
			propWin = new PropertiesWindow();
		}
		else if (e.getActionCommand().equals("run"))
		{
			/*
			 * If no string has been generated or entered
			 * we display an error message prompting the
			 * user to enter a string of numbers. If a string
			 * has been provided, we continue with the program
			 * execution.
			 */
			if (randStrArea.getText().equals(""))
			{
				JOptionPane.showMessageDialog(f, "Error! No string detected. Please\n" +
												"generate or supply a string of numbers\n" +
												"with a comma and space between each\n" +
												"number.",
												"String Error", JOptionPane.ERROR_MESSAGE);
			} else {
				if (!(refStringToArray() == null))
				{
					if (!(refStringToArray().length < 7))
					{
						updateTableColumns();
						updateTableRows();
						populateFIFOTable();
						populateLRUTable();
						populateSCTable();
					} else {
						JOptionPane.showMessageDialog(f, "You must supply a string of\n" +
														"at least 7 numbers. Remember\n" +
														"to separate each number by a\n" +
														"comma and a space.",
														"String Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		else if (e.getActionCommand().equals("stop"))
		{
			
		}
		else if (e.getActionCommand().equals("step"))
		{
			
		}
		else if (e.getActionCommand().equals("generate"))
		{
			/*
			 * Here we grab the value in the JSpinner which signifies the amount of
			 * numbers that the individual wants for the string. Then we generate
			 * that amount of random numbers with the generateRandomNumbers() function
			 * and then place that string into the JTextArea.
			 */
			int i = strLengthModel.getNumber().intValue();
			randStrArea.setText(generateRandomNumbers(i, rangeSpinnerModel.getNumber().intValue()));
		}
	}
}