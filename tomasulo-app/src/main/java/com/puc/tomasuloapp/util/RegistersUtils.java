package com.puc.tomasuloapp.util;

import com.puc.tomasuloapp.component.CoreComponent;
import com.puc.tomasuloapp.domain.ISerializable;
import com.puc.tomasuloapp.domain.ITable;
import com.puc.tomasuloapp.model.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistersUtils {

  public Register deserialize(Object object) {
      return Register.builder().instructionValue(String.valueOf((object))).build();
  }
}
