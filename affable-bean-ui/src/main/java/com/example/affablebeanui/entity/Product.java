package com.example.affablebeanui.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter

public class Product {

    private int id;

    private String name;

    private double price;
    private  int quantity;
   private List<Integer> quantityList= new ArrayList<>();

    private String description;
 /*   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdate;*/


    private LocalDateTime lastUpdate;

    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
