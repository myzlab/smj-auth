package com.smartjob.auth.dak;

import com.myzlab.k.KBuilder;
import com.myzlab.k.KCollection;
import com.myzlab.k.KDeleteFrom;
import com.myzlab.k.KFrom;
import com.myzlab.k.allowed.KColumnAllowedToSelect;
import com.smartjob.auth.k.generated.mappers.Phone;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import com.smartjob.auth.k.generated.repository.PhoneRepository;
import com.smartjob.auth.payloads.PhonePayload;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhoneDAK {

    private final KBuilder k;
    private final PhoneRepository phoneRepository;
    
    public void create(
        final Long appUserId,
        final List<PhonePayload> list
    ) {
        if (list == null || list.isEmpty()) {
            return;
        }
        
        final List<Phone> phones = new ArrayList<>();
        
        for (final PhonePayload phonePayload : list) {
            phones.add(
                new Phone()
                    .setNumber(phonePayload.getNumber())
                    .setCityCode(phonePayload.getCityCode())
                    .setCountryCode(phonePayload.getCountryCode())
                    .setAppUserId(appUserId)
            );
        }
        
        phoneRepository.insert(phones);
    }
    
    public void deleteByAppUserId(
        final Long appUserId
    ) {
        phoneRepository.deleteBy(
            (KDeleteFrom kDeleteFrom) -> 
                kDeleteFrom
                    .where(PHONE.APP_USER_ID.eq(appUserId))
        );
    }
    
    public KCollection<Phone> getByAppUserIds(
        final List<Long> appUserIds,
        final KColumnAllowedToSelect... kColumnAllowedToSelects
    ) {
        return
            phoneRepository.findMultipleBy(
                (KFrom kFrom) -> 
                    kFrom
                    .where(PHONE.APP_USER_ID.in(appUserIds)),
                kColumnAllowedToSelects
            );
    }
}