package com.puc.tomasuloapp.core;

import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.model.ReserveStation;
import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;
import com.puc.tomasuloapp.tomasulo.TomasuloAlgorithm;
import com.puc.tomasuloapp.util.InstructionUtils;
import com.puc.tomasuloapp.util.SerializableUtils;
import com.puc.tomasuloapp.util.RegistersUtils;
import com.puc.tomasuloapp.util.ReserveStationUtils;

import javax.swing.*;
import java.util.List;
import java.util.Queue;

public class StepButton extends JButton {

    private final Queue<Instruction> instructionQueue;


    public StepButton(AlgorithmPanel algorithmPanel, Queue<Instruction> instructionQueue) {
        this.instructionQueue = instructionQueue;
        setText("Next");
        setEnabled(false);
        addActionListener(e -> runTomasuloAlgorithm(algorithmPanel));
    }

    private void runTomasuloAlgorithm(AlgorithmPanel algorithmPanel) {
        List<Instruction> instructions = new SerializableUtils<Instruction>()
                .deserialize(algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable,
                        new InstructionUtils());

        var registers = algorithmPanel.registersPanel.registersWrapped.registersTable.getRegisters();

        List<ReserveStation> reserveStations = new SerializableUtils<ReserveStation>()
                .deserialize(algorithmPanel.reserveStationPanel.reserveStationWrapped.reserveStationTable,
                        new ReserveStationUtils());

        new TomasuloAlgorithm(algorithmPanel, instructionQueue).run(instructions, registers, reserveStations);
    }
}
