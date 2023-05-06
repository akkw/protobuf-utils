package com.akkw.test.model;

import java.util.List;
import java.util.Map;

public class UserJavaObject {

    private int id;


    private String code;


    private String name;


    private List<String> strList;

    NickNameJava role;

    private List<NickNameJava> roleList;

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public void setNickNameJavas(List<NickNameJava> nickNameJavas) {
        this.roleList = nickNameJavas;
    }

    //    private Map<String, String> map;
//
//    private Map<String, MapVauleObjectJava> mapVauleObjectJavaMap;
}
