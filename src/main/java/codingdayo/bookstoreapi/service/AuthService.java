package codingdayo.bookstoreapi.service;

import codingdayo.bookstoreapi.Utils.JWTUtils;
import codingdayo.bookstoreapi.dto.CustomResponse;
import codingdayo.bookstoreapi.entity.OurUsers;
import codingdayo.bookstoreapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public CustomResponse signUp(CustomResponse signUprequest){
        CustomResponse response = new CustomResponse();

        try {
            OurUsers ourUsers = OurUsers.builder()
                    .email(signUprequest.getEmail())
                    .password(passwordEncoder.encode(signUprequest.getPassword()))
                    .role(signUprequest.getRole())
                    .build();
            OurUsers requestResult = userRepository.save(ourUsers);

            if(requestResult != null && requestResult.getId()>0){
                response.setOurUsers(requestResult);
                response.setMessage("USER ACCOUNT CREATED SUCCESSFULLY");
                response.setStatusCode(200);
            }
        } catch (Exception e){
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    public CustomResponse signIn(CustomResponse signInRequest){
        CustomResponse customResponse = new CustomResponse();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getEmail(),
                    signInRequest.getPassword()
            ));

            var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow();
            System.out.println("User is: " + user);

            var  jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            customResponse.setToken(jwt);
            customResponse.setRefreshToken(refreshToken);
            customResponse.setStatusCode(200);
            customResponse.setExpirationTime("24hrs");
            customResponse.setMessage("Successfully signed in");

        } catch (Exception e){
            customResponse.setStatusCode(500);
            customResponse.setError(e.getMessage());
        }
        return customResponse;
    }


    public CustomResponse refreshToken(CustomResponse refreshTokenRequest){
        CustomResponse customResponse = new CustomResponse();

        String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        OurUsers users = userRepository.findByEmail(ourEmail).orElseThrow();

        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)){
            var jwt = jwtUtils.generateToken(users);

           customResponse.setToken(jwt);
            customResponse.setRefreshToken(refreshTokenRequest.getToken());
            customResponse.setStatusCode(200);
            customResponse.setExpirationTime("24hr");
            customResponse.setMessage("Successfully Refreshed Token");
        }
        customResponse.setStatusCode(500);
        return customResponse;
    }


}
