package com.puc.tomasuloapp.panel.algorithm;

import com.puc.tomasuloapp.core.RunButton;
import com.puc.tomasuloapp.core.StepButton;
import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.util.Converter;
import com.sun.tools.javac.Main;
import org.apache.batik.transcoder.TranscoderException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

public class ControlPanel extends JPanel {

    public final StepButton stepButton;
    public final RunButton runButton;
    public final CiclesPanel ciclesPanel;

    public ControlPanel(AlgorithmPanel algorithmPanel) throws TranscoderException, IOException {
        var runIcon = svgIcon("run.svg");
        var stepIcon = svgIcon("right-arrow.svg");
        Queue<Instruction> instructionQueue = new LinkedList<>();

        ciclesPanel = new CiclesPanel();

        runButton = new RunButton(algorithmPanel, instructionQueue);
        runButton.setIcon(runIcon);

        stepButton = new StepButton(algorithmPanel, instructionQueue);
        stepButton.setIcon(stepIcon);
        ciclesPanel.setVisible(false);

        add(runButton, "wrap");
        add(stepButton, "wrap");
        add(ciclesPanel, "wrap,east");
    }

    public ImageIcon svgIcon(String iconName) throws IOException, TranscoderException {
        String fullIconName = "icon/svg/".concat(iconName);
        // Get Resources URL
        URL iconURL = Main.class.getClassLoader().getResource(fullIconName);
        // Make sure the url does not return null value
        assert iconURL != null;
        // Create Image icon from URL
        BufferedImage iconImage = Converter.convertSVGToPNG(iconURL.toString());
        // Convert Buffered image to Image icon
        return new ImageIcon(iconImage);
    }
}
