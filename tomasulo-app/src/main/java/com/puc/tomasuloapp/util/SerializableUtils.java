package com.puc.tomasuloapp.util;

import com.puc.tomasuloapp.domain.ISerializable;
import com.puc.tomasuloapp.domain.ITable;

import java.util.ArrayList;
import java.util.List;

public class SerializableUtils<T> {
    public List<T> deserialize(ITable<T> table, ISerializable<T> serializable) {
        var listInstructions = new ArrayList<T>();
        for (int row = 0; row < table.getRowCount(); row++) {
            listInstructions.add(serializable.deserialize(table.getRow(row)));
        }
        return listInstructions;
    }

    public String[] serialize(T input, ISerializable<T> serializable) {
        return serializable.serialize(input);
    }
}
