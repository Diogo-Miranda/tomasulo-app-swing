package com.puc.tomasuloapp.panel.algorithm;

import net.miginfocom.swing.MigLayout;
import org.apache.batik.transcoder.TranscoderException;

import javax.swing.*;
import java.io.IOException;

public class AlgorithmPanel extends JPanel {
    public int id;
    public final RegistersPanel registersPanel;
    public final ReorderBufferPanel reorderBufferPanel;
    protected final ReserveStationPanel reserveStationPanel;

    protected final ControlPanel controlPanel;

    public AlgorithmPanel() throws TranscoderException, IOException {
        super(new MigLayout("insets 10 10 10 10"));

        controlPanel = new ControlPanel(this);
        registersPanel = new RegistersPanel();
        reorderBufferPanel = new ReorderBufferPanel();
        reserveStationPanel = new ReserveStationPanel();

        add(controlPanel, "growx, pushx, span 3, wrap");
        add(reorderBufferPanel, "growx, pushx, span 3, wrap");
        add(reserveStationPanel, "growx, pushx, span 3, wrap");
        add(registersPanel, "growx, pushx, span 3, wrap");
    }

}
