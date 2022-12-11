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

import java.util.*;
import java.util.stream.Collectors;

public class TomasuloAlgorithm {

    private ReorderBufferTable reorderBufferTable;
    private RegistersTable registersTable;
    private ReserveStationTable reserveStationTable;
    private final SerializableUtils<Instruction> instructionSerializable;
    private final SerializableUtils<ReserveStation> reserveStationSerializable;
    private final RegistersUtils registersSerializable;
    private final Queue<Instruction> instructionQueue;

    private static final HashMap<String, Integer> clocksToFinish = new HashMap<>();;

    public TomasuloAlgorithm(AlgorithmPanel algorithmPanel, Queue<Instruction> instructionQueue) {

        this.instructionQueue = instructionQueue;
        reorderBufferTable = algorithmPanel.reorderBufferPanel.reorderBufferWrapped.reorderBufferTable;
        registersTable = algorithmPanel.registersPanel.registersWrapped.registersTable;
        reserveStationTable = algorithmPanel.reserveStationPanel.reserveStationWrapped.reserveStationTable;
        instructionSerializable = new SerializableUtils<>();
        reserveStationSerializable = new SerializableUtils<>();
        registersSerializable = new RegistersUtils();
    }

  public void run(
      List<Instruction> instructions,
      List<Register> registers,
      List<ReserveStation> reserveStations) {

    // Carregar clocks que faltam para cada instrucao
    // TODO pegar essa config de um arquivo
    initClocksToFinish(instructions);

    // Criação de variaveis de instrucao
    var instructionIndex =
        instructionQueue.peek() == null ? -1 : instructions.indexOf(instructionQueue.peek());
    var instruction = instructionIndex < 0 ? null : instructions.get(instructionIndex);
    var previousInstruction =
        instructionIndex <= 0 ? instruction : instructions.get(instructionIndex - 1);

    // Primeiras validações, ajustes de clock e mudança de estado dos componentes e atualiza valor do registrador
    checkAndUpdateInstructions(instructions);
    checkAndUpdateReserveStations(instructions, reserveStations);
    checkAndUpdateRegisters(instructions, registers);
    reorderBufferTable.updateTable(instructions);
    reserveStationTable.updateTable(reserveStations);
    registersTable.updateTable(registers);

    // Validar se a instrucao não possui dependencias
    if (!validateNextInstruction(previousInstruction, instruction) && instructionIndex != 0) {
      return;
    }

    // Validar se a instrução possui unidades funcionais livres
    var emptyReserveStation = getEmptyReserveStation(reserveStations, instruction);
    if (emptyReserveStation.isEmpty()) {
      return;
    }

    // Ir para a proxima Instrucao
    instructionQueue.remove();

    // Atualizar reserveStation que estava disponivel para a instrucao pega da fila
    updateReserveStations(instruction, emptyReserveStation.get());
    reserveStationTable.updateTable(reserveStations);

    // Atualizar instrucao atual que foi pega da fila
    instruction.setBusy(true);
    instruction.setStatus("Executing");
    reorderBufferTable.updateTable(instructions);

    // Atualizar registradores
    var register = findRegisterByName(registers, instruction);
    register.setInstructionValue(emptyReserveStation.get().getName());
    registersTable.updateTable(registers);
  }

  private void checkAndUpdateRegisters(List<Instruction> instructions, List<Register> registers) {
    instructions.stream().filter(
            instruction1 ->
                instruction1.getStatus().equals("Commit")).forEach(
                    instruction1 ->
                        updateRegisterValue(registers, instruction1));
  }

  private void checkAndUpdateReserveStations(
      List<Instruction> instructions, List<ReserveStation> reserveStations) {

    reserveStations.forEach(
        reserveStation ->
            instructions.forEach(
                instruction -> {
                  boolean isLoadType = instruction.isLoadType();
                  if (!instruction.getBusy()
                      && reserveStation.isEqualToInstruction(instruction, isLoadType)) {
                    reserveStation.setAllFieldsNull();
                  }
                }));
  }

  private void checkAndUpdateInstructions(List<Instruction> instructions) {

    // Pegar as instrucoes que ja estao sendo executadas
    var diferentes =
        instructions.stream()
            .filter(element -> !instructionQueue.contains(element))
            .collect(Collectors.toList());

    // Diminuir o clock restante de cada instrucao que esta sendo/foi executada
    diferentes.forEach(
        instruction ->
            clocksToFinish.replace(
                instruction.getId(), clocksToFinish.get(instruction.getId()) - 1));

    // Mudar status das instrucoes
    clocksToFinish.entrySet().forEach(entry -> updateInstructions(instructions, entry));
  }

  private void updateInstructions(
      List<Instruction> instructions, Map.Entry<String, Integer> entry) {
    if (entry.getValue() == 0) {
      Instruction instruction = filterInstructionById(instructions, entry);
      instruction.setStatus("Write Result");
      if (instruction.isLoadType()) {
        instruction.setInstructionValue("MEM[" + instruction.getRegOne().concat("+").concat(instruction.getImmediate()).concat("]"));
      } else {
        instruction.setInstructionValue(instruction.getRegOne().concat(instruction.getIdentifier().getSimbol().concat(instruction.getRegTwo())));
      }
    } else if (entry.getValue() < 0) {
      Instruction instruction = filterInstructionById(instructions, entry);
      instruction.setStatus("Commit");
      instruction.setBusy(false);
    }
  }

  private void updateRegisterValue(List<Register> registers, Instruction instruction)
  {
    var register = findRegisterByName(registers, instruction);
    var registerValue = register.getInstructionValue();
    register.setInstructionValue(registerValue.contains("VAL") ? registerValue : "VAL("+register.getInstructionValue().concat(")"));
  }
  private static Instruction filterInstructionById(
      List<Instruction> instructions, Map.Entry<String, Integer> entry) {
    return instructions.stream()
        .filter(instruction -> instruction.getId().equals(entry.getKey()))
        .findFirst()
        .get();
  }

  private void initClocksToFinish(List<Instruction> instructions) {
    // FIXME gambiarra
    var instructionIndex =
        instructionQueue.peek() == null ? -1 : instructions.indexOf(instructionQueue.peek());
    if (instructionIndex == 0) {
      var instructionsList = instructionQueue.stream().collect(Collectors.toList());
      clocksToFinish.put(instructionsList.get(0).getId(), 2);
      clocksToFinish.put(instructionsList.get(1).getId(), 5);
      clocksToFinish.put(instructionsList.get(2).getId(), 4);
      clocksToFinish.put(instructionsList.get(3).getId(), 1);
      clocksToFinish.put(instructionsList.get(4).getId(), 3);
      clocksToFinish.put(instructionsList.get(5).getId(), 1);
    }
  }

  private static Register findRegisterByName(List<Register> registers, Instruction instruction) {
    return registers.stream()
        .filter(r -> r.getName().equals(instruction.getRegDestiny()))
        .findFirst()
        .get();
  }

  private boolean validateNextInstruction(
      Instruction previousInstruction, Instruction nextInstruction) {

    if (previousInstruction == null || nextInstruction == null) {
      return false;
    }
    var previousInstructionReg = previousInstruction.getRegDestiny();

    // TODO Conferir se precisa olhar mais dependencias (ta olhando só de uma instrucao anterior)
    if ((previousInstructionReg.equals(nextInstruction.getRegOne())
            || previousInstructionReg.equals(nextInstruction.getRegTwo()))
        && clocksToFinish.get(previousInstruction.getId()) != 0) {
      return false;
    }
    return true;
  }

  private void updateReserveStations(Instruction instruction, ReserveStation reserveStation) {

    reserveStation.setBusy(true);

    if (instruction.isLoadType()) {
      reserveStation.setDest(instruction.getRegDestiny());
      reserveStation.setA(instruction.getRegOne().concat("+").concat(instruction.getImmediate()));
    } else {
      // TODO verificar se tem dependencia e atualizar o Qj e Qk
      reserveStation.setOp(instruction.getIdentifier().getIdentifier());
      reserveStation.setVj(instruction.getRegOne());
      reserveStation.setVk(instruction.getRegTwo());
    }
  }

  private static Optional<ReserveStation> getEmptyReserveStation(
      List<ReserveStation> reserveStations, Instruction instruction) {
    return reserveStations.stream()
        .filter(
            reserveStation ->
                reserveStation
                        .getName()
                        .contains(instruction.getIdentifier().getFunctionalUnitName())
                    && !reserveStation.getBusy())
        .findFirst();
  }
}
