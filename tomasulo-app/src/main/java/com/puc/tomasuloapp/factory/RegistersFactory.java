package com.puc.tomasuloapp.factory;

import com.puc.tomasuloapp.model.Register;
import com.puc.tomasuloapp.panel.algorithm.AlgorithmPanel;
import org.springframework.context.annotation.Bean;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
@Singleton
public class RegistersFactory {
    @Bean
    @Named("registers")
    public ArrayList<Register> makeRegisterTable() {
        var registers = new ArrayList<Register>();
        for (int i = 0; i < 10; i++) {
            registers.add(Register.builder()
                    .reorderNumber(null)
                    .busy(false)
                    .build());
        }
        return registers;
    }
}
