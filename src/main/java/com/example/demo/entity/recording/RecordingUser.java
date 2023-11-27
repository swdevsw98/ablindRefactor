package com.example.demo.entity.recording;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class RecordingUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path1;
    private String path2;
    private String path3;


    public RecordingUser(String name, List<Path> paths) {
        this.name = name;
        this.path1 = paths.get(0).toString();
        this.path2 = paths.get(1).toString();
        this.path3 = paths.get(2).toString();
    }

}
