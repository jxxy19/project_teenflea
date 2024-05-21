package org.fullstack4.teenflea.service;

import org.fullstack4.teenflea.dto.MemberDTO;

public interface MemberServiceIf {

    int regist(MemberDTO memberDTO);

    MemberDTO view(String user_id);

    void modify(MemberDTO memberDTO);

    void delete(int idx);
}
