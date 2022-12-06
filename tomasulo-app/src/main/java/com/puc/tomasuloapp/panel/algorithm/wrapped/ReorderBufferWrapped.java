package com.puc.tomasuloapp.panel.algorithm.wrapped;

import com.puc.tomasuloapp.panel.algorithm.table.ReorderBufferTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ReorderBufferWrapped extends JPanel {
    public final ReorderBufferTable reorderBufferTable;
    public ReorderBufferWrapped() {
        super(new MigLayout());

        reorderBufferTable = new ReorderBufferTable(false);

        add(reorderBufferTable, "growx,pushx");
    }
}
