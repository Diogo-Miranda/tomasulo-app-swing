package com.puc.tomasuloapp.panel.algorithm.table;


import com.puc.tomasuloapp.component.CoreComponent;
import com.puc.tomasuloapp.domain.ITable;
import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.util.RegistersUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistersTable extends JScrollPane {

    protected DefaultTableModel defaultTableModel;
    protected JTable registersTable;
    private static final int NUMBER_OF_REGISTERS = CoreComponent.numberOfRegs;

    public RegistersTable(boolean editable) {
        defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
        };

        for(int i = 0; i < NUMBER_OF_REGISTERS; i++) {
            defaultTableModel.addColumn("F" + i);
        }

        registersTable = new JTable(defaultTableModel);

        setViewportView(registersTable);

        registersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public DefaultTableModel getModel() {
        return defaultTableModel;
    }

    public void addRow(List<Register> registers) {
        addRow(registers, true);
    }

    public List<Register> getRegisters() {
        List<Register> registers = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_REGISTERS; i++) {
          var object = getModel().getValueAt(0, i);
          Register register;
          if(object == null) register = new Register();
          else {
              register = new RegistersUtils().deserialize(object);
          }
          register.setName(getModel().getColumnName(i));
          registers.add(register);
        }

        return registers;
    }

    public int getRowCount() {
        return getModel().getRowCount();
    }

    public void addRow(List<Register> registers, boolean withFocus) {
        Object[] rowData = new Object[NUMBER_OF_REGISTERS];

        getModel().addRow(rowData);
    }

  public void updateTable(List<Register> registers) {

    for (int colunm = 0; colunm < registers.size(); colunm++) {
      var register = registers.get(colunm);
      getModel().setValueAt(register.getInstructionValue(), 0, colunm);
    }
  }
}
