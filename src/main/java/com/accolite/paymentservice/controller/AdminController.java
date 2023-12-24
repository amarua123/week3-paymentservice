package com.accolite.paymentservice.controller;

import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/findUsers")
    public List<String> findUsers(@RequestBody AuthRequestBody body){
        return adminService.findAllUsers(body);
    }
    @PostMapping("/findVendors")
    public List<String> findVendors(@RequestBody AuthRequestBody body){
        return adminService.findAllVendors(body);
    }
    @PostMapping("/findActiveUsers")
    public List<String> findActiveUsers(@RequestBody AuthRequestBody body){
        return adminService.findActiveUsers(body);
    }
    @PostMapping("/findActiveVendors")
    public List<String> findActiveVendors(@RequestBody AuthRequestBody body){
        return adminService.findActiveVendors(body);
    }
    @PostMapping("/findInActiveUsers")
    public List<String> findInActiveUsers(@RequestBody AuthRequestBody body){
        return adminService.findInActiveUsers(body);
    }
    @PostMapping("/findInActiveVendors")
    public List<String> findInActiveVendors(@RequestBody AuthRequestBody body){
        return adminService.findInActiveVendors(body);
    }
    @PostMapping("/approve/user/{userName}")
    public String reviewUserRegistration(@RequestBody AuthRequestBody body, @PathVariable String userName){
        if(adminService.approveUserRegistration(body, userName)){
            return "Now the "+userName+" is ready to use.";
        }
        return "Username/password is incorrect or UserName doesn't exists !";
    }
    @PostMapping("/approve/vendor/{userName}")
    public String reviewVendorRegistration(@RequestBody AuthRequestBody body, @PathVariable String userName){
        if(adminService.approveVendorRegistration(body, userName)){
            return "Now the "+userName+" is ready to use.";
        }
        return "Username/password is incorrect or UserName doesn't exists !";
    }
}
