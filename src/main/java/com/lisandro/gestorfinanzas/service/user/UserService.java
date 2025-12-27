package com.lisandro.gestorfinanzas.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.exception.UsernameAlreadyExistsException;
import com.lisandro.gestorfinanzas.model.UserSec;
import com.lisandro.gestorfinanzas.repository.IUserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<UserSec> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserSec save(UserSec userSec) {
        // Verificar si el usuario ya existe (solo al crear, no al actualizar)
        Optional<UserSec> existingUser = userRepository.findUserEntityByUsername(userSec.getUsername());
        
        // Si existe un usuario con ese username Y tiene un ID diferente (o el actual no tiene ID)
        if (existingUser.isPresent() && (userSec.getId() == null || !existingUser.get().getId().equals(userSec.getId()))) {
            throw new UsernameAlreadyExistsException(userSec.getUsername());
        }
        
        return userRepository.save(userSec);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(UserSec userSec) {
        save(userSec);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public UserSec findByUsername(String username) {
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
    }

    @Override
    public void markTutorialComplete(String username) {
        UserSec user = findByUsername(username);
        user.setTutorialComplete(true);
        save(user);
    }

    @Override
    public Boolean isTutorialComplete(String username) {
        UserSec user = findByUsername(username);
        return user.isTutorialComplete();
    }

}