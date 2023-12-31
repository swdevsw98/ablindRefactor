package com.example.demo.service.admin;

import com.example.demo.dto.order.OrderDetailDto;
import com.example.demo.dto.order.OrderFilterDto;
import com.example.demo.dto.order.OrderItemDto;
import com.example.demo.dto.shop.ItemDto;
import com.example.demo.dto.shop.ItemQnaAnswerDto;
import com.example.demo.dto.shop.ItemQnaDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.cart.DeliveryStatus;
import com.example.demo.entity.shop.*;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.shop.*;
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

@Service
@Transactional
@RequiredArgsConstructor
public class AdminShopService {
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImagesRepository itemImagesRepository;
    private final ItemOptionRepository itemOptionRepository;

    private final ItemQnaRepository itemQnaRepository;
    private final OrderRepository orderRepository;
    private final ItemReviewRepository itemReviewRepository;

    /**
     * 아이템 추가
     * @param email
     * @param multipartFiles
     * @param detailImg
     * @param itemDto
     * @return
     * @throws IOException
     */
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

    /**
     * 아이템 삭제
     * @param email
     * @param id
     * @return
     */
    public ResponseEntity deleteItem(String email, Long id){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        Item item = itemRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("그런 아이템 없음"));

        for(ItemReviewBoard itemReviewBoard : item.getItemReviewBoards()){
            itemReviewRepository.deleteById(itemReviewBoard.getId());
        }

        for(ItemQnaBoard itemQnaBoard : item.getItemQnaBoards()) {
            itemQnaRepository.deleteById(itemQnaBoard.getId());
        }

        for(ItemOption itemOption : item.getOptions()){
            itemOptionRepository.deleteById(itemOption.getId());
        }
        for(ItemImages itemImage : item.getImages()) {
            itemImagesRepository.deleteById(itemImage.getId());
        }
        itemRepository.delete(item);


        return new ResponseEntity("삭제 완료", HttpStatus.OK);
    }

    /**아이템 qna 출력
     *
     */
    public List<ItemQnaDto> listItem(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        List<ItemQnaBoard> itemQnaBoards = itemQnaRepository.findByAnswerIsNull()
                .orElseThrow(() -> new IllegalStateException("답변 다 달림"));
        List<ItemQnaDto> itemQnaDtos = new ArrayList<>();

        for(ItemQnaBoard itemQnaBoard : itemQnaBoards) {
            ItemQnaDto itemQnaDto = ItemQnaDto.builder()
                    .itemId(itemQnaBoard.getItemQnaId().getId())
                    .qnaBoardId(itemQnaBoard.getId())
                    .title(itemQnaBoard.getTitle())
                    .content(itemQnaBoard.getContent())
                    .username(itemQnaBoard.getMember().getName())
                    .updatedAt(itemQnaBoard.getUpdatedAt())
                    .build();

            itemQnaDtos.add(itemQnaDto);
        }

        return itemQnaDtos;
    }

    /** qna 답변
     */
    public ResponseEntity qnaAnswer(String email, ItemQnaAnswerDto itemQnaAnswerDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());
        ItemQnaBoard itemQnaBoard = itemQnaRepository.findById(itemQnaAnswerDto.getQnaBoardId())
                .orElseThrow(() -> new IllegalStateException("그런 질문 없음"));

        itemQnaBoard.setAnswer(itemQnaAnswerDto.getAnswer());
        itemQnaRepository.save(itemQnaBoard);
        return new ResponseEntity("답변 달림" ,HttpStatus.OK);
    }

    /**
     * 질문 삭제
     */
    public ResponseEntity deleteAnswer(String email, ItemQnaAnswerDto itemQnaAnswerDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        ItemQnaBoard itemQnaBoard = itemQnaRepository.findById(itemQnaAnswerDto.getQnaBoardId())
                .orElseThrow(() -> new IllegalStateException("그런 질문 없음"));

        if(itemQnaBoard.getAnswer() == null){
            return new ResponseEntity("삭제할 답변이 없음", HttpStatus.BAD_REQUEST);
        }

        itemQnaBoard.setAnswer(null);
        itemQnaRepository.save(itemQnaBoard);
        return new ResponseEntity("답변 삭제" ,HttpStatus.OK);
    }

    /**
     * 주문 리스트 출력
     */
    public List<OrderDetailDto> listOrder(String email, OrderFilterDto orderFilterDto){
        List<Order> orders = orderRepository.findAll();
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();

        for(Order order : orders){
            OrderDetailDto orderDetailDto = OrderDetailDto.builder()
                    .orderStatus(order.getOrderStatus())
                    .price(order.getPrice())
                    .createdAt(order.getCreatedAt())
                    .id(order.getId())
                    .ordererName(order.getOrdererName())
                    .ordererPhoneNumber(order.getOrdererPhoneNumber())
                    .ordererAccountName(order.getOrdererAccountName())
                    .ordererAccount(order.getOrdererAccount())
                    .recipientAddress(order.getRecipientAddress())
                    .recipientName(order.getRecipientName())
                    .recipientPhoneNumber(order.getRecipientPhoneNumber())
                    .build();

            for(OrderItem orderItem : order.getOrderItems()){
                OrderItemDto itemDto = new OrderItemDto();
                itemDto.setOrderItem(orderItem);
                orderDetailDto.getOrderItems().add(itemDto);
            }

            //필터링 없음
            if(orderFilterDto.getOrderer() == null && orderFilterDto.getOrderStatus() == null) {
                orderDetailDtos.add(orderDetailDto);
            }
            //orderer 필터
            else if(orderFilterDto.getOrderer() != null && orderFilterDto.getOrderStatus() == null) {
                if(orderDetailDto.getOrdererName().equals(orderFilterDto.getOrderer())){
                    orderDetailDtos.add(orderDetailDto);
                }
            }
            //orderStatus 필터
            else if(orderFilterDto.getOrderer() == null && orderFilterDto.getOrderStatus() != null) {
                if(orderDetailDto.getOrderStatus().equals(orderFilterDto.getOrderStatus())){
                    orderDetailDtos.add(orderDetailDto);
                }
            }
        }

        return orderDetailDtos;
    }

    /**
     * 주문상태 변경
     */
    public ResponseEntity updateOrderStatus(String email, OrderDetailDto orderDetailDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("그런 유저 없음"));
        isAdmin(member.getRole());

        Order order = orderRepository.findById(orderDetailDto.getId())
                .orElseThrow(() -> new IllegalStateException("그런 주문 없음"));

        if(orderDetailDto.getOrderStatus().equals("주문완료")){
            order.setOrderStatus("주문완료");
            order.setDelivery(null);
        } else if(orderDetailDto.getOrderStatus().equals("배송 준비 중") && order.getDelivery() != null){
            order.setOrderStatus("배송 준비 중");
            order.getDelivery().setDeliveryStatus(DeliveryStatus.READY);
        } else if(orderDetailDto.getOrderStatus().equals("배송 중") && order.getDelivery() != null){
            order.setOrderStatus("배송 중");
            order.getDelivery().setDeliveryStatus(DeliveryStatus.GO);
        } else if(orderDetailDto.getOrderStatus().equals("배송 취소") && order.getDelivery() != null){
            order.setOrderStatus("배송 취소");
            order.getDelivery().setDeliveryStatus(DeliveryStatus.CANCEL);
        } else if(orderDetailDto.getOrderStatus().equals("배송완료") && order.getDelivery() != null){
            order.setOrderStatus("배송완료");
            order.getDelivery().setDeliveryStatus(DeliveryStatus.END);
        }
        else {
            return new ResponseEntity("배송 상태를 확인해주세요.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("업데이트 성공", HttpStatus.OK);
    }

    private void isAdmin(String role) {
        if(role != "ADMIN") {
            new IllegalStateException("관리자 승인을 받으세요.");
        }
    }
}
