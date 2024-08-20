package com.smartjob.auth.services;

import com.myzlab.k.KCollection;
import com.myzlab.k.helper.KExceptionHelper;
import com.smartjob.auth.common.helpers.validator.ValidatorHelper;
import com.smartjob.auth.constants.MessagesConstant;
import com.smartjob.auth.dak.AppUserDAK;
import com.smartjob.auth.dak.PhoneDAK;
import com.smartjob.auth.dqo.AppUserDQO;
import com.smartjob.auth.k.generated.mappers.AppUser;
import com.smartjob.auth.k.generated.mappers.Phone;
import static com.smartjob.auth.k.generated.metadata.Tables.APP_USER;
import static com.smartjob.auth.k.generated.metadata.Tables.PHONE;
import com.smartjob.auth.payloads.CreateAppUserPayload;
import com.smartjob.auth.payloads.UpdateAppUserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    
    private final AppUserDAK appUserDAK;
    private final PhoneDAK phoneDAK;
    
    public ResponseEntity dqo(
        final Long page,
        final Long limit,
        final String orderBy,
        final Integer order,
        final String fields,
        final String name,
        final String email,
        final Boolean totalCount,
        final Boolean getPhones
    ) {
        ValidatorHelper.assertValidPage(page);
        ValidatorHelper.assertValidLimit(limit);
        
        final AppUserDQO.Data data = AppUserDQO.toSelects(fields, orderBy);
        
        if (data == null) {
            throw KExceptionHelper.badRequest(MessagesConstant.AT_LEAST_A_VALID_FIELD_IS_REQUIRED);
        }
        
        final KCollection<AppUser> users = appUserDAK.dqo(data, page, limit, orderBy, order, name, email, totalCount);
        
        if (getPhones) {
            final KCollection<Phone> phones = phoneDAK.getByAppUserIds(
                users.takeLong(APP_USER.ID),
                PHONE.NUMBER,
                PHONE.CITY_CODE.as("cityCode"),
                PHONE.COUNTRY_CODE.as("countryCode"),
                PHONE.APP_USER_ID.as("app_user_id")
            );

            users.addProperty("phones", "id", "app_user_id", phones, false);
        }
        
        return users.buildResponse("users");
    }
    
    @Transactional
    public ResponseEntity create(
        final CreateAppUserPayload createAppUserPayload
    ) {
        createAppUserPayload.validate();
        
        appUserDAK.assertNotExistsByEmail(
            createAppUserPayload.getEmail().toLowerCase(),
            HttpStatus.BAD_REQUEST,
            MessagesConstant.EMAIL_ALREADY_REGISTERED
        );
        
        final AppUser appUser = appUserDAK.create(createAppUserPayload);
        
        phoneDAK.create(appUser.getId(), createAppUserPayload.getPhones());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @Transactional
    public ResponseEntity update(
        final Long id,
        final UpdateAppUserPayload addAppUserPayload
    ) {
        addAppUserPayload.validate();
        
        appUserDAK.assertExistsById(
            id,
            HttpStatus.NOT_FOUND,
            MessagesConstant.APP_USER_ID_NOT_FOUND
        );
        
        appUserDAK.updateById(id, addAppUserPayload.getName());
        
        phoneDAK.deleteByAppUserId(id);
        phoneDAK.create(id, addAppUserPayload.getPhones());

        return ResponseEntity.ok().build();
    }
    
    @Transactional
    public ResponseEntity delete(
        final Long id
    ) {
        appUserDAK.assertExistsById(
            id,
            HttpStatus.NOT_FOUND,
            MessagesConstant.APP_USER_ID_NOT_FOUND
        );
        
        phoneDAK.deleteByAppUserId(id);
        appUserDAK.deleteById(id);
        
        return ResponseEntity.ok().build();
    }
}