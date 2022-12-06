package com.puc.tomasuloapp.panel.algorithm;

import com.puc.tomasuloapp.panel.algorithm.wrapped.ReorderBufferWrapped;

import javax.swing.*;

public class ReorderBufferPanel extends JTabbedPane {
    public final ReorderBufferWrapped reorderBufferWrapped;

    public ReorderBufferPanel() {
        reorderBufferWrapped = new ReorderBufferWrapped();

        add("Reorder Buffer State", reorderBufferWrapped);
    }
}
