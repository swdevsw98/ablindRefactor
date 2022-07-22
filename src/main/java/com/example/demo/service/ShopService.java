package com.example.demo.service;

import com.example.demo.dto.shop.ItemDto;
import com.example.demo.entity.shop.Item;
import com.example.demo.repository.shop.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {
    private final ItemRepository itemRepository;

    //메인페이지
    public List<ItemDto> list() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(Item item : items){
            ItemDto itemDto = ItemDto.builder()
                    .itemId(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .build();

            itemDtoList.add(itemDto);
        }

        return itemDtoList;
    }

    //제목 검색 필터
    public List<ItemDto> titleFilter(String keyword) {
        List<Item> items = itemRepository.findByNameContaining(keyword)
                .orElseThrow(()-> new IllegalStateException("상품 없어요"));
        List<ItemDto> itemTitleFilterDtoList = new ArrayList<>();

        for(Item item : items){
            ItemDto itemDto = ItemDto.builder()
                    .itemId(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .build();

            itemTitleFilterDtoList.add(itemDto);
        }

        return itemTitleFilterDtoList;
    }

    //카테고리 필터
    public List<ItemDto> categoryFilter(String keyword) {
        List<Item> items = itemRepository.findByCategoryContaining(keyword)
                .orElseThrow(() -> new IllegalStateException("상품 없어요"));
        List<ItemDto> itemCategoryFilterDtoList = new ArrayList<>();

        for(Item item : items){
            ItemDto itemDto = ItemDto.builder()
                    .itemId(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .build();

            itemCategoryFilterDtoList.add(itemDto);
        }

        return itemCategoryFilterDtoList;
    }
}
