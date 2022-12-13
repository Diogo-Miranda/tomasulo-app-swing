package com.puc.tomasuloapp.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.puc.tomasuloapp.AppConstants.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum InstructionsEnum {
  LW("LW", "LOAD", "", MEMORY_CYCLES),
  SW("SW", "LOAD", "", MEMORY_CYCLES),
  ADD("ADD", "ADD", "+", INTEGER_CYCLES),
  SUB("SUB", "ADD", "-", INTEGER_CYCLES),
  DIV("DIV", "MULT", "/", FLOAT_CYCLES),
  MUL("MUL", "MULT", "*", FLOAT_CYCLES),
  BEQ("BEQ", "BEQ", "", BRANCH_CYCLES),
  BNE("BNE", "BNE", "", BRANCH_CYCLES);

  private String identifier;
  private String functionalUnitName;
  private String simbol;
  private Integer ciclos;
}
