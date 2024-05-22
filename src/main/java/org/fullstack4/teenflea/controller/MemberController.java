package org.fullstack4.teenflea.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.dto.MemberDTO;
import org.fullstack4.teenflea.service.MemberServiceIf;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void registGET(Model model){
        model.addAttribute("menu", "회원가입");
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
        System.out.println("result : " + result );
        if(result > 0 ){
           redirectAttributes.addFlashAttribute("info","alert(`가입이 완료되었습니다.`);");
            return "redirect:/login/login";
        }
        else{
          return "/member/regist";
       }
    }

    @GetMapping("/complete")
    public void completeGET(Model model){
        model.addAttribute("menu", "회원가입 완료");
    }

    //아이디 중복체크
    @GetMapping("/duplecheck")
    @ResponseBody
    public ResponseEntity<?> duplecheckGET(@RequestParam("userId") String userId,HttpServletRequest request,HttpServletResponse response,
                              Model model) {
        System.out.println("usrId idcheck getcontroller : " + userId);
        Boolean result = false;
        result = memberServiceIf.idCheck(userId);
        System.out.println("result controller : " + result) ;

        //아이디 중복으로 있으면 (true)
        if(result){
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message","중복된 아이디입니다.");

            return ResponseEntity.ok().body(resp);

        }
        else{
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);


            return ResponseEntity.ok().body(resp);

        }

    }

    /*@GetMapping("/modify")
    public void modifyGET(){
    }*/



    @PostMapping("/modify")
    public String modifyPOST(@Valid MemberDTO memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes,HttpServletRequest request){


        System.out.println("memberDTO : " + memberDTO);
        if(bindingResult.hasErrors()){
            System.out.println("bindingResult.getAllErrors() " + bindingResult.getAllErrors());
            log.info("bindingResult Errors : " +memberDTO);
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("info","alert(`회원 정보 수정 실패 올바른 값을 입력해 주세요.`);");
            return "redirect:/member/modify";
       }
        MemberDTO modifyDTO = memberServiceIf.modify(memberDTO);
        request.getSession().setAttribute("memberDTO",modifyDTO);
        redirectAttributes.addFlashAttribute("info","alert(`회원 정보 수정 성공`);");
        return "redirect:/member/modify";
    }


    @GetMapping("/modify")
    public void viewGET(@RequestParam(name = "userId", defaultValue = "") String userId,Model model){
        model.addAttribute("menu", "내 정보 수정");
//
//        MemberDTO memberDTO = memberServiceIf.view(userId);
//        model.addAttribute("memberDTO",memberDTO);
    }
    @GetMapping("/delete")
    public String deletePOST(HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO dto = (MemberDTO)session.getAttribute("memberDTO");
        String userId = dto.getUserId();
        System.out.println("user_id " +userId);
        log.info("============================");
        log.info("MemberController deletePOST");
        log.info("============================");
        int result = memberServiceIf.delete(userId);
        System.out.println("result controller : " + result);
        if(result > 0 ){
            request.getSession().invalidate();
            return "redirect:/login/logout";
        }
        else{
            return "/member/modify";
        }
    }
}
