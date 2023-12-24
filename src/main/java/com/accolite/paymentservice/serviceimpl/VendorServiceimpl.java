package com.accolite.paymentservice.serviceimpl;

import com.accolite.paymentservice.config.JwtTokenProvider;
import com.accolite.paymentservice.model.Vendor;
import com.accolite.paymentservice.repository.VendorRepository;
import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.requestbody.RegistrationRequestBody;
import com.accolite.paymentservice.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
@Service
public class VendorServiceimpl implements VendorService {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public Vendor registerVendor(RegistrationRequestBody body) {
        Vendor vendor = null;
        if(vendorRepository.existsByUserName(body.getUserName())){
            vendor = vendorRepository.findByUserName(body.getUserName()).get();
        }else{
            vendor = new Vendor();
            vendor.setCorX(body.getCorX());
            vendor.setCorY(body.getCorY());
            vendor.setUserName(body.getUserName());
            vendor.setPassword(body.getPassword());
            vendor.setBalance(BigDecimal.ZERO);
            vendor.setOfflineBalance(BigDecimal.ZERO);
            vendor.setIsActive(0);
        }
        String secret = jwtTokenProvider.createToken(body.getUserName());
        vendor.setToken(secret);
        vendorRepository.save(vendor);
        return vendor;
    }

    @Override
    public String TransferToStoreWallet(long amount) {
        return null;
    }

    @Override
    public String refreshToken(AuthRequestBody body) {
        Vendor vendor = null;
        if(vendorRepository.existsByUserName(body.getUserName())){
            vendor = vendorRepository.findByUserName(body.getUserName()).get();
            if(!vendor.getPassword().equals(body.getPassword())){
                return "Wrong password !";
            }
        }else{
            return "User not found !";
        }
        String secret = jwtTokenProvider.createToken(body.getUserName());
        vendor.setToken(secret);
        vendorRepository.save(vendor);
        return vendor.getToken();
    }
}
