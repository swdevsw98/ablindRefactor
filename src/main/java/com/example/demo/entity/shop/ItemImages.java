package com.example.demo.entity.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "item_images")
@Entity
public class ItemImages {

    @Id
    @GeneratedValue
    @Column(name = "item_images_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Item item;

    private String url;

    //==생성메소드==/
    public void setItem(Item item){
        this.item = item;
        item.getImages().add(this);
    }
}
