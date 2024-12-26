package com.tutorial.tutorialspringboot.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDTO {

    private long id;

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotEmpty(message = "Email can't be empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String content;

    public CommentDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
