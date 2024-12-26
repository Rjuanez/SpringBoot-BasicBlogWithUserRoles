package com.tutorial.tutorialspringboot.DTO;

import com.tutorial.tutorialspringboot.Entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class PublicationDTO {


    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Publication title must be at least 2 chars ")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Publication description must be at least 10 chars ")
    private String description;

    @NotEmpty(message = "Publication content can't be empty")
    @Size(min = 15, message = "Publication content must be at least 15 char ")
    private String content;

    private Set<Comment> comments;

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PublicationDTO() {
    }
}
