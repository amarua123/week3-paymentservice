package com.accolite.paymentservice.serviceimpl;

import com.accolite.paymentservice.model.Admin;
import com.accolite.paymentservice.model.User;
import com.accolite.paymentservice.model.Vendor;
import com.accolite.paymentservice.repository.AdminRepository;
import com.accolite.paymentservice.repository.UserRepository;
import com.accolite.paymentservice.repository.VendorRepository;
import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceimpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public boolean approveUserRegistration(AuthRequestBody body, String userName) {
        if(!verify(body)){
            return false;
        }
        if(userRepository.existsByUserName(userName)){
            Optional<User> user = userRepository.findByUserName(userName);
            User curUser = user.get();
            curUser.setIsActive(1);
            userRepository.save(curUser);
            return true;
        }
        return false;
    }

    @Override
    public List<String> findInActiveUsers(AuthRequestBody body) {
        List<String> allUsers = new ArrayList<>();
        if(!verify(body)){
            return allUsers;
        }
        for(User user: userRepository.findAllByIsActive(0)){
            allUsers.add(user.getUserName());
        }
        return allUsers;
    }

    @Override
    public List<String> findActiveUsers(AuthRequestBody body) {
        List<String> allUsers = new ArrayList<>();
        if(!verify(body)){
            return allUsers;
        }
        for(User user: userRepository.findAllByIsActive(1)){
            allUsers.add(user.getUserName());
        }
        return allUsers;
    }

    @Override
    public List<String> findAllUsers(AuthRequestBody body) {
        List<String> allUsers = new ArrayList<>();
        if(!verify(body)){
            return allUsers;
        }
        for(User user: userRepository.findAll()){
            allUsers.add(user.getUserName());
        }
        return allUsers;
    }

    @Override
    public boolean approveVendorRegistration(AuthRequestBody body, String userName) {
        if(!verify(body)){
            return false;
        }
        if(vendorRepository.existsByUserName(userName)){
            Optional<Vendor> vendor = vendorRepository.findByUserName(userName);
            Vendor curVendor = vendor.get();
            curVendor.setIsActive(1);
            vendorRepository.save(curVendor);
            return true;
        }
        return false;
    }

    @Override
    public List<String> findInActiveVendors(AuthRequestBody body) {
        List<String> allUsers = new ArrayList<>();
        if(!verify(body)){
            return allUsers;
        }
        for(Vendor vendor: vendorRepository.findAllByIsActive(0)){
            allUsers.add(vendor.getUserName());
        }
        return allUsers;
    }

    @Override
    public List<String> findActiveVendors(AuthRequestBody body) {
        List<String> allUsers = new ArrayList<>();
        if(!verify(body)){
            return allUsers;
        }
        for(Vendor vendor: vendorRepository.findAllByIsActive(1)){
            allUsers.add(vendor.getUserName());
        }
        return allUsers;
    }

    @Override
    public List<String> findAllVendors(AuthRequestBody body) {
        List<String> allUsers = new ArrayList<>();
        if(!verify(body)){
            return allUsers;
        }
        for(Vendor vendor: vendorRepository.findAll()){
            allUsers.add(vendor.getUserName());
        }
        return allUsers;
    }

    private boolean verify(AuthRequestBody body){
        if(adminRepository.existsByUserName(body.getUserName())){
            Admin admin = adminRepository.findByUserName(body.getUserName()).get();
            return admin.getPassword().equals(body.getPassword());
        }
        return false;
    }
}
