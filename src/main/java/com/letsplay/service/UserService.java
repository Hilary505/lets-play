package com.letsplay.service;

import com.letsplay.model.User;
import com.letsplay.repository.UserRepository;
import com.letsplay.util.InputSanitizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User createUser(String name, String email, String password, String role) {
        User user = new User();
        user.setName(InputSanitizer.sanitize(name));
        user.setEmail(InputSanitizer.sanitize(email));
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role != null ? role : "USER");
        return userRepository.save(user);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(InputSanitizer.sanitize(email)).orElse(null);
    }
}
