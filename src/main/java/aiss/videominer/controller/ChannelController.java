package aiss.videominer.controller;

import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {
    @Autowired
    private ChannelRepository channelRepository;

    //POST
    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel){
        Channel savedChannel = channelRepository.save(channel);
        return new ResponseEntity<>(savedChannel, HttpStatus.CREATED);
    }

    //GET
    @GetMapping
    public List<Channel> getAllChannels(){
        return channelRepository.findAll();
    }

    //GET channel by id
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable String id){
        Optional<Channel> channel = channelRepository.findById(id);

        if(channel.isPresent()){
            return new ResponseEntity<>(channel.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateChannel(@PathVariable String id, @RequestBody Channel updatedChannel){
        Optional<Channel> existingChannel = channelRepository.findById(id);

        if(existingChannel.isPresent()){
            Channel oldChannel = existingChannel.get();
            oldChannel.setName(updatedChannel.getName());
            oldChannel.setDescription(updatedChannel.getDescription());
            oldChannel.setCreatedTime(updatedChannel.getCreatedTime());
            oldChannel.setVideos(updatedChannel.getVideos());

            channelRepository.save(oldChannel);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable String id){
        if (channelRepository.existsById(id)){
            channelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
