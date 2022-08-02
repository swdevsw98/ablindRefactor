package com.example.demo.controller;

import com.example.demo.dto.cart.DeliveryDto;
import com.example.demo.service.cart.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/delivery")
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/pay")
    public ResponseEntity payDelivery(@RequestBody DeliveryDto deliveryDto){
        deliveryService.startDelivery(deliveryDto);
        return new ResponseEntity("배달시작~", HttpStatus.OK);
    }

}
