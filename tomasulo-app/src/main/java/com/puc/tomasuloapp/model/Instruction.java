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
    private String id;
    private InstructionsEnum identifier;
    private String regDestiny;
    private String regOne;
    @Nullable
    private String regTwo;
    private String immediate;
    private Boolean busy;
    private Integer inOrder;
    private String status;
    private String instructionValue;

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

    public String busyToString() {
        return busy ? "true" : "false";
    }

    @Override
    public boolean equals(Object object) {
        if(object == null) return false;

        var instruction = (Instruction) object;

        return this.getId().equals(((Instruction) object).getId());
    }

    public boolean isLoadType() {
        return this.getIdentifier().getFunctionalUnitName().equals("LOAD");
    }

}
