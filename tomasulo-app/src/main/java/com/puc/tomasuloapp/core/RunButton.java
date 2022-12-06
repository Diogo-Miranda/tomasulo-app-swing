package com.puc.tomasuloapp.core;

import com.puc.tomasuloapp.factory.ConfigFactory;
import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RunButton extends JButton {
    @Inject
    @Named("instructions")
    private List<Instruction> instructions;

    @Inject
    @Named("registers")
    private List<Register> registers;

    public RunButton(AlgorithmPanel algorithmPanel) {
        setText("Run");
        addActionListener((e) -> {
            setEnabled(false);
            setText("Running");
            makeRegisterTable(algorithmPanel);
            makeBufferReordTable(algorithmPanel);
        });
    }

    private void makeBufferReordTable(AlgorithmPanel algorithmPanel) {
        // var instructions = new ConfigFactory().getInstructions();
        var reorderBufferTable = algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable;
    }

    private void makeRegisterTable(AlgorithmPanel algorithmPanel) {
        var registersTable = algorithmPanel.registersPanel.registersWrapped.registersTable;
        registersTable.addRow(this.registers);
    }
}
