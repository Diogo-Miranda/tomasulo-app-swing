package com.puc.tomasuloapp.util;

import com.puc.tomasuloapp.domain.ISerializable;
import com.puc.tomasuloapp.enumeration.InstructionsEnum;
import com.puc.tomasuloapp.model.Instruction;

public class InstructionUtils implements ISerializable<Instruction> {
    @Override
    public Instruction deserialize(Object[] rowData) {
        // Entrada
        var id = (String) rowData[0];
        // Busy
        var busy = Boolean.valueOf((String) rowData[1]);
        // Instruction
        var instructionExpression = (String) rowData[2];
        // Status
        var status = (String) rowData[3];

        var instructionValue = (String) rowData[5];

        String[] instructionSplited = instructionExpression.split(" ");
        var instruction = instructionSplited[0];
        var regs = instructionSplited[1].split(",");

        if(instruction.equalsIgnoreCase(InstructionsEnum.LW.name())
                || instruction.equalsIgnoreCase(InstructionsEnum.SW.name())) {
            return Instruction.builder()
                    .id(id)
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .busy(busy)
                    .regDestiny(regs[0])
                    .regOne(regs[1])
                    .immediate(regs[2])
                    .status(status)
                    .instructionValue(instructionValue)
                    .build();
        } else {
            return Instruction.builder()
                    .id(id)
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .busy(busy)
                    .regDestiny(regs[0])
                    .regOne(regs[1])
                    .regTwo(regs[2])
                    .status(status)
                    .instructionValue(instructionValue)
                    .build();
        }
    }

    public String[] serialize(Instruction instruction) {
        String[] data = new String[5];
        data[0] = instruction.busyToString();
        data[1] = instruction.toString();
        data[2] = instruction.getStatus();
        data[3] = instruction.getRegDestiny();
        data[4] = "";
        data[5] = instruction.getInstructionValue();
        return data;
    }
}
