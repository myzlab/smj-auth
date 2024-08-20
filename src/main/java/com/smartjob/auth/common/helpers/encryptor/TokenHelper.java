package com.smartjob.auth.common.helpers.encryptor;

import com.myzlab.k.helper.KExceptionHelper;
import com.smartjob.auth.common.apps.RedisApp;
import com.smartjob.auth.constants.MessagesConstant;
import com.smartjob.auth.constants.ParamConstant;
import com.smartjob.auth.dak.AppUserDAK;
import com.smartjob.auth.dak.ParamDAK;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenHelper {

    private final ParamDAK paramDAK;
    private final RedisApp redisApp;
    
    public LocalDateTime generateDueDate(
        final Integer tokenExpirationTime
    ) {
        return LocalDateTime
            .now()
            .plusMinutes(tokenExpirationTime);
    }
    
    public String createAccessToken(
        final UUID uuid,
        final LocalDateTime dueDate
    ) {
        return create(uuid, dueDate, ParamConstant.ACCESS_TOKEN_ENCRYPTION_KEY);
    }
    
    public Claims decodeAccessToken(
        final String accessToken
    ) {
        return decode(accessToken, ParamConstant.ACCESS_TOKEN_ENCRYPTION_KEY);
    }
    
    public Map<String, Object> decodeAccessTokenAndGetData(
        final String accessToken
    ) {
        final Claims claims = decode(accessToken, ParamConstant.ACCESS_TOKEN_ENCRYPTION_KEY);
        
        final UUID uuid = UUID.fromString(claims.getId());
        
        final Map<String, Object> data = redisApp.getAccessTokenData(uuid);
        
        if (data == null || data.isEmpty()) {
            throw KExceptionHelper.unauthorized(MessagesConstant.EXPIRED_TOKEN);
        }
        
        data.put("uuid", uuid);
        
        return data;
    }
    
    private Claims decode(
        final String token,
        final Long paramConstantEncryptionKey
    ) {
        try {
            final Claims claims = Jwts.parser()
                .setSigningKey(paramDAK.findStringValue(paramConstantEncryptionKey))
                .parseClaimsJws(token)
                .getBody();

            return claims;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw KExceptionHelper.unauthorized(MessagesConstant.EXPIRED_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
            throw KExceptionHelper.unauthorized(MessagesConstant.INVALID_TOKEN);
        }
    }
    
    private String create(
        final UUID uuid,
        final LocalDateTime dueDate,
        final Long paramConstantEncryptionKey
    ) {

        final Date date = new Date(System.currentTimeMillis());
        final Date expirationDate = Date.from(dueDate.atZone(ZoneId.systemDefault()).toInstant());

        final JwtBuilder jwtBuilder = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, paramDAK.findStringValue(paramConstantEncryptionKey))
            .setId(uuid.toString())
            .setIssuedAt(date)
            .setExpiration(expirationDate);

        return jwtBuilder.compact();
    }
}
