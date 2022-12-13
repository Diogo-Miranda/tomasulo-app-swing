package com.puc.tomasuloapp.component;

import com.puc.tomasuloapp.enumeration.InstructionsEnum;
import com.puc.tomasuloapp.factory.ConfigFactory;
import com.puc.tomasuloapp.model.Instruction;
import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.model.ReserveStation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.io.IOException;

public class CoreComponent {

    public static Integer numberOfRegs = 10;

    public static List<ReserveStation> initReserveStation() {
        var reserveStationPath = new ConfigFactory().getConfigs().get("nameReserveStation");

        var resourcesPath = "input/";

        var file = new File(Objects.requireNonNull(CoreComponent.class
                        .getClassLoader()
                        .getResource(resourcesPath + reserveStationPath + ".txt"))
                .getFile());

        var reserveStationsResponse = new ArrayList<ReserveStation>();

        try {
            var reader = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = reader.readLine()) != null) {
                reserveStationsResponse.add(makeReserveStation(line));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return reserveStationsResponse;
    }

    public static List<Instruction> initInstructions() {
        var instructionPath = new ConfigFactory().getConfigs().get("nameInputFile");

        var resourcesPath = "input/";

        var file = new File(Objects.requireNonNull(CoreComponent.class
                        .getClassLoader()
                        .getResource(resourcesPath + instructionPath + ".txt"))
                .getFile());

        var instructionsResponse = new ArrayList<Instruction>();

        try {
            var reader = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = reader.readLine()) != null) {
                var expression = line.split(" ");
                var instruction = expression[0];
                var registers = expression[1];
                makeInstructions(instruction, registers, instructionsResponse);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return instructionsResponse;
    }

    private static ReserveStation makeReserveStation(String reserveStationName) {
        return ReserveStation.builder()
                .name(reserveStationName)
                .busy(false)
                .build();
    }

    private static void makeInstructions(String instruction, String registersExpression, List<Instruction> instructions) {
        String[] registers = registersExpression.split(",");
        if (instruction.equalsIgnoreCase(InstructionsEnum.LW.getIdentifier()) ||instruction.equalsIgnoreCase(InstructionsEnum.SW.getIdentifier()) ) {
            instructions.add(Instruction.builder()
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .regDestiny(registers[0])
                    .regOne(registers[1])
                    .immediate(registers[2])
                    .busy(false)
                    .build());
        } else {
            instructions.add(Instruction.builder()
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .regDestiny(registers[0])
                    .regOne(registers[1])
                    .regTwo(registers[2])
                    .busy(false)
                    .build());
        }
    }

    public static ArrayList<Register> initRegisters() {
        var registers = new ArrayList<Register>();
        for (int i = 0; i < numberOfRegs; i++) {
            registers.add(Register.builder().name("F" + 0)
                    .instructionValue(null)
                    .build());
        }
        return registers;
    }
}
