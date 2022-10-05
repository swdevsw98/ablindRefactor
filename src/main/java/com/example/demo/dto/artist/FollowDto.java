package com.example.demo.dto.artist;

import com.example.demo.entity.artist.Follow;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowDto {
    //follow id
    private Long id;
    private Long price;
    private String name;
    private String createdAt;
    private String artist;
    private boolean approve;
    private String updatedAt;

    public FollowDto(Follow follow){
        this.id = follow.getId();
        this.price = follow.getPrice();
        this.name = follow.getFollowUserId().getName();
        this.artist = follow.getFollowArtistId().getName();
        this.createdAt = follow.getCreatedAt();
        this.approve = follow.isApprove();
        this.updatedAt = follow.getUpdatedAt();
    }
}
