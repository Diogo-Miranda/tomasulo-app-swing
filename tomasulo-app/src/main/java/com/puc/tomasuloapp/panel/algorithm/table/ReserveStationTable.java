package com.puc.tomasuloapp.panel.algorithm.table;

import com.puc.tomasuloapp.domain.ITable;
import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.ReserveStation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReserveStationTable extends JScrollPane implements ITable<ReserveStation> {
    protected DefaultTableModel defaultTableModel;
    protected JTable registersTable;

    private final int colNumber = 9;

    public ReserveStationTable(boolean editable) {
        defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
        };

        defaultTableModel.addColumn("Name");
        defaultTableModel.addColumn("Busy");
        defaultTableModel.addColumn("Op");
        defaultTableModel.addColumn("Vj");
        defaultTableModel.addColumn("Vk");
        defaultTableModel.addColumn("Qj");
        defaultTableModel.addColumn("Qk");
        defaultTableModel.addColumn("Dest");
        defaultTableModel.addColumn("A");

        registersTable = new JTable(defaultTableModel);

        setViewportView(registersTable);

        registersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void addRow(List<ReserveStation> reserveStations) {
        for(var reserveStation : reserveStations) {
            Object[] rowData = new Object[colNumber];
            rowData[0] = reserveStation.getName();
            rowData[1] = reserveStation.getBusy().toString();
            rowData[2] = reserveStation.getOp();
            rowData[3] = reserveStation.getVj();
            rowData[4] = reserveStation.getQj();
            rowData[5] = reserveStation.getQk();
            rowData[6] = reserveStation.getDest();
            rowData[7] = reserveStation.getA();

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

    @Override
    public int getRowCount() {
        return getModel().getRowCount();
    }

    public DefaultTableModel getModel() {
        return defaultTableModel;
    }
}
