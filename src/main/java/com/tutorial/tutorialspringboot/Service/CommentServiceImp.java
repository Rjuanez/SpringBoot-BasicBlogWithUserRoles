package com.tutorial.tutorialspringboot.Service;

import com.tutorial.tutorialspringboot.DTO.CommentDTO;
import com.tutorial.tutorialspringboot.DTO.PublicationDTO;
import com.tutorial.tutorialspringboot.Entities.Comment;
import com.tutorial.tutorialspringboot.Entities.Publication;
import com.tutorial.tutorialspringboot.Exeption.BlogAppException;
import com.tutorial.tutorialspringboot.Exeption.ResourceNotFoundException;
import com.tutorial.tutorialspringboot.Repositories.CommentRespository;
import com.tutorial.tutorialspringboot.Repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService{

    @Autowired
    private CommentRespository commentRespository;

    @Autowired
    private PublicationRepository publicationRepository;


    @Override
    public CommentDTO createComment(long publicationId, CommentDTO commentDTO) {
        Comment comment = mapToComment(commentDTO);
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
        comment.setPublication(publication);

        Comment savedComment = commentRespository.save(comment);

        return mapToCommentDTO(savedComment);

    }

    @Override
    public List<CommentDTO> getCommentsByPublicationId(long publicationId) {
        List<Comment> comments = commentRespository.findByPublicationId(publicationId);

        return comments.stream().map(this::mapToCommentDTO).toList();
    }

    @Override
    public CommentDTO getCommentById(long publicationId, long commentId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
        Comment comment = commentRespository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPublication().getId().equals(publication.getId()))
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Publication does not belong to this comment");

        return mapToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long publicationId, long commentId, CommentDTO commentRequestDTO) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
        Comment comment = commentRespository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPublication().getId().equals(publication.getId()))
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Publication does not belong to this comment");
        comment.setName(commentRequestDTO.getName());
        comment.setEmail(commentRequestDTO.getEmail());
        comment.setContent(commentRequestDTO.getContent());

        Comment updatedComment = commentRespository.save(comment);

        return mapToCommentDTO(updatedComment);
    }

    @Override
    public void deleteComment(long publicationId, long commentId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));
        Comment comment = commentRespository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPublication().getId().equals(publication.getId()))
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Publication does not belong to this comment");

        commentRespository.delete(comment);


    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setContent(comment.getContent());

        return commentDTO;

    }

    private Comment mapToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setContent(commentDTO.getContent());

        return comment;
    }
}
