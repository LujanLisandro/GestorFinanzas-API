package com.lisandro.gestorfinanzas.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lisandro.gestorfinanzas.model.Role;
import com.lisandro.gestorfinanzas.model.UserSec;
import com.lisandro.gestorfinanzas.service.role.IRoleService;
import com.lisandro.gestorfinanzas.service.user.IUserService;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;
    
    @PostMapping
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec user) {
        Set<Role> validList = new HashSet<>();
        Role readRol;

        // ENCRIPTAR CONTRASEÑA
        user.setPassword(userService.encriptPassword(user.getPassword()));

        for (Role rol : user.getRolesList()) {
            readRol = roleService.findById(rol.getId()).orElse(null);
            if (readRol != null) {
                validList.add(readRol);
            }
        }
        user.setRolesList(validList);
        userService.save(user);
        return ResponseEntity.ok(user);

    }
    @PutMapping("/tutorial/complete")
    public ResponseEntity <Void> markTutorialComplete(Authentication auth){
        String username = auth.getName();
        userService.markTutorialComplete(username);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/tutorial")
    public ResponseEntity<Boolean> getTutorialComplete(Authentication auth){
        String username = auth.getName();
        Boolean isComplete = userService.isTutorialComplete(username);
        return ResponseEntity.ok(isComplete);
    }


}