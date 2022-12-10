package com.puc.tomasuloapp.panel.algorithm.table;


import com.puc.tomasuloapp.component.CoreComponent;
import com.puc.tomasuloapp.domain.ITable;
import com.puc.tomasuloapp.model.Register;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class RegistersTable extends JScrollPane implements ITable<Register> {
    protected DefaultTableModel defaultTableModel;
    protected JTable registersTable;
    private static final int NUMBER_OF_REGISTERS = CoreComponent.numberOfRegs;

    private static final int colNumber = CoreComponent.numberOfRegs;

    public RegistersTable(boolean editable) {
        defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
        };

        defaultTableModel.addColumn("Field");
        for(int i = 0; i < NUMBER_OF_REGISTERS; i++) {
            defaultTableModel.addColumn("F" + i);
        }

        registersTable = new JTable(defaultTableModel);

        setViewportView(registersTable);

        registersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public DefaultTableModel getModel() {
        return defaultTableModel;
    }

    public void addRow(List<Register> registers) {
        addRow(registers, true);
    }

    @Override
    public Object[] getRow(int row) {
        Object[] result = new String[colNumber];

        for (int i = 1; i < colNumber; i++) {
            result[i-1] = getModel().getValueAt(row, i);
        }

        return result;
    }

    @Override
    public int getRowCount() {
        return getModel().getRowCount();
    }

    public void addRow(List<Register> registers, boolean withFocus) {
        Object[] rowData = new Object[NUMBER_OF_REGISTERS + 1];

        rowData[0] = "Reord.#";
        for(int i = 1; i <= NUMBER_OF_REGISTERS; i++) {
            var reorderNumber = registers.get(i-1).getReorderNumber();
            if (reorderNumber != null)
                rowData[i] = reorderNumber.toString();
            else
                rowData[i] = "";
        }

        getModel().addRow(rowData);

        rowData[0] = "Busy";
        for(int i = 1; i <= NUMBER_OF_REGISTERS; i++) {
            rowData[i] = registers.get(i-1).busyToString();
        }

        getModel().addRow(rowData);
    }
}
