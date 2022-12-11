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
            rowData[1] = instruction.busyToString();
            // Instruction
            rowData[2] = instruction.toString();
            // Status
            rowData[3] = "initial";
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

    @Override
    public void updateRow(String visitorId, Object[] data) {
        if (data.length > colNumber)
            throw new IllegalArgumentException("data[] is to long");
        for (int i = 0; i < getRowCount(); i++) {
            if (Integer.parseInt((String) getModel().getValueAt(i, 0)) == (Integer.parseInt(visitorId)))
                for (int j = 1; j < data.length+1; j++) {
                    getModel().setValueAt(data[j-1], i, j);
                }
        }
    }

  @Override
  public void updateTable(List<Instruction> instructions) {

    for (int row = 0; row < instructions.size(); row++) {

      var instruction = instructions.get(row);
      getModel().setValueAt(String.valueOf(row), row, 0);
      getModel().setValueAt(instruction.busyToString(), row, 1);
      getModel().setValueAt(instruction.toString(), row, 2);
      getModel().setValueAt(instruction.getStatus(), row, 3);
      getModel().setValueAt(instruction.getRegDestiny(), row, 4);
      getModel().setValueAt(instruction.getInstructionValue(), row, 5);
    }
  }
}
