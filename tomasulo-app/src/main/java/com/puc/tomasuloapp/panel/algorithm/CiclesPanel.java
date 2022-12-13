package com.puc.tomasuloapp.panel.algorithm;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CiclesPanel extends JPanel {

    public JTextArea cyclesTextArea;
    public JLabel cyclesLabel;

    public LineBorder lineBorder;

    public CiclesPanel() {
        cyclesTextArea = new JTextArea();
        cyclesLabel = new JLabel("Cycles:");
        lineBorder = new LineBorder(Color.GRAY, 2, true);

        cyclesTextArea.setEditable(false);
        cyclesTextArea.insert("0", 0);

        add(cyclesLabel, "pushx,growx,wrap");
        add(cyclesTextArea, "wrap");
        setBorder(lineBorder);
    }
}
