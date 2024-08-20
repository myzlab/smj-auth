package com.smartjob.auth.dak;

import com.myzlab.k.KBuilder;
import com.myzlab.k.KCollection;
import com.myzlab.k.KException;
import static com.myzlab.k.KFunction.*;
import com.myzlab.k.KQuery;
import com.myzlab.k.KValues;
import com.smartjob.auth.common.apps.RedisApp;
import com.smartjob.auth.dqo.AppUserDQO;
import com.smartjob.auth.k.generated.mappers.AppUser;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import com.smartjob.auth.k.generated.repository.AppUserRepository;
import com.smartjob.auth.payloads.CreateAppUserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppUserDAK {

    private final KBuilder k;
    private final AppUserRepository appUserRepository;
    
    public AppUser create(
        final CreateAppUserPayload addAppUserPayload
    ) {
        final KValues user = values()
            .append(
                addAppUserPayload.getName(),
                addAppUserPayload.getEmail().toLowerCase(),
                crypt(addAppUserPayload.getPassword())
            );

        return
            k
            .insertInto(APP_USER)
            .columns(
                APP_USER.NAME,
                APP_USER.EMAIL,
                APP_USER.PASSWORD
            )
            .values(user)
            .returning(
                APP_USER.ID.useNoAlias()
            )
            .execute(AppUser.class)
            .get(0);
    }
    
    public AppUser findByEmailToLogin(
        final String email,
        final String password,
        final KException kException
    ) {
        return
            k
            .select(
                APP_USER.ID,
                APP_USER.NAME,
                APP_USER.IS_ACTIVE,
                APP_USER.PASSWORD.eq(crypt(password, APP_USER.PASSWORD)).as("pswmatch")
            )
            .from(APP_USER)
            .where(APP_USER.EMAIL.eq(email))
            .single(AppUser.class)
            .orElseThrow(kException);
    }

    public KCollection<AppUser> dqo(
        final AppUserDQO.Data data,
        final Long page,
        final Long limit,
        final String orderBy,
        final Integer order,
        final String name,
        final String email,
        final boolean totalCount
    ) {
        return
            k
            .sf(optional(totalCount ? totalCount() : null))
            .select(data.getSelects())
            .from(APP_USER)
            .where(APP_USER.NAME.ilka(optional(name)))
            .and(APP_USER.EMAIL.ilka(optional(email)))
            .orderBy(
                getKColumnOrderedByName(
                    orderBy,
                    order,
                    APP_USER
                )
            )
            .limit(optional(limit))
            .offset(calculateOffset(optional(page), optional(limit)))
            .multiple(AppUser.class);
    }
    
    public void assertNotExistsByEmail(
        final String email,
        final HttpStatus httpStatus,
        final String message
    ) {
        final KQuery kQuery =
            k
            .select1()
            .from(APP_USER)
            .where(APP_USER.EMAIL.eq(email));
        
        assertNotExists(k, kQuery, httpStatus, message);
    }
    
    public void assertExistsById(
        final Long id,
        final HttpStatus httpStatus,
        final String message
    ) {
        appUserRepository.assertExistsById(id, httpStatus, message);
    }
    
    public void updateById(
        final Long id,
        final String name
    ) {
        appUserRepository.updateById(
            new AppUser()
                .setId(id)
                .setName(name)
        );
    }
    
    public void deleteById(
        final Long id
    ) {
        appUserRepository.deleteById(id);
    }
}