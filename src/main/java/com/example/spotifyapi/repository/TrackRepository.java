package com.example.spotifyapi.repository;


import com.example.spotifyapi.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TrackRepository extends JpaRepository<Track,Long> {
}
