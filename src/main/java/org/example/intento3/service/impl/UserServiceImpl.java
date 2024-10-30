package org.example.intento3.service.impl;


import org.example.intento3.exception.ValidationException;
import org.example.intento3.model.User;
import org.example.intento3.repository.TempDataSensorRepository;
import org.example.intento3.repository.UserRepository;
import org.example.intento3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TempDataSensorRepository tempDataSensorRepository;

    @Override
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void existsUserByEmail(User user) {
        if(userRepository.existsByUserEmail(user.getUserEmail())){
            throw new ValidationException("Ya existe un usuario con el email " + user.getUserEmail());
        }
    }

    @Override
    public User getUserById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
