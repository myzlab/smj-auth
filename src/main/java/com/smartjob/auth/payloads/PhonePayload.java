package com.smartjob.auth.payloads;

import com.myzlab.k.validator.PayloadValidator;
import com.smartjob.auth.constants.MessagesConstant;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PhonePayload extends PayloadValidator {

    @NotBlank(message = MessagesConstant.CITY_CODE_IS_REQUIRED)
    private String cityCode;
    
    @NotBlank(message = MessagesConstant.COUNTRY_CODE_IS_REQUIRED)
    private String countryCode;
    
    @NotBlank(message = MessagesConstant.NUMBER_IS_REQUIRED)
    private String number;
}
