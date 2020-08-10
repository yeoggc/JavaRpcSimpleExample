package com.ggc.rpc_example.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class User implements Serializable {
    private String name;
    private String gender;

}
