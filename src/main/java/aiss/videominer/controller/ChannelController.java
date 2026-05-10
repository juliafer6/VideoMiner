package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
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
    public List<Channel> getAllChannels(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(required=false) String name, // Filtrado de canales por nombre
            @RequestParam(required=false) String order) {
        Sort sort = Sort.unsorted();
        if (order != null)
            sort = order.startsWith("-")
                    ? Sort.by(order.substring(1)).descending()
                    : Sort.by(order).ascending();
        Pageable paging = PageRequest.of(page, size, sort);
        Page<Channel> pageChannels;
        if (name != null) {
            pageChannels = channelRepository.findByName(name, paging);
        } else {
            pageChannels = channelRepository.findAll(paging);
        }
        // return channelRepository.findAll();
        return pageChannels.getContent();
    }

    //GET channel by id
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable String id){
        Optional<Channel> channel = channelRepository.findById(id);

        if(channel.isPresent()){
            return new ResponseEntity<>(channel.get(), HttpStatus.OK);
        } else {
            throw new ChannelNotFoundException(id);
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
            throw new ChannelNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable String id){
        if (channelRepository.existsById(id)){
            channelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            throw new ChannelNotFoundException(id);
        }
    }
}
