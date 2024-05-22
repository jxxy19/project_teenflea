package org.fullstack4.teenflea.service;

import org.fullstack4.teenflea.dto.MemberDTO;

public interface MemberServiceIf {

    int regist(MemberDTO memberDTO);

    MemberDTO view(String userId);

    MemberDTO modify(MemberDTO memberDTO);

    int delete(String userId);

    //아이디중복체크
    Boolean idCheck(String user_id);
}
