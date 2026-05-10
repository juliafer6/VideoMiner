package aiss.videominer.controller;

import aiss.videominer.exception.CommentNotFoundException;
import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/comments")
public class CommentController {
// Controller videominer comentarios
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id){
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isPresent()){
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        } else{
            throw new CommentNotFoundException(id);
        }
    }
}
