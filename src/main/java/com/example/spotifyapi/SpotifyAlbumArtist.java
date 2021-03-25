package com.example.spotifyapi;

import com.example.spotifyapi.entity.Track;
import com.example.spotifyapi.model.Spotify;
import com.example.spotifyapi.model.dto.SpotifyDto;
import com.example.spotifyapi.repository.TrackRepository;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.awt.*;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/album")
public class SpotifyAlbumArtist {

    private TrackRepository trackRepository;

    public SpotifyAlbumArtist(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @GetMapping(path = "/{artistName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpotifyDto>> getAlbumsByArtist(OAuth2Authentication details, @PathVariable String artistName){
        String jwt = ((OAuth2AuthenticationDetails)details.getDetails()).getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);


        ResponseEntity<Spotify> exchange = restTemplate.exchange("https://api.spotify.com/v1/search?q="+artistName+"&type=track&market=US&limit=20&offset=5",
                HttpMethod.GET,httpEntity,Spotify.class);

        List<SpotifyDto>  spotifyDtos = exchange.getBody().getTracks().getItems().stream()
                .map(item -> new SpotifyDto(item.getName()))
                .collect(Collectors.toList());

        for (SpotifyDto item: spotifyDtos){
            System.out.println(item.getTrackName());
        }
        return ResponseEntity.ok(spotifyDtos);
    }

    @PostMapping(path = "/add-track", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveTrack(@RequestBody Track track){
        trackRepository.save(track);
    }
}
