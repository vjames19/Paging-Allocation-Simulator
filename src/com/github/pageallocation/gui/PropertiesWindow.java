package com.github.pageallocation.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.pageallocation.resources.Resources;

/*
 * This class constructs a modal dialog box that allows the
 * user to change program properties such as modifying the
 * delay of data being added to the JTables.
 */
public class PropertiesWindow extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField textfield;

	public PropertiesWindow() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		c.add(northPanel(), BorderLayout.CENTER);
		c.add(southPanel(), BorderLayout.SOUTH);

		setIconImage(Resources.PROPERTIES.getIcon().getImage());
		pack();
		setLocation(setFrameCentered());
		setResizable(false);
		setModal(true);
		setTitle("Properties");
		setVisible(true);
	}

	private JPanel northPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 10));

		JLabel label;
		label = new JLabel("Time Delay (ms):");
		label.setToolTipText("Time delay for displaying data in the table");
		textfield = new JTextField();
		textfield.setText("0");
		textfield.setPreferredSize(new Dimension(100, 20));
		p.add(label);
		p.add(textfield);

		return p;
	}

	private JPanel southPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton button;

		button = new JButton("Save");
		button.setActionCommand("save");
		button.addActionListener(this);
		p.add(button);

		button = new JButton("Cancel");
		button.setActionCommand("cancel");
		button.addActionListener(this);
		p.add(button);

		return p;
	}

	/**
	 * Centers the JFrame on the users screen.
	 * 
	 * @return returns a Point(x, y) where the JFrame will be centered on the
	 *         users screen.
	 */
	private Point setFrameCentered() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int w = /* getSize().width */getWidth();
		int h = /* getSize().height */getHeight();
		int x = (d.width - w) / 2;
		int y = (d.height - h) / 2;

		Point p = new Point(x, y);
		return p;
	}

	/*
	 * Save the users preferences/properties
	 */
	private void save() {
		setDelay(Integer.parseInt(textfield.getText()));
	}

	/*
	 * Returns the delay that has been set by the user. Default is 0.
	 */
	public int getDelay() {
		return Integer.parseInt(textfield.getText());
	}

	/*
	 * Sets the delay by resetting the textfield's text to whatever the user has
	 * put into the textfield.
	 */
	public void setDelay(int d) {
		textfield.setText("" + d);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("save")) {
			save();
			dispose();
			return;
		}
		if (e.getActionCommand().equals("cancel")) {
			dispose();
			return;
		}
	}
}