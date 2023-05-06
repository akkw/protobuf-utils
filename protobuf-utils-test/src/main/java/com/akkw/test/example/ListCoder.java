package com.akkw.test.example;

import com.akkw.test.example.model.Price;
import com.akkw.test.example.model.Sku;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListCoder {

    public static void main(String[] args) {
        Sku sku = new Sku();
        sku.prices = new ArrayList<>();
        sku.nameList = new ArrayList<>();

        sku.nameList.add("name");
        sku.prices.add(new Price(10));
        Field[] declaredFields = sku.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            System.out.println(field.getType());
            System.out.println((field.getGenericType()));
        }
    }


}
