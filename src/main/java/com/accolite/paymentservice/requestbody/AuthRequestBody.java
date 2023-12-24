package com.accolite.paymentservice.requestbody;

import lombok.Data;

@Data
public class AuthRequestBody {
    private String userName;
    private String password;
}
