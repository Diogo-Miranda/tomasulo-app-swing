package com.puc.tomasuloapp.core;

import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;

import javax.swing.*;

import com.puc.tomasuloapp.component.CoreComponent;
import com.puc.tomasuloapp.util.InstructionUtils;

import java.util.Queue;

public class RunButton extends JButton {

    private final Queue<Instruction> instructionQueue;
    private final InstructionUtils instructionUtils;

    public RunButton(AlgorithmPanel algorithmPanel, Queue<Instruction> instructionQueue) {
        this.instructionQueue = instructionQueue;
        this.instructionUtils = new InstructionUtils();
        setText("Run");
        addActionListener((e) -> {
            setEnabled(false);
            setText("Running");
            makeRegisterTable(algorithmPanel);
            makeBufferReordTable(algorithmPanel);
            makeReserveStationTable(algorithmPanel);
            fillInstructionsQueue(algorithmPanel);
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

    private void fillInstructionsQueue(AlgorithmPanel algorithmPanel) {

        var registersTable = algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable;

        for(int i=0; i<registersTable.getRowCount(); i++) {
            var row = registersTable.getRow(i);
            instructionQueue.add(instructionUtils.deserialize(row));
        }
    }
}
