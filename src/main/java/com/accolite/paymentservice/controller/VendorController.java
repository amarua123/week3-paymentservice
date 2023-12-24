package com.accolite.paymentservice.controller;

import com.accolite.paymentservice.model.Vendor;
import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.requestbody.RegistrationRequestBody;
import com.accolite.paymentservice.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @PostMapping("/register")
    public ResponseEntity<String> registerVendor(@RequestBody RegistrationRequestBody body) {
        Vendor vendor = vendorService.registerVendor(body);
//        System.out.println(jwtTokenProvider.decodeToken(vendor.getUserSecret()).getSubject());
        return ResponseEntity.ok("vendor registered. UserSecret: " + vendor.getToken());
    }
    @PostMapping("/refreshToken")
    public @ResponseBody String refreshToken(@RequestBody AuthRequestBody body){
        return vendorService.refreshToken(body);
    }
}
