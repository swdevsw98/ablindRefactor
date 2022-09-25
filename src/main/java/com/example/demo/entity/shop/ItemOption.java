package com.example.demo.entity.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "item_option")
@Entity
public class ItemOption {

    @Id
    @GeneratedValue
    @Column(name = "item_option_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Item item;

    private String itemOption;

    //==생성메소드==/
    public void setItem(Item item){
        this.item = item;
        item.getOptions().add(this);
    }
}
