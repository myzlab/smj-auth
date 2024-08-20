package com.smartjob.auth.dak;

import com.myzlab.k.KBuilder;
import com.myzlab.k.helper.KExceptionHelper;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import com.smartjob.auth.k.generated.repository.ParamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParamDAK {
    
    private final KBuilder k;
    private final ParamRepository paramRepository;
    
    public String findStringValue(
        final Long id
    ) {
        
        return
            paramRepository
                .findById(id, PARAM.VALUE)
                .assertNotNull(0, HttpStatus.INTERNAL_SERVER_ERROR, String.format("Param '%s' not found", id))
                .getString(0);
    }
    
    public Integer findIntegerValue(
        final Long id
    ) {
        final String value = findStringValue(id);
        
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            throw KExceptionHelper.internalServerError(String.format("Param '%s' not parseable to integer", id));
        }
    }
    
    public Boolean findBooleanValue(
        final Long id
    ) {
        final String value = findStringValue(id);
        
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            throw KExceptionHelper.internalServerError(String.format("Param '%s' not parseable to boolean", id));
        }
    }
}