package com.puc.tomasuloapp.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum InstructionsEnum {
  LW("LW", "LOAD", ""),
  SW("SW", "LOAD", ""),
  ADD("ADD", "ADD", "+"),
  SUB("SUB", "ADD", "-"),
  DIV("DIV", "MULT", "/"),
  MUL("MUL", "MULT", "*");

  private String identifier;
  private String functionalUnitName;
  private String simbol;
}
