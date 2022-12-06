package com.puc.tomasuloapp.panel.algorithm.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReserveStationTable extends JScrollPane {
    protected DefaultTableModel defaultTableModel;
    protected JTable registersTable;

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
}
