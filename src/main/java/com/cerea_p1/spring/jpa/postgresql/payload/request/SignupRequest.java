package com.cerea_p1.spring.jpa.postgresql.payload.request;

import javax.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 255)
    private String username;
 
    @NotBlank
    @Size(max = 255)
    @Email
    private String email;
    
    @NotNull
    @Size(max = 255)
    private String pais;
    
    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public String getPassword() {
        return password;
    }

    public String getPais() {
        return pais;
    }
}