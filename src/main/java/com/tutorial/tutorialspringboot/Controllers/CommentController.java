package com.tutorial.tutorialspringboot.Controllers;

import com.tutorial.tutorialspringboot.DTO.CommentDTO;

import com.tutorial.tutorialspringboot.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/publication/{publicationId}/comment")
    public List<CommentDTO> getCommentsByPublicationId(@PathVariable Long publicationId){
        return commentService.getCommentsByPublicationId(publicationId);
    }

    @GetMapping("/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long commentId, @PathVariable Long publicationId){
        return new ResponseEntity<>(commentService.getCommentById(publicationId, commentId), HttpStatus.OK);
    }

    @PostMapping("/publication/{publicationId}/comment")
    public ResponseEntity<CommentDTO> addComment(@PathVariable(value = "publicationId") Long publicationId, @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(publicationId, commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentById(@PathVariable Long commentId, @PathVariable Long publicationId, @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.updateComment(publicationId, commentId, commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/publication/{publicationId}/comment/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Long commentId, @PathVariable Long publicationId){
        commentService.deleteComment(publicationId, commentId);
        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }



}
