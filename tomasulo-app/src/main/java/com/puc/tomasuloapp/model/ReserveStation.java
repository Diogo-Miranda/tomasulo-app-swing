package com.puc.tomasuloapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

  public boolean isEqualToInstruction(Instruction instruction, boolean isLoadType, List<Register> registers) {
    if (!isLoadType) {
        var outro = getCorrespondente(registers);
      return outro.getOp() != null // TODO não ta verificando o regDestiny
          && outro.getOp().equals(instruction.getIdentifier().getIdentifier())
          && outro.getVj().equals(instruction.getRegOne())
          && outro.getVk().equals(instruction.getRegTwo());
    }

    if (this.getA() == null) {
      return false;
    }
    String[] regs = this.getA().split("\\+");
    return instruction.getRegDestiny().equals(this.getDest())
        && regs[0].equals(instruction.getRegOne())
        && regs[1].equals(instruction.getImmediate());
  }

  private ReserveStation getCorrespondente(List<Register> registers) {

    var outro = new ReserveStation();
    outro.setName(this.name);
    outro.setBusy(this.busy);
    outro.setOp(this.op);
    outro.setVj(this.vj);
    outro.setVk(this.vk);
    outro.setQj(this.qj);
    outro.setQk(this.qk);
    outro.setDest(this.dest);
    outro.setA(this.a);


    registers
        .forEach(
            register -> {
              var instructionValue = register.getInstructionValue();
              if(instructionValue == null) return;
              if (instructionValue.equals(outro.getVj())) outro.setVj(register.getName());
              if (instructionValue.equals(outro.getVk())) outro.setVk(register.getName());
              if (instructionValue.equals(outro.getQj())) outro.setQj(register.getName());
              if (instructionValue.equals(outro.getQk())) outro.setQk(register.getName());
            });

    return outro;
  }
}
