package com.puc.tomasuloapp.core;

import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.model.ReserveStation;
import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StepButton extends JButton {

    public StepButton(AlgorithmPanel algorithmPanel) {
        setText("Next");
        addActionListener((e) -> {
            runTomasuloAlgorithm(algorithmPanel);
        });
    }

    private void runTomasuloAlgorithm(AlgorithmPanel algorithmPanel) {
        List<Instruction> instructions = new ArrayList<>();
        List<Register> registers = new ArrayList<>();
        List<ReserveStation> reserveStations = new ArrayList<>();

        var reorderBufferTable = algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable;
        var registersTable = algorithmPanel.registersPanel.registersWrapped.registersTable;
        var reserveStationTable = algorithmPanel.reserveStationPanel.reserveStationWrapped.reserveStationTable;

        var row = algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable.getRow(0);
        var instruction = Instruction.deserialize(row);
    }
}
