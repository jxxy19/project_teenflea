package org.fullstack4.teenflea.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.dto.MemberDTO;
import org.fullstack4.teenflea.service.MemberServiceIf;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceIf memberServiceIf;
   /* @GetMapping("/view")
    public void view(){
    }*/
    @GetMapping("/regist")
    public void registGET(){
    }
    @PostMapping("/regist")
    public String registPOST(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
       /* memberDTO.setPassword(commonUtil.encryptPwd(memberDTO.getPassword()));*/
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            model.addAttribute("memberDTO",memberDTO);
            return "/member/regist";
        }
        int result = memberServiceIf.regist(memberDTO);
        if(result > 0 ){
           redirectAttributes.addFlashAttribute("info","alert(`가입이 완료되었습니다.`);");
            return "redirect:/login";
        }
        else{
          return "/member/regist";
       }
    }

    //아이디 중복체크
//    @GetMapping("/duplecheck")
//    public void duplecheckGET(HttpServletRequest request, HttpServletResponse response){
//        String user_id = request.getParameter("user_id");
//        int count = 0;
//        if(memberServiceIf.idCheck(user_id) >0){
//            count = memberServiceIf.idCheck(user_id);
//        }
//        if(count > 0) {
//            try {
//                response.getWriter().print("N");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        }else {
//            try {
//                response.getWriter().print("Y");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    /*@GetMapping("/modify")
    public void modifyGET(){
    }*/



    @PostMapping("/modify")
    public String modifyPOST(@Valid MemberDTO memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes,HttpServletRequest request){
        if(bindingResult.hasErrors()){
            log.info("bindingResult Errors : " +memberDTO);
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("info","alert(`회원 정보 수정 실패 올바른 값을 입력해 주세요.`);");
            return "redirect:/member/modify";
       }
        MemberDTO modifyDTO = memberServiceIf.modify(memberDTO);
        request.getSession().setAttribute("memberDTO",modifyDTO);
        redirectAttributes.addFlashAttribute("info","alert(`회원 정보 수정 성공`);");
        return "redirect:/member/view";
    }


    @GetMapping("/view")
    public void viewGET(@RequestParam("userId") String userId,Model model){

        MemberDTO memberDTO = memberServiceIf.view(userId);
        model.addAttribute("memberDTO",memberDTO);
    }
    @PostMapping("/delete")
    public String deletePOST(String user_id, HttpServletRequest request){
        log.info("============================");
        log.info("MemberController deletePOST");
        log.info("============================");
        int result = memberServiceIf.delete(user_id);
        if(result > 0 ){
            request.getSession().invalidate();
            return "redirect:/login/logout";
        }
        else{
            return "/member/view?user_id="+user_id;
        }
    }
}
