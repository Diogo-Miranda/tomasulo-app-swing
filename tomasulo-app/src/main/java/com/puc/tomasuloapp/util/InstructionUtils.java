package com.puc.tomasuloapp.util;

import com.puc.tomasuloapp.domain.ISerializable;
import com.puc.tomasuloapp.enumeration.InstructionsEnum;
import com.puc.tomasuloapp.model.Instruction;

public class InstructionUtils implements ISerializable<Instruction> {
    @Override
    public Instruction deserialize(Object[] rowData) {
        // Entrada
        var instructionCount = (String) rowData[0];
        // Busy
        var busy = Boolean.valueOf((String) rowData[1]);
        // Instruction
        var instructionExpression = (String) rowData[2];
        // Status
        var status = (String) rowData[3];

        String[] instructionSplited = instructionExpression.split(" ");
        var instruction = instructionSplited[0];
        var regs = instructionSplited[1].split(",");

        if(instruction.equalsIgnoreCase(InstructionsEnum.LW.name())
                || instruction.equalsIgnoreCase(InstructionsEnum.SW.name())) {
            return Instruction.builder()
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .busy(busy)
                    .regDestiny(regs[0])
                    .regOne(regs[1])
                    .immediate(regs[2])
                    .status(status)
                    .build();
        } else {
            return Instruction.builder()
                    .identifier(InstructionsEnum.valueOf(instruction))
                    .busy(busy)
                    .regDestiny(regs[0])
                    .regOne(regs[1])
                    .regTwo(regs[2])
                    .status(status)
                    .build();
        }
    }
}
