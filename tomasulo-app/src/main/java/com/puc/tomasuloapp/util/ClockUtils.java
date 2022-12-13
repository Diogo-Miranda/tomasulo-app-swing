package com.puc.tomasuloapp.util;

import com.puc.tomasuloapp.model.Instruction;

public class ClockUtils {
    public static Integer getClocksToFinish(Instruction instruction) {
        return instruction.getIdentifier().getCiclos();
    }
}
