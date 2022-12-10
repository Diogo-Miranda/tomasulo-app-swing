package com.puc.tomasuloapp.util;

import com.puc.tomasuloapp.component.CoreComponent;
import com.puc.tomasuloapp.domain.ISerializable;
import com.puc.tomasuloapp.model.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistersUtils {
    public List<Register> deserialize(Object[] reorderInfoRow, Object[] busyInfoRow) {
        var registers = new ArrayList<Register>();

        for (int i = 0; i < CoreComponent.numberOfRegs; i++) {
            var reg = Register.builder().name("F" + i).build();
            var reorderInfo = (String) reorderInfoRow[i];
            if (Objects.nonNull(reorderInfo) && !reorderInfo.equals(""))
                reg.setReorderNumber(Integer.parseInt(reorderInfo));
            var busyInfo = busyInfoRow[i];
            reg.setBusy(Boolean.parseBoolean((String) busyInfo));
            registers.add(reg);
        }

        return registers;
    }
}
