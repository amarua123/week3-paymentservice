package com.accolite.paymentservice.serviceimpl;

import com.accolite.paymentservice.config.JwtTokenProvider;
import com.accolite.paymentservice.model.User;
import com.accolite.paymentservice.model.Vendor;
import com.accolite.paymentservice.repository.UserRepository;
import com.accolite.paymentservice.requestbody.AuthRequestBody;
import com.accolite.paymentservice.requestbody.RegistrationRequestBody;
import com.accolite.paymentservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public User registerUser(RegistrationRequestBody body) {
        User user = null;
        if(userRepository.existsByUserName(body.getUserName())){
            user = userRepository.findByUserName(body.getUserName()).get();
        }else{
            user = new User();
            user.setCorX(body.getCorX());
            user.setCorY(body.getCorY());
            user.setUserName(body.getUserName());
            user.setPassword(body.getPassword());
            user.setBalance(BigDecimal.ZERO);
            user.setOfflineBalance(BigDecimal.ZERO);
            user.setIsActive(0);
            user.setPaymentMode(0);
        }
        String secret = jwtTokenProvider.createToken(body.getUserName());
        user.setToken(secret);
        userRepository.save(user);
        return user;
    }

    @Override
    public String deposit(String userName, BigDecimal balance) {
        if(userRepository.existsByUserName(userName)){
            Optional<User> user = userRepository.findByUserName(userName);
            User curUser = user.get();
            if(curUser.getIsActive() == 0){
                return "Your account is not active right now !";
            }
            curUser.setBalance(curUser.getBalance().add(balance));
            userRepository.save(curUser);
            return "Your new balance is " + curUser.getBalance();
        }
        return "Unable to add the balance";
    }

    @Override
    public String refreshToken(AuthRequestBody body) {
        User user = null;
        if(userRepository.existsByUserName(body.getUserName())){
            user = userRepository.findByUserName(body.getUserName()).get();
            if(!user.getPassword().equals(body.getPassword())){
                return "Wrong password !";
            }
        }else{
            return "User not found !";
        }
        String secret = jwtTokenProvider.createToken(body.getUserName());
        user.setToken(secret);
        userRepository.save(user);
        return user.getToken();
    }

    @Override
    public String onlinePayment(String userName, Vendor vendor, BigDecimal amount) {
        return null;
    }
}
