package aiss.videominer.controller;

import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    private CaptionRepository captionRepository;

    @GetMapping
    public List<Caption> getAllCaptions(){
        return captionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caption> getCaptionById(@PathVariable String id){
        Optional<Caption> caption = captionRepository.findById(id);
        if(caption.isPresent()){
            return new ResponseEntity<>(caption.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
