package com.example.demo.service.shop;

import com.example.demo.dto.shop.ItemReviewDto;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemReviewBoard;
import com.example.demo.repository.shop.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemReviewService {
    private final ItemReviewRepository itemReviewRepository;
    public List<ItemReviewDto> getReviewBoardList(Item item_id) {
        List<ItemReviewBoard> boards = itemReviewRepository.findByItemReviewId(item_id)
                .orElseThrow(() -> new IllegalStateException("리뷰 글 없음"));
        List<ItemReviewDto> itemReviewDtoList = new ArrayList<>();

        for (ItemReviewBoard board : boards){
            ItemReviewDto reviewDto = ItemReviewDto.builder()
                    .reviewBoardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .build();

            itemReviewDtoList.add(reviewDto);
        }

        return itemReviewDtoList;
    }

    public void writeReview(Item item ,ItemReviewDto itemReviewDto) {
        ItemReviewBoard itemReviewBoard = ItemReviewBoard.builder()
                .item(item)
                .title(itemReviewDto.getTitle())
                .content(itemReviewDto.getContent())
                .build();

        itemReviewRepository.save(itemReviewBoard);
    }

    public void updateReview(ItemReviewDto itemReviewDto){
        ItemReviewBoard itemReviewBoard = itemReviewRepository.findById(itemReviewDto.getReviewBoardId())
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없어요"));
        itemReviewBoard.setTitle(itemReviewDto.getTitle());
        itemReviewBoard.setContent(itemReviewDto.getContent());
        itemReviewRepository.save(itemReviewBoard);
    }

    public void deleteReview(ItemReviewDto itemReviewDto){
        itemReviewRepository.deleteById(itemReviewDto.getReviewBoardId());
    }
}
