package com.example.demo.service.artist;

import com.example.demo.dto.artist.CommentDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.artist.ArtistBoard;
import com.example.demo.entity.artist.Comment;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.artist.ArtistBoardCommentRepository;
import com.example.demo.repository.artist.ArtistBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistBoardCommentService {

    private final ArtistBoardRepository artistBoardRepository;
    private final MemberRepository memberRepository;
    private final ArtistBoardCommentRepository artistBoardCommentRepository;

    /**
     * 댓글조회
     */
    public List<CommentDto> getComment(Long boardId) {
        ArtistBoard artistBoard = artistBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("없는 응원글"));
        List<Comment> comments = artistBoardCommentRepository.findAllByArtistBoard(artistBoard)
                .orElseThrow(()-> new IllegalStateException("댓글이 없어영"));
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment : comments){
            CommentDto commentDto = CommentDto
                    .builder()
                    .commentId(comment.getId())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .content(comment.getContent())
                    .writer(comment.getWriter())
                    .build();

            commentDtos.add(commentDto);
        }

        return commentDtos;
    }

    /**
     * 댓글 작성
     */
    public ResponseEntity writeComment(String email, Long boardId, String content) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("없는 유저"));
        ArtistBoard artistBoard = artistBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("없는 응원글"));
        Comment comment = new Comment(content, member.getName());
        comment.setBoard(artistBoard);
        artistBoardCommentRepository.save(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 댓글 수정
     */
    public ResponseEntity updateComment(CommentDto commentDto){
        Comment comment = artistBoardCommentRepository.findById(commentDto.getCommentId())
                .orElseThrow(() -> new IllegalStateException("없는 댓글"));
        comment.updateComment(commentDto.getContent());
        artistBoardCommentRepository.save(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     */
    public ResponseEntity deleteComment(Long commentId){
        artistBoardCommentRepository.deleteById(commentId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
