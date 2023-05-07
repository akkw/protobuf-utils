package com.akkw.test.protobuf;

import com.google.protobuf.ByteString;
import com.protobuf.test.UserProto;
import org.junit.Test;

import java.util.Arrays;

public class UserProtoTest {
    /**
     * id: 2
     * code: 7
     * name: 12
     * strList: 21
     * nickName: 5
     * nickNameJavas: 29
     * 29
     */
    @Test
    public void test() {

        UserProto.MapVauleObject.Builder mapVaule = UserProto.MapVauleObject.newBuilder();
        UserProto.MapVauleObject mapVauleObject = mapVaule.setCode("MapVauleObjectCode").setName("MapVauleObject").build();




        UserProto.User.Builder user = UserProto.User.newBuilder();

        UserProto.User build = user.setId(1)
                .setCode("")
                .setName("qzw")
                .addStrList("strList")
                .addStrList("asd1")
                .addStrList("asd")
                .addStrList("afasd")
                .addRoleList(UserProto.NickName.newBuilder().addNickName("").build())
                .addRoleList(UserProto.NickName.newBuilder().addNickName("").build())
                .addRoleList(UserProto.NickName.newBuilder().addNickName("sadfasg").build())
                .addRoleList(UserProto.NickName.newBuilder().addNickName("sadfasg123").build())
                .addRoleList(UserProto.NickName.newBuilder().addNickName("11345345").build())
                .addRoleList(UserProto.NickName.newBuilder().addNickName("fgdhfdg").build())
                .setRole(UserProto.NickName.newBuilder().addNickName("asd").build())
                .setBody(ByteString.copyFrom("123456".getBytes()))
                .build();

        System.out.println(Arrays.toString(build.toByteArray()));
        System.out.println(build.toByteArray().length);
    }
}