package com.example.demo.repository.artist;

import com.example.demo.dto.artist.CommentDto;
import com.example.demo.entity.artist.ArtistBoard;
import com.example.demo.entity.artist.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistBoardCommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByArtistBoard(ArtistBoard artistBoard);
}
