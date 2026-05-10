package aiss.videominer.controller;

import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Channel;
import aiss.videominer.repository.CaptionRepository;
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
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    private CaptionRepository captionRepository;

    @GetMapping
    public List<Caption> getAllCaptions(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(required=false) String language, // Filtrado de subtitulo por idioma
            @RequestParam(required=false) String order){
        Sort sort = Sort.unsorted();
        if (order != null)
            sort = order.startsWith("-")
                    ? Sort.by(order.substring(1)).descending()
                    : Sort.by(order).ascending();
        Pageable paging = PageRequest.of(page, size, sort);
        Page<Caption> pageCaptions;
        if (language != null) {
            pageCaptions = captionRepository.findByLanguage(language, paging);
        } else {
            pageCaptions = captionRepository.findAll(paging);
        }
        // return captionsRepository.findAll();
        return pageCaptions.getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caption> getCaptionById(@PathVariable String id){
        Optional<Caption> caption = captionRepository.findById(id);
        if(caption.isPresent()){
            return new ResponseEntity<>(caption.get(), HttpStatus.OK);
        } else{
            throw new CaptionNotFoundException(id);
        }
    }
}
