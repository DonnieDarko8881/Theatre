package com.crud.theatre.service;

import com.crud.theatre.domain.LoginInformation;
import com.crud.theatre.domain.Success;
import com.crud.theatre.domain.User;
import com.crud.theatre.exception.UserNotFoundException;
import com.crud.theatre.repository.LoginInformationRepository;
import com.crud.theatre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoginInformationRepository loginRepository;

    @Autowired
    public UserService(UserRepository userRepository, LoginInformationRepository loginRepository) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByMail(String mail) throws UserNotFoundException {
        return userRepository.findAll().stream()
                .filter(user -> user.getMail().equals(mail))
                .findFirst().orElseThrow(UserNotFoundException::new);
    }

    public Success login(String mail, String password) {
        List<User> userAfterLogin = userRepository.findAll().stream()
                .filter(user -> user.getMail().equals(mail))
                .filter(user -> user.getPassword().equals(password))
                .collect(Collectors.toList());
        if (userAfterLogin.size() == 0) {
            LoginInformation loginInformation = new LoginInformation(false,
                    "mail: " + mail + " or password: " + password + " is wrong");
            loginRepository.save(loginInformation);
            return new Success(false);
        } else {
            LoginInformation loginInformation = new LoginInformation(true,
                    "mail: " + mail + " and password: " + password + " is correct");
            loginRepository.save(loginInformation);
            return new Success(true);
        }
    }

    public Success mailExist(String mail) {
        List<User> userByMail = userRepository.findAll().stream()
                .filter(user -> user.getMail().equals(mail))
                .collect(Collectors.toList());
        if (userByMail.size() == 0) {
            return new Success(false);
        } else {
            return new Success(true);
        }
    }
}
