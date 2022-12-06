package com.puc.tomasuloapp;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.util.SystemInfo;
import com.puc.tomasuloapp.core.Frame;
import com.puc.tomasuloapp.helper.FontLoader;
import com.puc.tomasuloapp.panel.MainPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class TomasuloAppApplication {

	public static void main(String[] args) {
		var ctx = new SpringApplicationBuilder(TomasuloAppApplication.class)
				.headless(false).run(args);

		System.setProperty("awt.useSystemAAFontSettings", "lcd");
		System.setProperty("swing.aatext", "true");
		System.setProperty("sun.java2d.renderer", "sun.java2d.marlin.MarlinRenderingEngine");

		UIManager.put("Button.font", FontLoader.getDefaultFont());
		UIManager.put("Table.font", FontLoader.getDefaultFont());


		SwingUtilities.invokeLater(() -> {
			// macOS support
			if (SystemInfo.isMacOS && System.getProperty("apple.laf.useScreenMenuBar") == null)
				System.setProperty("apple.laf.useScreenMenuBar", "true");

			FlatIntelliJLaf.setup();

			JDialog.setDefaultLookAndFeelDecorated(true);
			JFrame.setDefaultLookAndFeelDecorated(true);

			var frame = new Frame("Tomasulo Algorithm");
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

}
