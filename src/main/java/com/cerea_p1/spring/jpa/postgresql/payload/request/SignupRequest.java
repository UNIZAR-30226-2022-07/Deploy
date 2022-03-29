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
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPais() {
        return pais;
    }
 
    public void setPais(String pais) {
        this.pais = pais;
    }
}