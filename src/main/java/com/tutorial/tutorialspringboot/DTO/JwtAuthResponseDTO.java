package com.tutorial.tutorialspringboot.DTO;

public class JwtAuthResponseDTO {
    private String token;
    private String tokenType = "Bearer";

    public JwtAuthResponseDTO(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public JwtAuthResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
