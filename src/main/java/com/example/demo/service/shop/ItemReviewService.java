package com.example.demo.service.shop;

import com.example.demo.dto.shop.ItemReviewDto;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemReviewBoard;
import com.example.demo.repository.shop.ItemRepository;
import com.example.demo.repository.shop.ItemReviewRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemReviewService {
    private final ItemReviewRepository itemReviewRepository;
    private final S3Uploader s3Uploader;
    private final ItemRepository itemRepository;

    public List<ItemReviewDto> getReviewBoardList(Item item_id) {
        List<ItemReviewBoard> boards = itemReviewRepository.findByItemReviewId(item_id)
                .orElseThrow(() -> new IllegalStateException("리뷰 글 없음"));
        List<ItemReviewDto> itemReviewDtoList = new ArrayList<>();

        for (ItemReviewBoard board : boards){
            ItemReviewDto reviewDto = ItemReviewDto.builder()
                    .reviewBoardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .rate(board.getRate())
                    .image(board.getImage())
                    .build();

            itemReviewDtoList.add(reviewDto);
        }

        return itemReviewDtoList;
    }

    public void writeReview(Item item , ItemReviewDto itemReviewDto, MultipartFile multipartFile) throws IOException {
        //[0] -> S3에 저장된 파일이름 [1] -> 이미지 경로
        String[] values = s3Uploader.upload(multipartFile, "review");

        ItemReviewBoard itemReviewBoard = ItemReviewBoard.builder()
                .item(item)
                .title(itemReviewDto.getTitle())
                .content(itemReviewDto.getContent())
                .rate(itemReviewDto.getRate())
//                .image(s3Uploader.upload(multipartFile, "review"))
                .deleteImage(values[0])
                .image(values[1])
                .build();

        item.getItemReviewBoards().add(itemReviewBoard);
        item.avgAddRate(itemReviewBoard.getRate());

        itemReviewRepository.save(itemReviewBoard);
    }

    public void updateReview(ItemReviewDto itemReviewDto, MultipartFile multipartFile) throws IOException{
        String[] values = s3Uploader.upload(multipartFile, "review");
        ItemReviewBoard itemReviewBoard = itemReviewRepository.findById(itemReviewDto.getReviewBoardId())
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없어요"));
        BigDecimal preRate = itemReviewBoard.getRate();

        itemReviewBoard.setTitle(itemReviewDto.getTitle());
        itemReviewBoard.setContent(itemReviewDto.getContent());
        itemReviewBoard.setDeleteImage(values[0]);
        itemReviewBoard.setImage(values[1]);
        itemReviewBoard.setRate(itemReviewDto.getRate());

        itemReviewBoard.getItemReviewId().avgUpdateRate(preRate, itemReviewBoard.getRate());

        itemRepository.save(itemReviewBoard.getItemReviewId());
        itemReviewRepository.save(itemReviewBoard);
    }

    public void deleteReview(ItemReviewDto itemReviewDto){
        ItemReviewBoard itemReviewBoard = itemReviewRepository.findById(itemReviewDto.getReviewBoardId())
                .orElseThrow(() -> new IllegalStateException("그런 리뷰 없음"));
        itemReviewBoard.getItemReviewId().avgDeleteRate(itemReviewBoard.getRate());
        s3Uploader.deleteFile(itemReviewBoard.getDeleteImage());
        itemReviewRepository.deleteById(itemReviewDto.getReviewBoardId());
    }
}
