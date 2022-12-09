package com.puc.tomasuloapp.model;

import com.puc.tomasuloapp.enumeration.InstructionsEnum;
import io.reactivex.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Instruction {
    private InstructionsEnum identifier;
    private String regDestiny;
    private String regOne;
    @Nullable
    private String regTwo;
    private String immediate;
    private Boolean busy;
    private Integer inOrder;
    private String status;

    public String toString() {
        return new StringBuilder()
                .append(this.identifier.toString())
                .append(" ")
                .append(regDestiny)
                .append(",")
                .append(regOne)
                .append(",")
                .append(regTwo != null ? regTwo : immediate)
                .toString();
    }

    public static Instruction deserialize(Object[] rowData) {
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
