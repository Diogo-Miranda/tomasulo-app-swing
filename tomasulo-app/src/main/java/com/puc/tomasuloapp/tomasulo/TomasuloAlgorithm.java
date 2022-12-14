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

import javax.swing.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.puc.tomasuloapp.util.ClockUtils.getClocksToFinish;

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
    initClocksToFinish(instructions);

    // Criação de variaveis de instrucao
    var instructionIndex =
        instructionQueue.peek() == null ? -1 : instructions.indexOf(instructionQueue.peek());
    var instruction = instructionIndex < 0 ? null : instructions.get(instructionIndex);
    var previousInstructions = instructions.stream()
            .filter(element -> !instructionQueue.contains(element))
            .collect(Collectors.toList());

    // Primeiras validações, ajustes de clock e mudança de estado dos componentes e atualiza valor do registrador
    checkAndUpdateInstructions(instructions);
    checkAndUpdateReserveStationWithVal(instructions, registers, reserveStations);
    checkAndUpdateReserveStations(instructions, reserveStations, registers);
    checkAndUpdateRegisters(instructions, registers);
    reorderBufferTable.updateTable(instructions);
    reserveStationTable.updateTable(reserveStations);
    registersTable.updateTable(registers);

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

    // Validar se a instrucao não possui dependencias
    if (!validateNextInstructions(previousInstructions, instruction) && instructionIndex != 0) {
      updateReserveStationsQjAndQk(previousInstructions, instruction, reserveStations, emptyReserveStation.get());
      reserveStationTable.updateTable(reserveStations);
      instruction.setBusy(true);
      instruction.setStatus("Executing");
      var register = findRegisterByName(registers, instruction);
      register.setInstructionValue(emptyReserveStation.get().getName());
      registersTable.updateTable(registers);
      reorderBufferTable.updateTable(instructions);
      return;
    }

    // Atualizar instrucao atual que foi pega da fila
    instruction.setBusy(true);
    instruction.setStatus("Executing");
    reorderBufferTable.updateTable(instructions);

    // Atualizar registradores
    var register = findRegisterByName(registers, instruction);
    register.setInstructionValue(emptyReserveStation.get().getName());
    registersTable.updateTable(registers);
  }

  private void updateReserveStationsQjAndQk(
      List<Instruction> previousInstructions,
      Instruction instruction,
      List<ReserveStation> reserveStations,
      ReserveStation reserveStation) {

    previousInstructions.forEach(
        previousInstruction -> {
          var reserveStationName =
              reserveStations.stream()
                  .filter(
                      reserveStation1 ->
                          reserveStation1
                                  .getName()
                                  .contains(
                                      previousInstruction.getIdentifier().getFunctionalUnitName())
                              && (reserveStation1.getDest() != null && reserveStation1.getDest().equals(previousInstruction.getRegDestiny())))
                  .findFirst();

          if(reserveStationName.isEmpty()) {
            return;
          }

          updateReserveStationsQjAndQk(
              previousInstruction, instruction, reserveStation, reserveStationName.get().getName());
        });
  }

  private void updateReserveStationsQjAndQk(Instruction previousInstruction, Instruction instruction, ReserveStation reserveStation, String reserveStationName)
  {

    var previousInstructionDestiny = previousInstruction.getRegDestiny();
    if (instruction.isLoadType()) {
      return;
    }
    if(previousInstructionDestiny.equals(instruction.getRegOne()) || previousInstructionDestiny.equals(instruction.getRegTwo())) {
      clocksToFinish.replace(instruction.getId(), 1 + clocksToFinish.get(instruction.getId()) + clocksToFinish.get(previousInstruction.getId()));
      instruction.setInstructionValue("");
    }

    if(previousInstructionDestiny.equals(instruction.getRegOne())) {
      //reserveStation.setQj(instruction.getRegOne());
      reserveStation.setQj(reserveStationName);
      reserveStation.setVj("");
    }

    if(previousInstructionDestiny.equals(instruction.getRegTwo())) {
      //reserveStation.setQk(instruction.getRegTwo());
      reserveStation.setQk(reserveStationName);
      reserveStation.setVk("");
    }
    // TODO Verificar todas as instruções anteriores
  }

  private void checkAndUpdateRegisters(List<Instruction> instructions, List<Register> registers) {
    instructions.stream().filter(
            instruction1 ->
                instruction1.getStatus().equals("Commit")).forEach(
                    instruction1 ->
                        updateRegisterValue(registers, instruction1));
  }

  private void checkAndUpdateReserveStations(
      List<Instruction> instructions, List<ReserveStation> reserveStations, List<Register> registers) {

    reserveStations.forEach(
        reserveStation ->
            instructions.forEach(
                instruction -> {
                  boolean isLoadType = instruction.isLoadType();
                  if (!instruction.getBusy()
                      && reserveStation.isEqualToInstruction(instruction, isLoadType, registers)) {
                    reserveStation.setAllFieldsNull();
                  }
                }));
  }

  private void checkAndUpdateReserveStationWithVal(
      List<Instruction> instructions,
      List<Register> registers,
      List<ReserveStation> reserveStations) {

    getFiltredReserveStations(registers, reserveStations);
  }

  private void getFiltredReserveStations(
      List<Register> registers, List<ReserveStation> reserveStations) {

    var b =
        registers.stream()
            .flatMap(
                register ->
                    reserveStations.stream()
                        .filter(
                            reserveStation ->
                                reserveStation.getDest() != null
                                    && reserveStation.getDest().equals(register.getName())));

    b.forEach(
        reserveStation -> {
          var qj = reserveStation.getQj() != null && !reserveStation.getQj().isEmpty() ? reserveStation.getQj() : "xxx";
          var qk = reserveStation.getQk() != null && !reserveStation.getQk().isEmpty() ? reserveStation.getQk() : "xxx";

          var instructionValueQj =
              registers.stream()
                  .filter(
                      register -> {
                        if (register.getInstructionValue() == null) {
                          return false;
                        }
                        return register.getInstructionValue().contains("VAL(".concat(qj));
                      })
                  .findFirst();

          var instructionValueQk =
              registers.stream()
                  .filter(
                      register -> {
                        if (register.getInstructionValue() == null) {
                          return false;
                        }
                        return register.getInstructionValue().contains("VAL(".concat(qk));
                      })
                  .findFirst();

          registers.forEach(
              register -> {
                if (instructionValueQj.isPresent()) {
                  var instructionValue = instructionValueQj.get().getInstructionValue();
                  reserveStation.setVj(instructionValue);
                  reserveStation.setQj("");
                }

                if (instructionValueQk.isPresent()) {
                  var instructionValue = instructionValueQk.get().getInstructionValue();
                  reserveStation.setVk(instructionValue);
                  reserveStation.setQk("");
                }
              });
        });
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
    if(registerValue == null) {
      return;
    }
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
    var instructionIndex =
        instructionQueue.peek() == null ? -1 : instructions.indexOf(instructionQueue.peek());
    if (instructionIndex == 0) {
      var instructionsList = instructionQueue.stream().collect(Collectors.toList());
      for (int i = 0; i < instructionsList.size(); i++) {
        clocksToFinish.put(instructionsList.get(i).getId(), getClocksToFinish(instructionsList.get(i)));
      }
    }
  }

  private static Register findRegisterByName(List<Register> registers, Instruction instruction) {
    return registers.stream()
        .filter(r -> r.getName().equals(instruction.getRegDestiny()))
        .findFirst()
        .get();
  }

  private boolean validateNextInstructions(
      List<Instruction> previousInstructions, Instruction currentInstruction) {
      AtomicReference<Boolean> isValid = new AtomicReference<>(true);

    previousInstructions.forEach(previousInstruction -> {
      if(!validateNextInstruction(previousInstruction, currentInstruction)) {
        isValid.set(false);
      }
    });

    return isValid.get();
  }

  private boolean validateNextInstruction(
          Instruction previousInstruction, Instruction currentInstruction) {

    if (previousInstruction == null || currentInstruction == null) {
      return false;
    }
    var previousInstructionReg = previousInstruction.getRegDestiny();



    // TODO Conferir se precisa olhar mais dependencias (ta olhando só de uma instrucao anterior)
    if ((previousInstructionReg.equals(currentInstruction.getRegOne())
            || previousInstructionReg.equals(currentInstruction.getRegTwo()))
            && clocksToFinish.get(previousInstruction.getId()) != 0) {
      return false;
    }
    return true;
  }

  private void updateReserveStations(Instruction instruction, ReserveStation reserveStation) {

    reserveStation.setBusy(true);

    reserveStation.setDest(instruction.getRegDestiny());
    if (instruction.isLoadType()) {
      reserveStation.setA(instruction.getRegOne().concat("+").concat(instruction.getImmediate()));
    } else {
      reserveStation.setOp(instruction.getIdentifier().getIdentifier());
      reserveStation.setVj(instruction.getRegOne());
      reserveStation.setVk(instruction.getRegTwo());
    }
  }

  private static Optional<ReserveStation> getEmptyReserveStation(
      List<ReserveStation> reserveStations, Instruction instruction) {
      if(instruction == null){
        return Optional.empty();
      }
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
