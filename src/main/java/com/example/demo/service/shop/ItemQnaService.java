package com.example.demo.service.shop;

import com.example.demo.dto.shop.ItemQnaDto;
import com.example.demo.dto.shop.ItemReviewDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemQnaBoard;
import com.example.demo.entity.shop.ItemReviewBoard;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.ItemQnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemQnaService {
    private final ItemQnaRepository itemQnaRepository;
    private final MemberRepository memberRepository;

    public List<ItemQnaDto> getQnaBoardList(Item item_id, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));
        List<ItemQnaBoard> boards = itemQnaRepository.findByItemQnaId(item_id)
                .orElseThrow(() -> new IllegalStateException("리뷰 글 없음"));
        List<ItemQnaDto> itemQnaDtoList = new ArrayList<>();

        for (ItemQnaBoard board : boards){
            ItemQnaDto reviewDto = new ItemQnaDto();

            if (member.getId() == board.getMember().getId()){
                reviewDto = ItemQnaDto.builder()
                        .qnaBoardId(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .secretTNF(board.isSecretTNF())
                        .myReview(true)
                        .username(board.getMember().getName())
                        .createdAt(board.getCreatedAt())
                        .updatedAt(board.getUpdatedAt())
                        .answer(board.getAnswer())
                        .build();
            } else {
                reviewDto = ItemQnaDto.builder()
                        .qnaBoardId(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .secretTNF(board.isSecretTNF())
                        .myReview(false)
                        .username(board.getMember().getName())
                        .createdAt(board.getCreatedAt())
                        .updatedAt(board.getUpdatedAt())
                        .answer(board.getAnswer())
                        .build();

            }

            itemQnaDtoList.add(reviewDto);
        }

        return itemQnaDtoList;
    }

    public void writeQna(Item item ,ItemQnaDto itemQnaDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));


        ItemQnaBoard itemQnaBoard = ItemQnaBoard.builder()
                .item(item)
                .title(itemQnaDto.getTitle())
                .content(itemQnaDto.getContent())
                .member(member)
                .secretTNF(itemQnaDto.isSecretTNF())
                .build();

        itemQnaBoard.addItem(item);

        itemQnaRepository.save(itemQnaBoard);
    }

    public void updateQna(ItemQnaDto itemQnaDto){
        ItemQnaBoard itemQnaBoard = itemQnaRepository.findById(itemQnaDto.getQnaBoardId())
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없어요"));
        itemQnaBoard.setTitle(itemQnaDto.getTitle());
        itemQnaBoard.setContent(itemQnaDto.getContent());
        itemQnaRepository.save(itemQnaBoard);
    }

    public void deleteQna(ItemQnaDto itemQnaDto){
        itemQnaRepository.deleteById(itemQnaDto.getQnaBoardId());
    }
}
