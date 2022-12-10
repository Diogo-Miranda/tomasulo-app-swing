package com.puc.tomasuloapp.tomasulo;

import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.model.ReserveStation;
import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;
import com.puc.tomasuloapp.panel.algorithm.table.RegistersTable;
import com.puc.tomasuloapp.panel.algorithm.table.ReorderBufferTable;
import com.puc.tomasuloapp.panel.algorithm.table.ReserveStationTable;
import com.puc.tomasuloapp.util.RegistersUtils;
import com.puc.tomasuloapp.util.SerializableUtils;

import java.util.List;

public class TomasuloAlgorithm {
    private final String BUSY_ROW = RegistersTable.VISITOR_BUSY;

    private final String REORD_ROW = RegistersTable.VISITOR_REORD;

    private ReorderBufferTable reorderBufferTable;
    private RegistersTable registersTable;
    private ReserveStationTable reserveStationTable;
    private final SerializableUtils<Instruction> instructionSerializable;
    private final SerializableUtils<ReserveStation> reserveStationSerializable;
    private final RegistersUtils registersSerializable;

    public TomasuloAlgorithm(AlgorithmPanel algorithmPanel) {
        reorderBufferTable = algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable;
        registersTable = algorithmPanel.registersPanel.registersWrapped.registersTable;
        reserveStationTable = algorithmPanel.reserveStationPanel.reserveStationWrapped.reserveStationTable;
        instructionSerializable = new SerializableUtils<>();
        reserveStationSerializable = new SerializableUtils<>();
        registersSerializable = new RegistersUtils();
    }

    public void run(List<Instruction> instructions,
                    List<Register> registers,
                    List<ReserveStation> reserveStations) {

    }
}
