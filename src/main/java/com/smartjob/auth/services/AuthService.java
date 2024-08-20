package com.smartjob.auth.services;

import com.myzlab.k.helper.KExceptionHelper;
import com.smartjob.auth.common.helpers.validator.ValidatorHelper;
import com.smartjob.auth.constants.MessagesConstant;
import com.smartjob.auth.dak.AppUserDAK;
import com.smartjob.auth.k.generated.mappers.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AppUserDAK appUserDAK;
    private final LoginService loginService;
    
    @Transactional
    public ResponseEntity login(
        final String email,
        final String password
    ) {
        ValidatorHelper.assertNotNullNotEmpty(email, MessagesConstant.EMAIL_IS_REQUIRED);
        ValidatorHelper.assertNotNullNotEmpty(password, MessagesConstant.PASSWORD_IS_REQUIRED);

        final AppUser appUser = appUserDAK.findByEmailToLogin(
            email.toLowerCase(),
            password,
            KExceptionHelper.unauthorized(MessagesConstant.INVALID_CREDENTIALS)
        );
        
        if (!appUser.getBoolean("pswmatch")) {
            throw KExceptionHelper.unauthorized(MessagesConstant.INVALID_CREDENTIALS);
        }
        
        return loginService.doLogin(appUser, email.toLowerCase());
    }
}