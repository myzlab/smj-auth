package com.smartjob.auth.controllers;

import com.smartjob.auth.payloads.CreateAppUserPayload;
import com.smartjob.auth.payloads.UpdateAppUserPayload;
import com.smartjob.auth.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class AppUserController {
    
    private final AppUserService appUserService;
    
    @GetMapping("/dqo")
    public ResponseEntity dqo(
        @RequestParam(required = false) final Long page,
        @RequestParam(required = false) final Long limit,
        @RequestParam(required = false) final String orderBy,
        @RequestParam(required = false) final Integer order,
        @RequestParam final String fields,
        @RequestParam(required = false) final String name,
        @RequestParam(required = false) final String email,
        @RequestParam(required = false, defaultValue = "false") final Boolean totalCount,
        @RequestParam(required = false, defaultValue = "false") final Boolean getPhones
    ) {
        return appUserService.dqo(
            page,
            limit,
            orderBy,
            order,
            fields,
            name,
            email,
            totalCount,
            getPhones
        );
    }
    
    @PostMapping
    public ResponseEntity<String> create(
        @RequestBody final CreateAppUserPayload createAppUserPayload
    ) {
        return appUserService.create(createAppUserPayload);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
        @PathVariable final Long id,
        @RequestBody final UpdateAppUserPayload updateAppUserPayload
    ) {
        return appUserService.update(id, updateAppUserPayload);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
        @PathVariable final Long id
    ) {
        return appUserService.delete(id);
    }
}