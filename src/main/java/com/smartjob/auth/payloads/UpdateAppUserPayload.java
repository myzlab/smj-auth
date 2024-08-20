package com.smartjob.auth.payloads;

import com.myzlab.k.validator.PayloadValidator;
import com.smartjob.auth.constants.MessagesConstant;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateAppUserPayload extends PayloadValidator {

    @NotBlank(message = MessagesConstant.NAME_IS_REQUIRED)
    @Size(max = 127, message = MessagesConstant.NAME_CAN_CONTAIN_MAXIMUM_127_CHARACTERS)
    private String name;
    
    @Valid
    private List<PhonePayload> phones;
}
