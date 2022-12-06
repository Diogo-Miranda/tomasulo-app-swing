package com.puc.tomasuloapp.panel.algorithm;

import com.puc.tomasuloapp.panel.algorithm.wrapped.RegistersWrapped;

import javax.swing.*;

public class RegistersPanel extends JTabbedPane {

    public RegistersWrapped registersWrapped;

    public RegistersPanel() {
        registersWrapped = new RegistersWrapped();

        add("Registers State", registersWrapped);
    }
}
