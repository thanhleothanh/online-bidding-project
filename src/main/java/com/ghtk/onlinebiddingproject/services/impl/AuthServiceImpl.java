package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.exceptions.BadRequestException;
import com.ghtk.onlinebiddingproject.models.dtos.UserLogin;
import com.ghtk.onlinebiddingproject.models.dtos.UserSignup;
import com.ghtk.onlinebiddingproject.models.entities.Profile;
import com.ghtk.onlinebiddingproject.models.entities.Role;
import com.ghtk.onlinebiddingproject.models.responses.CommonResponse;
import com.ghtk.onlinebiddingproject.models.responses.UserAuthResponse;
import com.ghtk.onlinebiddingproject.repositories.ProfileRepository;
import com.ghtk.onlinebiddingproject.repositories.RoleRepository;
import com.ghtk.onlinebiddingproject.security.UserDetailsImpl;
import com.ghtk.onlinebiddingproject.services.AuthService;
import com.ghtk.onlinebiddingproject.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProfileRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public CommonResponse login(UserLogin loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UserAuthResponse userLoginResponse = new UserAuthResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getName(), List.of(userDetails.getAuthorities()));
        return new CommonResponse(true, "Đăng nhập thành công!", userLoginResponse, null);
    }

    @Override
    public CommonResponse signUp(UserSignup signupRequest) {
        if (userInfoRepository.existsByUsername(signupRequest.getUsername())) {
            throw new BadRequestException("Username đã được sử dụng!");
        }
        Role userRole = roleRepository.findById(signupRequest.getRole().getId()).get();
        Profile newUser = userInfoRepository.save(new Profile(signupRequest.getUsername(),
                encoder.encode(signupRequest.getPassword()), signupRequest.getName(), userRole));

        if (userRole.getId() == 1) userInfoRepository.insertAdmin(newUser.getId());
        else userInfoRepository.insertUser(newUser.getId());

        UserAuthResponse userRegisterResponse = new UserAuthResponse(newUser.getId(), newUser.getUsername(), newUser.getName(), List.of(newUser.getRole()));
        return new CommonResponse(true, "Đăng ký thành công!", userRegisterResponse, null);
    }


    @Override
    public ResponseCookie generateJwtCookie() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtUtils.generateJwtCookie(userDetails);
    }

    @Override
    public ResponseCookie cleanJwtCookie() {
        return jwtUtils.getCleanJwtCookie();
    }
}
