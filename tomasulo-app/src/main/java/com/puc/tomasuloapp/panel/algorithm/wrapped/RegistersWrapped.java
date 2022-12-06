package com.puc.tomasuloapp.panel.algorithm.wrapped;

import com.puc.tomasuloapp.panel.algorithm.table.RegistersTable;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class RegistersWrapped extends JPanel {
    public RegistersTable registersTable;

    public RegistersWrapped() {
        super(new MigLayout());

        registersTable = new RegistersTable(false);

        add(registersTable, "growx,pushx");
    }
}
