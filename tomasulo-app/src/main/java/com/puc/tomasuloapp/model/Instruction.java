package com.puc.tomasuloapp.model;

import com.puc.tomasuloapp.enumeration.InstructionsEnum;
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
    private String regOne;
    private String regTwo;
    private String immediate;
    private Boolean busy;
    private Integer inOrder;
    private String status;

    public String toString() {
        return new StringBuilder()
                .append(this.identifier.toString())
                .append(" ")
                .append(regOne)
                .append(",")
                .append(regTwo != null ? regTwo : immediate)
                .toString();
    }
}
