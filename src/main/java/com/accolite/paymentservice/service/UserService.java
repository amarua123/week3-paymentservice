package com.accolite.paymentservice.service;


import com.accolite.paymentservice.model.User;
import com.accolite.paymentservice.model.Vendor;
import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.requestbody.RegistrationRequestBody;

import java.math.BigDecimal;

public interface UserService {
    User registerUser(RegistrationRequestBody body);
    String deposit(String userName, BigDecimal amount);
    String refreshToken(AuthRequestBody body);
    String onlinePayment(String userName, Vendor vendor, BigDecimal amount);
}
