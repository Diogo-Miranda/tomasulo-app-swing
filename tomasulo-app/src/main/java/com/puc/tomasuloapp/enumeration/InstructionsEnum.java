package com.puc.tomasuloapp.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum InstructionsEnum {
    LW("LW"),
    SW("SW"),
    ADD("ADD"),
    SUB("SUB"),
    DIV("DIV"),
    MUL("MUL");

    private String identifier;
}
