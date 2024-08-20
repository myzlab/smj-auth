package com.smartjob.auth.payloads;

import com.myzlab.k.validator.PayloadValidator;
import com.smartjob.auth.constants.MessagesConstant;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateAppUserPayload extends PayloadValidator {

    @NotBlank(message = MessagesConstant.NAME_IS_REQUIRED)
    @Size(max = 127, message = MessagesConstant.NAME_CAN_CONTAIN_MAXIMUM_127_CHARACTERS)
    private String name;
    
    @NotBlank(message = MessagesConstant.EMAIL_IS_REQUIRED)
    @Email(message = MessagesConstant.EMAIL_FORMAT_NOT_VALID)
    @Size(max = 255, message = MessagesConstant.EMAIL_CAN_CONTAIN_MAXIMUM_255_CHARACTERS)
    private String email;
    
    @NotBlank(message = MessagesConstant.PASSWORD_IS_REQUIRED)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\-\\[{}\\]:;',?/*~$^+=<>.]).{8,14}$", message = MessagesConstant.PASSWORD_FORMAT_NOT_VALID)
    private String password;
    
    @Valid
    private List<PhonePayload> phones;
}
