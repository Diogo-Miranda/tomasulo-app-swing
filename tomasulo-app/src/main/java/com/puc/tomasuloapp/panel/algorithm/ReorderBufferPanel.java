package com.puc.tomasuloapp.panel.algorithm;

import com.puc.tomasuloapp.panel.algorithm.wrapped.ConfigReorderBufferWrapped;
import com.puc.tomasuloapp.panel.algorithm.wrapped.ReorderBufferWrapped;

import javax.swing.*;

public class ReorderBufferPanel extends JTabbedPane {
    public final ReorderBufferWrapped reorderBufferWrapped;
    public final ConfigReorderBufferWrapped configWrapped;

    public ReorderBufferPanel() {
        reorderBufferWrapped = new ReorderBufferWrapped();
        configWrapped = new ConfigReorderBufferWrapped();

        add("Reorder Buffer State", reorderBufferWrapped);
        // add("Config", configWrapped);
    }
}
