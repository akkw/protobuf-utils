package com.akkw.test.model;

import com.protobuf.test.UserProto;

import java.util.List;
import java.util.Map;

public class UserJavaObject {

    private int id;


    private String code;


    private String name;


    private List<String> strList;

    NickNameJava nickNameJava;

    private List<NickNameJava> nickNameList;

    private byte[] body;


    private Map<String, String> map;


    private Map<String, MapVauleObjectJava> mapObject;


    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setMapObject(Map<String, MapVauleObjectJava> mapObject) {
        this.mapObject = mapObject;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

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


    public void setNickNameJava(NickNameJava nickNameJava) {
        this.nickNameJava = nickNameJava;
    }

    public void setNickNameList(List<NickNameJava> nickNameList) {
        this.nickNameList = nickNameList;
    }

    //    private Map<String, String> map;
//
//    private Map<String, MapVauleObjectJava> mapVauleObjectJavaMap;
}
