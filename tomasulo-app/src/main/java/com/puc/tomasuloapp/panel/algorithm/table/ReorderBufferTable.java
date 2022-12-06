package com.puc.tomasuloapp.panel.algorithm.table;


import com.puc.tomasuloapp.model.Instruction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReorderBufferTable extends JScrollPane {
    protected DefaultTableModel defaultTableModel;
    protected JTable registersTable;

    public ReorderBufferTable(boolean editable) {
        defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
        };

        defaultTableModel.addColumn("Input");
        defaultTableModel.addColumn("Busy");
        defaultTableModel.addColumn("Instruction");
        defaultTableModel.addColumn("Status");
        defaultTableModel.addColumn("Target");
        defaultTableModel.addColumn("Value");

        registersTable = new JTable(defaultTableModel);

        setViewportView(registersTable);

        registersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void addRow(List<Instruction> instructions) {
        Integer instructionCount = 0;
        for(var instruction : instructions) {
            Object[] rowData = new Object[6];
            rowData[0] = instructionCount.toString();
            rowData[1] = instruction.getBusy().toString();
            instructionCount++;
        }
    }
}
