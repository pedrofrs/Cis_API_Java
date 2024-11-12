package jala.guaxi.ds3_capstone.domain.repository;

import jala.guaxi.ds3_capstone.domain.model.AuthenticationUserAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationRepository extends JpaRepository<AuthenticationUserAPI, String> {

    UserDetails findUserByLogin(String login);
}
