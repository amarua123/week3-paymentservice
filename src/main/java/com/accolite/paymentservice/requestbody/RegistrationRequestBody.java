package com.accolite.paymentservice.requestbody;

import lombok.Data;

import java.io.Serializable;
@Data
public class RegistrationRequestBody implements Serializable {
    private String userName;
    private String password;
    private Long corX;
    private Long corY;
}
