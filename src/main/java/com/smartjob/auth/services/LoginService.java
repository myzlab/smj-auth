package com.smartjob.auth.services;

import com.myzlab.k.DynamicObject;
import com.myzlab.k.helper.KExceptionHelper;
import com.smartjob.auth.common.apps.RedisApp;
import com.smartjob.auth.common.helpers.encryptor.TokenHelper;
import com.smartjob.auth.constants.MessagesConstant;
import com.smartjob.auth.constants.ParamConstant;
import com.smartjob.auth.dak.ParamDAK;
import com.smartjob.auth.k.generated.mappers.AppUser;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    
    private final TokenHelper tokenHelper;
    private final ParamDAK paramDAK;
    private final RedisApp redisApp;
    
    public ResponseEntity doLogin(
        final AppUser appUser,
        final String email
    ) {
        if (appUser.getIsActive().equals(Boolean.FALSE)) {
            throw KExceptionHelper.badRequest(MessagesConstant.INACTIVE_USER);
        }
        
        final Long appUserId = appUser.getId();
        final String name = appUser.getName();
        
        final Integer accessTokenExpirationTime = paramDAK.findIntegerValue(ParamConstant.ACCESS_TOKEN_EXPIRATION_TIME);
        final LocalDateTime dueDate = tokenHelper.generateDueDate(accessTokenExpirationTime);
        
        final UUID accessTokenUuid = UUID.randomUUID();
        
        final String accessToken = tokenHelper.createAccessToken(
            accessTokenUuid,
            dueDate
        );
        
        redisApp.putAccessToken(accessTokenUuid, accessTokenExpirationTime, appUserId);
        
        return DynamicObject.create()
            .add("user", 
                DynamicObject.create()
                    .add("email", email)
                    .add("name", name)
                .toMap()
            )
            .add("access", 
                DynamicObject.create()
                    .add("token", accessToken)
                .toMap()
            )
        .buildResponse();
    }
}