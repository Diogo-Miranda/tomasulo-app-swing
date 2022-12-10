package com.puc.tomasuloapp.core;

import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;

import javax.swing.*;

import com.puc.tomasuloapp.component.CoreComponent;

public class RunButton extends JButton {

    public RunButton(AlgorithmPanel algorithmPanel) {
        setText("Run");
        addActionListener((e) -> {
            setEnabled(false);
            setText("Running");
            makeRegisterTable(algorithmPanel);
            makeBufferReordTable(algorithmPanel);
            makeReserveStationTable(algorithmPanel);
            algorithmPanel.controlPanel.stepButton.setEnabled(true);
        });
    }

    private void makeBufferReordTable(AlgorithmPanel algorithmPanel) {
        var reorderBufferTable = algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable;
        reorderBufferTable.addRow(CoreComponent.initInstructions());
    }

    private void makeRegisterTable(AlgorithmPanel algorithmPanel) {
        var registersTable = algorithmPanel.registersPanel.registersWrapped.registersTable;
        registersTable.addRow(CoreComponent.initRegisters());
    }

    private void makeReserveStationTable(AlgorithmPanel algorithmPanel) {
        var reserveStationTable = algorithmPanel.reserveStationPanel.reserveStationWrapped.reserveStationTable;
        reserveStationTable.addRow(CoreComponent.initReserveStation());
    }
}
