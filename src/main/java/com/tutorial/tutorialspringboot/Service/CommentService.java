package com.tutorial.tutorialspringboot.Service;

import com.tutorial.tutorialspringboot.DTO.CommentDTO;

import java.util.List;

public interface CommentService {

    public CommentDTO createComment(long publicationId, CommentDTO commentDTO);

    public List<CommentDTO> getCommentsByPublicationId(long publicationId);

    public CommentDTO getCommentById(long publicationId, long commentId);

    public CommentDTO updateComment(long publicationId,  long commentId, CommentDTO commentRequestDTO);

    public void deleteComment(long publicationId, long commentId);
}
