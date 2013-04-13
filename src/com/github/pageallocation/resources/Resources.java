package com.github.pageallocation.resources;

import java.net.URL;

import javax.swing.ImageIcon;

public enum Resources {
	PLAY("play.png"), RESET("reset.png"), HELP("help_icon.png"), PAUSE(
			"pause_icon.png"), PROPERTIES("properties_icon.png"), SMALL(
			"small_icon.png"), STEP("step.png"), EXCLAMATION(
			"exclamation_icon.png"), STOP("stop.png"), SIMULATOR(
			"page_allocation_simulator.png");

	private ImageIcon icon;

	private Resources(String path) {
		this.icon = loadIcon(path);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	private ImageIcon loadIcon(String path) {
		URL url = Resources.class.getResource(path);
		if (url == null) {
			System.out.println("Invalid path " + path);
		}

		return new ImageIcon(url);
	}

}
