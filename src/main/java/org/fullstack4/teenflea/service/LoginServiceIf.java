package org.fullstack4.teenflea.service;

import org.fullstack4.teenflea.dto.LoginDTO;
import org.fullstack4.teenflea.dto.MemberDTO;

public interface LoginServiceIf {
    MemberDTO login_info(LoginDTO loginDTO);
}
