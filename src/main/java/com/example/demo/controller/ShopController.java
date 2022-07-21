package com.example.demo.controller;

import com.example.demo.dto.shop.ItemDto;
import com.example.demo.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/shop")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("")
    public List<ItemDto> listItem(){
        List<ItemDto> list = shopService.list();

        return list;
    }

    @GetMapping("/search/filter")
    public List<ItemDto> filterItem(@RequestBody Map<String, String> keyword) {
        List<ItemDto> list = shopService.titleFilter(keyword.get("keyword"));

        return list;
    }

    @GetMapping("/category/filter")
    public List<ItemDto> filterCategory(@RequestParam(value = "keyword") String keyword){
        List<ItemDto> list = shopService.categoryFilter(keyword);

        return list;
    }
}
