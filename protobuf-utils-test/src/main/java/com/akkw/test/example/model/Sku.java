package com.akkw.test.example.model;

import java.util.List;

public class Sku {
    public List<String> nameList;

    public List<Price> prices;


    private List<User> users;


    public Sku(List<String> nameList, List<Price> prices, List<User> users) {
        this.nameList = nameList;
        this.prices = prices;
        this.users = users;
    }
}
