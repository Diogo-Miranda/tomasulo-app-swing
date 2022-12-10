package com.puc.tomasuloapp.domain;
public interface ISerializable<T> {
    T deserialize(Object[] rowData);
    String[] serialize(T input);
}
