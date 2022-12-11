package com.puc.tomasuloapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveStation {
    private String name;
    private Boolean busy;
    private String op;
    private String vj;
    private String vk;
    private String qj;
    private String qk;
    private String dest;
    private String a;

    public String busyToString() {
        return busy ? "true" : "false";
    }

    public void setAllFieldsNull() {
        this.busy = false;
        this.op = null;
        this.vj = null;
        this.vk = null;
        this.qj = null;
        this.qk = null;
        this.dest = null;
        this.a = null;
    }

  public boolean isEqualToInstruction(Instruction instruction, boolean isLoadType) {
    if (!isLoadType) {
      return this.getOp() != null // TODO não ta verificando o regDestiny
          && this.getOp().equals(instruction.getIdentifier().getIdentifier())
          && this.getVj().equals(instruction.getRegOne())
          && this.getVk().equals(instruction.getRegTwo());
    }

    if (this.getA() == null) {
      return false;
    }
    String[] regs = this.getA().split("\\+");
    return instruction.getRegDestiny().equals(this.getDest())
        && regs[0].equals(instruction.getRegOne())
        && regs[1].equals(instruction.getImmediate());
  }
}
