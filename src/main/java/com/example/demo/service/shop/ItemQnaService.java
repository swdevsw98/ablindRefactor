package com.example.demo.service.shop;

import com.example.demo.dto.shop.ItemQnaDto;
import com.example.demo.dto.shop.ItemReviewDto;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemQnaBoard;
import com.example.demo.entity.shop.ItemReviewBoard;
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

    public List<ItemQnaDto> getQnaBoardList(Item item_id) {
        List<ItemQnaBoard> boards = itemQnaRepository.findByItemQnaId(item_id)
                .orElseThrow(() -> new IllegalStateException("리뷰 글 없음"));
        List<ItemQnaDto> itemQnaDtoList = new ArrayList<>();

        for (ItemQnaBoard board : boards){
            ItemQnaDto reviewDto = ItemQnaDto.builder()
                    .qnaBoardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();

            itemQnaDtoList.add(reviewDto);
        }

        return itemQnaDtoList;
    }

    public void writeQna(Item item ,ItemQnaDto itemQnaDto) {
        ItemQnaBoard itemQnaBoard = ItemQnaBoard.builder()
                .item(item)
                .title(itemQnaDto.getTitle())
                .content(itemQnaDto.getContent())
                .build();

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
