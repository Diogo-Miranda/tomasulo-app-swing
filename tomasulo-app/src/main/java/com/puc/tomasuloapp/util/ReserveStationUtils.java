package com.puc.tomasuloapp.util;

import com.puc.tomasuloapp.domain.ISerializable;
import com.puc.tomasuloapp.model.ReserveStation;

public class ReserveStationUtils implements ISerializable<ReserveStation> {
    @Override
    public ReserveStation deserialize(Object[] rowData) {
        var name = (String) rowData[0];
        var busy = (String) rowData[1];
        var op = (String) rowData[2];
        var vj = (String) rowData[3];
        var vk = (String) rowData[4];
        var qj = (String) rowData[5];
        var qk = (String) rowData[6];
        var dest = (String) rowData[7];
        var a = (String) rowData[8];

        return ReserveStation.builder()
                .name(name)
                .busy(Boolean.parseBoolean(busy))
                .op(op)
                .vj(vj)
                .vk(vk)
                .qj(qj)
                .qk(qk)
                .dest(dest)
                .a(a)
                .build();
    }
}
