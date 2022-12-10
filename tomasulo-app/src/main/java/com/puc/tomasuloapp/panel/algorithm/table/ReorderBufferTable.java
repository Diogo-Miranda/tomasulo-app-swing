package com.puc.tomasuloapp.panel.algorithm.table;


import com.puc.tomasuloapp.domain.ITable;
import com.puc.tomasuloapp.model.Instruction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReorderBufferTable extends JScrollPane implements ITable<Instruction> {
    protected DefaultTableModel defaultTableModel;
    protected JTable registersTable;

    private final int colNumber = 6;

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
            Object[] rowData = new Object[colNumber];
            // Entrada
            rowData[0] = instructionCount.toString();
            // Busy
            rowData[1] = instruction.getBusy().toString();
            // Instruction
            rowData[2] = instruction.toString();
            // Status
            rowData[3] = "intial";
            // Destino
            rowData[4] = instruction.getRegDestiny();
            instructionCount++;

            // add on table
            getModel().addRow(rowData);
        }
    }

    public Object[] getRow(int row) {
        Object[] result = new String[colNumber];

        for (int i = 0; i < colNumber; i++) {
            result[i] = getModel().getValueAt(row, i);
        }

        return result;
    }

    public int getRowCount() {
        return getModel().getRowCount();
    }

    public DefaultTableModel getModel() {
        return defaultTableModel;
    }

}
