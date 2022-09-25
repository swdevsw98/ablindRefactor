package com.example.demo.service.admin;

import com.example.demo.dto.shop.ItemDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.shop.Item;
import com.example.demo.entity.shop.ItemImages;
import com.example.demo.entity.shop.ItemOption;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.ItemImagesRepository;
import com.example.demo.repository.shop.ItemOptionRepository;
import com.example.demo.repository.shop.ItemRepository;
import com.example.demo.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminShopService {
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImagesRepository itemImagesRepository;
    private final ItemOptionRepository itemOptionRepository;

    public ResponseEntity addItem(String email, MultipartFile[] multipartFiles,
                                  MultipartFile detailImg,
                                  ItemDto itemDto) throws IOException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        Item item = new Item(itemDto.getName(), itemDto.getAuthor(), itemDto.getPrice(),
                itemDto.getCategory());

        String[] values = s3Uploader.upload(detailImg,"item/detailImg");
        item.setDetailImg(values[1]);

        for(MultipartFile ml : multipartFiles){
            String[] val = s3Uploader.upload(ml, "item/Image");
            ItemImages img = new ItemImages();
            img.setUrl(val[1]);
            img.setItem(item);
            itemImagesRepository.save(img);
        }

        for(ItemOption op : itemDto.getOptions()){
            op.setItem(item);
            item.getOptions().add(op);
            itemOptionRepository.save(op);
        }

        itemRepository.save(item);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteItem(String email, Long id){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        itemRepository.deleteById(id);
        return new ResponseEntity("삭제 완료", HttpStatus.OK);
    }
    private void isAdmin(String role) {
        if(role != "ADMIN") {
            new IllegalStateException("관리자 승인을 받으세요.");
        }
    }
}
