package com.puc.tomasuloapp.factory;

import com.puc.tomasuloapp.enumeration.InstructionsEnum;
import com.puc.tomasuloapp.model.Instruction;
import org.springframework.context.annotation.Bean;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Singleton
public class InstructionsFactory {
    @Bean
    @Named("instructions")
    public List<Instruction> getInstructions() {
        var instructionPath = new ConfigFactory().getConfigs().get("nameInputFile");

        var resourcesPath = "input/";

        var file = new File(Objects.requireNonNull(getClass()
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

    private void makeInstructions(String instruction, String registersExpression, List<Instruction> instructions) {
        String[] registers = registersExpression.split(",");
        if (instruction.equalsIgnoreCase(InstructionsEnum.LW.getIdentifier()) ||instruction.equalsIgnoreCase(InstructionsEnum.SW.getIdentifier()) ) {
            instructions.add(Instruction.builder()
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .regOne(registers[0])
                    .immediate(registers[1])
                    .build());
        } else {
            instructions.add(Instruction.builder()
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .regOne(registers[0])
                    .regTwo(registers[1])
                    .build());
        }
    }
}
