package com.puc.tomasuloapp.core;

import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.model.ReserveStation;
import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;
import com.puc.tomasuloapp.util.Deserialize;
import com.puc.tomasuloapp.util.InstructionUtils;
import com.puc.tomasuloapp.util.RegistersUtils;
import com.puc.tomasuloapp.util.ReserveStationUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StepButton extends JButton {

    public StepButton(AlgorithmPanel algorithmPanel) {
        setText("Next");
        setEnabled(false);
        addActionListener((e) -> {
            runTomasuloAlgorithm(algorithmPanel);
        });
    }

    private void runTomasuloAlgorithm(AlgorithmPanel algorithmPanel) {
        List<Instruction> instructions = new Deserialize<Instruction>()
                .make(algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable,
                        new InstructionUtils());

        var registersTable = algorithmPanel.registersPanel.registersWrapped.registersTable;
        List<Register> registers = new RegistersUtils().deserialize(registersTable.getRow(0), registersTable.getRow(1));

        List<ReserveStation> reserveStations = new Deserialize<ReserveStation>()
                .make(algorithmPanel.reserveStationPanel.reserveStationWrapped.reserveStationTable,
                        new ReserveStationUtils());
    }
}
