package com.puc.tomasuloapp.panel.algorithm;


import com.puc.tomasuloapp.panel.algorithm.wrapped.ReserveStationWrapped;

import javax.swing.*;

public class ReserveStationPanel extends JTabbedPane {
    protected ReserveStationWrapped reserveStationWrapped;

    public ReserveStationPanel() {
        reserveStationWrapped = new ReserveStationWrapped();

        add("Reserve Station State", reserveStationWrapped);
    }
}
