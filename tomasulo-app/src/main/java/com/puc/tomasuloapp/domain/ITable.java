package com.puc.tomasuloapp.domain;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface ITable<T> {
    void addRow(List<T> input);
    Object[] getRow(int row);
    int getRowCount();
    void updateTable(List<T> input);

    DefaultTableModel getModel();
    void updateRow(String visitorId, Object[] data);
}
