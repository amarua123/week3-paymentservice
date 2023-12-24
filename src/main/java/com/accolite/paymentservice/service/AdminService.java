package com.accolite.paymentservice.service;


import com.accolite.paymentservice.requestbody.AuthRequestBody;

import java.util.List;

public interface AdminService {
    boolean approveUserRegistration(AuthRequestBody body, String userName);
    List<String> findInActiveUsers(AuthRequestBody body);
    List<String> findActiveUsers(AuthRequestBody body);
    List<String> findAllUsers(AuthRequestBody body);
    boolean approveVendorRegistration(AuthRequestBody body, String userName);
    List<String> findInActiveVendors(AuthRequestBody body);
    List<String> findActiveVendors(AuthRequestBody body);
    List<String> findAllVendors(AuthRequestBody body);
}
