package com.xdstudios.usermanagementservice.service;

import com.xdstudios.usermanagementservice.domain.User;
import com.xdstudios.usermanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public boolean checkIfExists(Long id){
        return userRepository.findById(id).isPresent();
    }
}
