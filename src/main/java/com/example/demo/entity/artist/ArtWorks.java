package com.example.demo.entity.artist;

import com.example.demo.entity.artist.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "artworks")
@Entity
public class ArtWorks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artistWorkId;

    private String work;
    private String deleteWork;
}
