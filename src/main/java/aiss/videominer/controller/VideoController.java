package aiss.videominer.controller;

import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
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
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping
    public List<Video> getAllVideos(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String user,
            @RequestParam(required=false) String order
    ){
        Sort sort = Sort.unsorted();
        if (order != null) {
            sort = order.startsWith("-")
                    ? Sort.by(order.substring(1)).descending()
                    : Sort.by(order).ascending();
        }
        Pageable paging = PageRequest.of(page, size, sort);
        Page<Video> pageVideos;
        if (name != null && user != null) {
            pageVideos = videoRepository.findByNameAndAuthorName(name, user, paging);
        } else if (name != null) {
            pageVideos = videoRepository.findByName(name, paging);
        } else if (user != null) {
            pageVideos = videoRepository.findByAuthorName(user, paging);
        } else {
            pageVideos = videoRepository.findAll(paging);
        }
        // return videoRepository.findAll();
        return pageVideos.getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable String id){
        Optional<Video> video = videoRepository.findById(id);
        if(video.isPresent()){
            return new ResponseEntity<>(video.get(), HttpStatus.OK);
        }else{
            throw new VideoNotFoundException(id);
        }
    }
}
