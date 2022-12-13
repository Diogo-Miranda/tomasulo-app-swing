package com.puc.tomasuloapp;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.util.SystemInfo;
import com.puc.tomasuloapp.core.Frame;
import com.puc.tomasuloapp.helper.FontLoader;
import com.puc.tomasuloapp.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class TomasuloAppApplication extends JFrame {

	public static int defaultWidth = 1024;
	public static int defaultHeight = 600;

	public static void main(String[] args) {

		System.setProperty("awt.useSystemAAFontSettings", "lcd");
		System.setProperty("swing.aatext", "true");
		System.setProperty("sun.java2d.renderer", "sun.java2d.marlin.MarlinRenderingEngine");

		UIManager.put("Button.font", FontLoader.getDefaultFont());
		UIManager.put("Table.font", FontLoader.getTableFont());
		UIManager.put("Label.font", FontLoader.getTableFont());
		UIManager.put("TextArea.font", FontLoader.getTableFont());

		SwingUtilities.invokeLater(() -> {
			// macOS support
			if (SystemInfo.isMacOS && System.getProperty("apple.laf.useScreenMenuBar") == null)
				System.setProperty("apple.laf.useScreenMenuBar", "true");

			FlatIntelliJLaf.setup();

			JDialog.setDefaultLookAndFeelDecorated(true);
			JFrame.setDefaultLookAndFeelDecorated(true);

			var frame = new Frame("Tomasulo App");
			frame.setName("Runnable simulator for Tomasulo Algorithm");

			try {
				frame.add(new MainPanel(), BorderLayout.CENTER);
			} catch (Exception e) {
				e.printStackTrace();
			}
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

	private void initUI(String title, Boolean customTitleBar, int width, int height) {
		setTitle(title);

		var dimension = new Dimension(width, height);

		setUndecorated(customTitleBar);

		if (customTitleBar) {
			// new CustomTitle Bar TODO
		}

		var dim = Toolkit.getDefaultToolkit().getScreenSize();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(dimension);
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
	}
}
