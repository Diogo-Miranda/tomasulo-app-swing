package com.puc.tomasuloapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveStation {
    private String name;
    private Boolean busy;
    private String op;
    private String vj;
    private String vk;
    private String qj;
    private String qk;
    private String dest;
    private String a;

}
