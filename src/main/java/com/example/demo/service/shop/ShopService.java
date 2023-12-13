package com.example.demo.service.shop;

import com.amazonaws.Response;
import com.example.demo.dto.shop.ArtistProductRequest;
import com.example.demo.dto.shop.ItemDto;
import com.example.demo.dto.shop.ProductsResponse;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.ArtistProduct;
import com.example.demo.entity.shop.Item;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.ArtistProductRepository;
import com.example.demo.repository.shop.ItemRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ArtistProductRepository artistProductRepository;
    private final S3Uploader s3Uploader;


    //메인페이지
    public List<ItemDto> list() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(Item item : items){
            ItemDto itemDto = ItemDto.builder()
                    .author(item.getAuthor())
                    .images(item.getImages())
                    .itemId(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .category(item.getCategory())
                    .build();

            itemDtoList.add(itemDto);
        }

        return itemDtoList;
    }

    //디테일 페이지
    public ItemDto detailList(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("없는 상품"));
        ItemDto itemDto = ItemDto.builder()
                .author(item.getAuthor())
                .images(item.getImages())
                .itemId(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .detailImg(item.getDetailImg())
                .options(item.getOptions())
                .build();

        return itemDto;
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

    //작가 상품 등록
    public ResponseEntity addArtistProduct(ArtistProductRequest artistProductRequest, String email,
                                           MultipartFile image) throws IOException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Not found User"));
        String[] values = s3Uploader.upload(image,"artist/product");

        ArtistProduct artistProduct = new ArtistProduct(member, artistProductRequest, values[1]);


        artistProductRepository.save(artistProduct);

        return new ResponseEntity(HttpStatus.OK);
    }


    //작가 상품 출력
    public List<ProductsResponse> getProducts() {
        return artistProductRepository.findAll()
                .stream()
                .map(
                        product -> new ProductsResponse(
                                product.getImage(),
                                product.getTitle(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getId(),
                                product.getMember().getName()
                        )
                )
                .collect(Collectors.toList());
    }
}
