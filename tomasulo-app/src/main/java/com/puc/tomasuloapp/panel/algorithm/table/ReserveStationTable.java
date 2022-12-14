package com.puc.tomasuloapp.panel.algorithm.table;

import com.puc.tomasuloapp.domain.ITable;
import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.ReserveStation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
            rowData[1] = reserveStation.busyToString();
            rowData[2] = reserveStation.getOp();
            rowData[3] = reserveStation.getVj();
            rowData[4] = reserveStation.getQj();
            rowData[5] = reserveStation.getQk();
            rowData[6] = reserveStation.getDest();
            rowData[7] = reserveStation.getA();

            // add on table
            getModel().addRow(rowData);
        }
        var centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setVerticalAlignment(JLabel.CENTER);
        for(int i = 0; i < 8; i++)
            registersTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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

    @Override
    public void updateRow(String visitorId, Object[] data) {
        if (data.length > colNumber)
            throw new IllegalArgumentException("data[] is to long");
        for (int i = 0; i < getRowCount(); i++) {
            if ((getModel().getValueAt(i, 0)).equals(visitorId))
                for (int j = 1; j < data.length+1; j++) {
                    getModel().setValueAt(data[j-1], i, j);
                }
        }
    }

  @Override
  public void updateTable(List<ReserveStation> reserveStations) {

    for (int row = 0; row < reserveStations.size(); row++) {

      var reserveStation = reserveStations.get(row);

      getModel().setValueAt(reserveStation.getName(), row, 0);
      getModel().setValueAt(reserveStation.busyToString(), row, 1);
      getModel().setValueAt(reserveStation.getOp(), row, 2);
      getModel().setValueAt(reserveStation.getVj(), row, 3);
      getModel().setValueAt(reserveStation.getVk(), row, 4);
      getModel().setValueAt(reserveStation.getQj(), row, 5);
      getModel().setValueAt(reserveStation.getQk(), row, 6);
      getModel().setValueAt(reserveStation.getDest(), row, 7);
      getModel().setValueAt(reserveStation.getA(), row, 8);
    }
  }
}
