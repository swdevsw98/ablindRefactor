package com.example.demo.controller;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.admin.MainBannerDto;
import com.example.demo.dto.artist.ArtistDetailDto;
import com.example.demo.dto.order.OrderDetailDto;
import com.example.demo.dto.order.OrderFilterDto;
import com.example.demo.dto.shop.ItemDto;
import com.example.demo.dto.shop.ItemQnaAnswerDto;
import com.example.demo.dto.shop.ItemQnaDto;
import com.example.demo.entity.MainBanner;
import com.example.demo.service.admin.AdminArtistService;
import com.example.demo.service.admin.AdminService;
import com.example.demo.service.admin.AdminShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;
    private final AdminShopService adminShopService;
    private final AdminArtistService adminArtistService;

    @PostMapping("/add/mainbanner")
    public ResponseEntity addMainBanner(@RequestPart(value="file", required = false)MultipartFile multipartFile,
                                        @RequestPart(value = "MainBannerDto")MainBannerDto mainBannerDto,
                                        ServletRequest request) throws IOException {
        return adminService.addMainBanner(getEmail(request), multipartFile, mainBannerDto);
    }

    @DeleteMapping("/delete/mainbanner")
    public ResponseEntity deleteMainBanner(@RequestBody MainBannerDto mainBannerDto,
                                           ServletRequest request){
        return adminService.deleteMainBanner(getEmail(request),mainBannerDto.getId());
    }

    @PostMapping("/add/item")
    public ResponseEntity addItem(@RequestPart(value = "img", required = false) MultipartFile[] multipartFiles,
                                  @RequestPart(value = "detail", required = false) MultipartFile detail,
                                  @RequestPart(value = "itemDto")ItemDto itemDto,
                                  ServletRequest request) throws IOException{
        return adminShopService.addItem(getEmail(request), multipartFiles, detail, itemDto);
    }

    @DeleteMapping("/delete/item")
    public ResponseEntity deleteItem(@RequestBody ItemDto itemDto,
                                     ServletRequest request) {
        return adminShopService.deleteItem(getEmail(request), itemDto.getItemId());
    }

    @GetMapping("/list/qna/noanswer")
    public List<ItemQnaDto> listNoAnswerQna(ServletRequest request){
        return adminShopService.listItem(getEmail(request));
    }

    @PostMapping("/list/qna/answer")
    public ResponseEntity answerQna(@RequestBody ItemQnaAnswerDto itemQnaAnswerDto,
                                    ServletRequest request) {
        return adminShopService.qnaAnswer(getEmail(request), itemQnaAnswerDto);
    }

    @DeleteMapping("/list/qna/answer/delete")
    public ResponseEntity deleteAnswerQna(@RequestBody ItemQnaAnswerDto itemQnaAnswerDto,
                                          ServletRequest request){
        return adminShopService.deleteAnswer(getEmail(request), itemQnaAnswerDto);
    }

    @GetMapping("/list/order")
    public List<OrderDetailDto> listOrder(ServletRequest request,
                                          @RequestParam(value = "orderer", required = false) String orderer,
                                          @RequestParam(value = "orderStatus", required = false) String orderStatus){
        OrderFilterDto orderFilterDto = new OrderFilterDto();
        if(orderer != null)
            orderFilterDto.setOrderer(orderer);
        if(orderStatus != null)
            orderFilterDto.setOrderStatus(orderStatus);

        return adminShopService.listOrder(getEmail(request), orderFilterDto);
    }

    @PostMapping("/artist/add")
    public ResponseEntity addArtist(ServletRequest request,
                                    @RequestPart(value = "artistDetailDto")ArtistDetailDto artistDetailDto,
                                    @RequestPart(value = "profile") MultipartFile profile,
                                    @RequestPart(value = "backGround") MultipartFile backGround,
                                    @RequestPart(value = "detail") MultipartFile detail) throws IOException{
        return adminArtistService.addArtist(getEmail(request), artistDetailDto, profile, backGround, detail);
    }

    @DeleteMapping("/artist/delete")
    public ResponseEntity deleteArtist(ServletRequest request,
                                       @RequestBody ArtistDetailDto artistDetailDto) {
        return adminArtistService.deleteArtist(getEmail(request), artistDetailDto);
    }

    private String getEmail(ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        return jwtTokenProvider.getUserPk(token);
    }
}
