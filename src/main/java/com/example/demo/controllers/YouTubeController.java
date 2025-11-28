package com.example.demo.controllers;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.SearchResult;git rebase -i --root
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.YoutubeService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class YouTubeController {

    private final YoutubeService youtubeService;

    @Value("${youtube.channel.id}")
    private String channelId;

    public YouTubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/channel")
    public Channel getChannelInfo() {
        try {
            return youtubeService.searchInfoChannel(channelId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/videos")
    public List<SearchResult> getVideos(){
        try {
            return youtubeService.searchLastVideos(channelId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
