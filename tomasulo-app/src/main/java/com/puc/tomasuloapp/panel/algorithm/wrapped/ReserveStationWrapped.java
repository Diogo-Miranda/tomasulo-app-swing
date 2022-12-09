package com.puc.tomasuloapp.panel.algorithm.wrapped;

import com.puc.tomasuloapp.panel.algorithm.table.ReserveStationTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ReserveStationWrapped extends JPanel {
    public final ReserveStationTable reserveStationTable;
    public ReserveStationWrapped() {
        super(new MigLayout());

        reserveStationTable = new ReserveStationTable(false);

        add(reserveStationTable, "growx,pushx");
    }
}
