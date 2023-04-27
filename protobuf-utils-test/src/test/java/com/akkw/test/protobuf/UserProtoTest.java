package com.akkw.test.protobuf;

import com.protobuf.test.UserProto;
import org.junit.Test;

import java.util.Arrays;

public class UserProtoTest {

    @Test
    public void test() {

        UserProto.MapVauleObject.Builder mapVaule = UserProto.MapVauleObject.newBuilder();
        UserProto.MapVauleObject mapVauleObject = mapVaule.setCode("MapVauleObjectCode").setName("MapVauleObject").build();


        UserProto.NickName.Builder nickName = UserProto.NickName.newBuilder();
        nickName.addNickName("asd");
        UserProto.NickName nickNameOb = nickName.build();


        UserProto.User.Builder user = UserProto.User.newBuilder();

        UserProto.User build = user.setId(1)
                .setCode("200")
                .setName("qzw")
                .addStrList("strList")
                .addRoleList(nickNameOb)
                .putMap("mapKey", "mapValue")
                .putMapObject("mapObject", mapVauleObject).build();

        System.out.println(Arrays.toString(build.toByteArray()));
    }
}