package com.puc.tomasuloapp.panel;

import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;
import org.apache.batik.transcoder.TranscoderException;

import javax.swing.*;
import java.io.IOException;

public class MainPanel extends JTabbedPane {
    private static final String runAlgorithm = "Run Algorithm";

    public MainPanel() throws TranscoderException, IOException {
        add(runAlgorithm, new AlgorithmPanel());
    }
}
