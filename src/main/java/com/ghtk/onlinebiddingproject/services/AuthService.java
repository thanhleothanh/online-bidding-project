package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.dtos.UserLogin;
import com.ghtk.onlinebiddingproject.models.dtos.UserSignup;
import com.ghtk.onlinebiddingproject.models.responses.CommonResponse;
import org.springframework.http.ResponseCookie;

public interface AuthService {

    CommonResponse login(UserLogin loginRequest);

    CommonResponse signUp(UserSignup signupRequest);

    ResponseCookie generateJwtCookie();

    ResponseCookie cleanJwtCookie();

}
