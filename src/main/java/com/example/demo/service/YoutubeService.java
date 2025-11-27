package com.example.demo.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class YoutubeService {

    private final YouTube youtube;

    @Value("${youtube.api.key}")
    private String apiKey;

    public YoutubeService(YouTube youtube) {
        this.youtube = youtube;
    }

    public Channel searchInfoChannel(String channelId) throws GeneralSecurityException, IOException {
       var request = youtube.channels().list(List.of("snippet,statistics,localizations"));

       request.setKey(apiKey);
       request.setId(List.of(channelId));

       var response = request.execute();

       if(response.getItems().isEmpty()) return null;
       return response.getItems().getFirst();

    }

    public List<SearchResult> searchLastVideos(String channelId) throws GeneralSecurityException, IOException {
        var searchRequest = youtube.search().list(List.of("snippet"));

        searchRequest.setKey(apiKey);
        searchRequest.setChannelId(channelId);
        searchRequest.setOrder("date");
        searchRequest.setMaxResults(5L);
        searchRequest.setType(List.of("video"));

        var searchResponse = searchRequest.execute();
        if(searchResponse.getItems().isEmpty()) return null;
        return searchResponse.getItems();
    }
}
