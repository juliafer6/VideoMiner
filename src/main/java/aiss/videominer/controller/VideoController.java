package aiss.videominer.controller;

import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
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
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping
    public List<Video> getAllVideos(){
        return videoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable String id){
        Optional<Video> video = videoRepository.findById(id);
        if(video.isPresent()){
            return new ResponseEntity<>(video.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
