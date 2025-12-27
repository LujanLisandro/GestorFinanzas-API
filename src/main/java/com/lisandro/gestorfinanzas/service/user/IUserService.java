package com.lisandro.gestorfinanzas.service.user;

import java.util.List;
import java.util.Optional;

import com.lisandro.gestorfinanzas.model.UserSec;

public interface IUserService {

    List<UserSec> findAll();

    Optional<UserSec> findById(Long id);

    UserSec save(UserSec userSec);

    void deleteById(Long id);

    void update(UserSec userSec);

    UserSec findByUsername(String username);

    String encriptPassword(String password);

    void markTutorialComplete (String username);

    Boolean isTutorialComplete(String username);

}