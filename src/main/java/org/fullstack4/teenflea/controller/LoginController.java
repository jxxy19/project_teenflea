package org.fullstack4.teenflea.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.service.LoginServiceIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Log4j2
@RequiredArgsConstructor
@Controller
public class LoginController {
    private final LoginServiceIf loginServiceIf;
    @GetMapping("/login")
    public void loginGET(HttpServletRequest req, Model model){
        String acc_url = req.getHeader("referer");
        model.addAttribute("acc_url", acc_url);
    }

//    @PostMapping("/login")
//    public String loginPost(LoginDTO loginDTO,
//                          @RequestParam(name="acc_url", defaultValue = "/", required = false) String acc_url,
//                          HttpServletRequest req,
//                          Model model,
//                          HttpServletResponse response,
//                          RedirectAttributes redirectAttributes){
//        HttpSession session = req.getSession();
//        loginDTO.setPwd(commonUtil.encryptPwd(loginDTO.getPwd()));
//        MemberDTO LoginMemberDTO = loginServiceIf.login(loginDTO);
//        session.setAttribute("memberDTO",LoginMemberDTO);
//        if(req.getServletPath().equals("/login")){
//            return "redirect:/";
//        }
//        return "redirect:"+acc_url;
//    }

    @GetMapping("/logout")
    public String logoutGet(HttpServletRequest req, Model model){
        HttpSession session = req.getSession(false);
        if(session!=null) {
            session.invalidate();
        }
        return "redirect:/";
    }


}
