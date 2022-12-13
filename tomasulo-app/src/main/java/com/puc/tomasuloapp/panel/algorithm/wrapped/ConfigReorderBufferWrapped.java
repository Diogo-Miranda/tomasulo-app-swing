package com.puc.tomasuloapp.panel.algorithm.wrapped;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ConfigReorderBufferWrapped extends JPanel {
    public JButton configButton;

    String configText = "Config";

    public ConfigReorderBufferWrapped() {
        setLayout(new MigLayout("insets 10 10 10 10"));

        configButton = new JButton(configText);

        Color background = UIManager.getColor("Table.background");
        Color lineColor = UIManager.getColor("Table.gridColor");
        Color fontColor = UIManager.getColor("FormattedTextField.foreground");
        Color selectionColor = UIManager.getColor("FormattedTextField.selectionBackground");
        Color headerForeground = UIManager.getColor("TableHeader.foreground");

        JPanel configPanel = new JPanel(new MigLayout("w 200!,insets n n n 10"));
//        configPanel.add(;
//        configPanel.add(new JLabel("MUL"), "pushx,growx,wrap");
//        configPanel.add(new JLabel("LW/SW"), "pushx,growx,wrap");
//        configPanel.add(new JLabel("BRANCH"), "pushx,growx,wrap");
        add(configPanel, "wrap");
        add(new JLabel("ADD"), "west, pushy");
        add(new JLabel("LW"), "east, pushy");
        add(new JLabel("MUL"), "east, pushy");

//        configPanel.add(inputListener, "pushx,growx,wrap");
//        configPanel.add(addListenerButton, "pushx,growx,wrap");
//        configPanel.add(new JSeparator(), "pushx,growx,wrap");
//        configPanel.add(listenerScrollPane, "push,grow");
        // add(new JLabel("Response Message"), "growx,pushx,wrap");
    }
}
