package jala.guaxi.ds3_capstone.controller;


import jakarta.validation.Valid;
import jala.guaxi.ds3_capstone.domain.dto.AuthenticationDTO;
import jala.guaxi.ds3_capstone.domain.dto.LoginResponseDTO;
import jala.guaxi.ds3_capstone.domain.dto.RegisterDTO;
import jala.guaxi.ds3_capstone.domain.model.AuthenticationUserAPI;
import jala.guaxi.ds3_capstone.domain.repository.AuthenticationRepository;
import jala.guaxi.ds3_capstone.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationRepository userRepositoryAPI;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((AuthenticationUserAPI) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if (this.userRepositoryAPI.findUserByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        AuthenticationUserAPI newuser = new AuthenticationUserAPI(data.login(), encryptedPassword, data.role());

        this.userRepositoryAPI.save(newuser);

        return ResponseEntity.ok().build();
    }
}
