package com.example.spotifyapi.model.dto;

public class SpotifyDto {
    private String trackName;

    public SpotifyDto(String trackName) {
        this.trackName = trackName;
    }


    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

}
