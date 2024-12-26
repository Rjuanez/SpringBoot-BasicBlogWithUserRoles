package com.tutorial.tutorialspringboot.Repositories;

import com.tutorial.tutorialspringboot.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRespository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPublicationId(Long publicationId);
}
