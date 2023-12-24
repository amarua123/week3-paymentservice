package com.accolite.paymentservice.service;
import com.accolite.paymentservice.model.User;
import com.accolite.paymentservice.model.Vendor;
import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.requestbody.RegistrationRequestBody;

import java.math.BigDecimal;

public interface VendorService {

    Vendor registerVendor(RegistrationRequestBody body);

    String TransferToStoreWallet(long amount);
    String refreshToken(AuthRequestBody body);
}
