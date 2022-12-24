package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.requests.UserChangePassword;
import com.ghtk.onlinebiddingproject.models.requests.UserLogin;
import com.ghtk.onlinebiddingproject.models.requests.UserSignup;
import com.ghtk.onlinebiddingproject.models.responses.UserAuthResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface AuthService {

    UserAuthResponse login(UserLogin loginRequest, HttpServletRequest request);

    UserAuthResponse signUp(UserSignup signupRequest, final HttpServletRequest request);

    void changeMyPassword(UserChangePassword userChangePassword);

    ResponseCookie generateJwtCookie();

    ResponseCookie cleanJwtCookie();

    String validateVerificationToken(String token);
}
