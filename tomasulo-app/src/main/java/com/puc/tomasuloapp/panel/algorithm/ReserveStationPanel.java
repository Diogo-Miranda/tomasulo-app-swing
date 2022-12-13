package com.puc.tomasuloapp.panel.algorithm;


import com.puc.tomasuloapp.panel.algorithm.wrapped.ConfigReserveStateWrapped;
import com.puc.tomasuloapp.panel.algorithm.wrapped.ReserveStationWrapped;

import javax.swing.*;

public class ReserveStationPanel extends JTabbedPane {
    public ReserveStationWrapped reserveStationWrapped;
    public ConfigReserveStateWrapped configWrapped;


    public ReserveStationPanel() {
        reserveStationWrapped = new ReserveStationWrapped();

        add("Reserve Station State", reserveStationWrapped);
        // add("Config", configWrapped);
    }
}
