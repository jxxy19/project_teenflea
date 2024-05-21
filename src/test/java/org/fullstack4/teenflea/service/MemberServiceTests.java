package org.fullstack4.teenflea.service;

import jakarta.validation.constraints.NotEmpty;
import org.fullstack4.teenflea.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberServiceIf memberServiceIf;

    @Test
    public void testRegist(){
        MemberDTO memberDTO = MemberDTO.builder()
                .userId("test")
                .name("채종윤")
                .pwd("1234")
                .email("je@naver.com")
                .phoneNumber("010-7777-7777")
                .birthday("1998-02-05")
                .addr1("서울시 노원구")
                .addr2("2층")
                .zipCode("01635")
                .userState("Y")
                .build();

        int result=memberServiceIf.regist(memberDTO);
    }

    @Test
    public void testModify(){
        MemberDTO memberDTO = MemberDTO.builder()
                .userId("test")
                .pwd("12345678")
                .email("update@naver.com")
                .phoneNumber("010-4444-4444")
                .addr1("서울시 수정")
                .addr2("3층")
                .zipCode("87777")
                .userState("Y")
                .build();

        memberServiceIf.modify(memberDTO);
    }

    @Test
    public void testDelete(){
        String user_id = "test2";
        memberServiceIf.delete(user_id);
    }

    @Test
    public void testList(){

    }

    @Test
    public void testView(){
        String user_id ="test";
        MemberDTO memberDTO = memberServiceIf.view(user_id);
        System.out.println("memberDTO : " + memberDTO);
    }
}
