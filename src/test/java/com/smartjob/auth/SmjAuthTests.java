package com.smartjob.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartjob.auth.controllers.AuthController;
import com.smartjob.auth.services.AuthService;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SmjAuthTests {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
	@Test
	void testLoginSuccessful() throws Exception {
        final String response =
            mockMvc.perform(
                post("/auth/login")
                    .header("email", "mquintero@smartjob.cl")
                    .header("password", "HolaMaria123!")
            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        
        final Map<String, Object> body = objectMapper.readValue(response, Map.class);
        
        final Map user = (Map) body.get("user");
        
        assertEquals("mquintero@smartjob.cl", user.get("email"));
	}

    @Test
	void testLoginUnauthorized() throws Exception {
        mockMvc.perform(
            post("/auth/login")
                .header("email", "invalid")
                .header("password", "invalid")
        )
        .andExpect(status().isUnauthorized());
	}
}
