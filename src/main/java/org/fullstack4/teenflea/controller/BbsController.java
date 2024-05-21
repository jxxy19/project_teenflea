package org.fullstack4.teenflea.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.dto.*;
import org.fullstack4.teenflea.service.BbsServiceIf;
import org.fullstack4.teenflea.util.CommonFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class BbsController {
    private final BbsServiceIf bbsServiceIf;
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
        model.addAttribute("pageResponseDTO" , pageResponseDTO);
    }

    @GetMapping("/view")
    public void view(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO, Model model, HttpServletRequest request){
        BbsDTO resultbbsDTO = bbsServiceIf.view(bbsDTO);
        model.addAttribute("bbsDTO",resultbbsDTO);
    }

    @GetMapping("/regist")
    public void regist(){
    }
    @Transactional
    @PostMapping("/regist")
    public String registPost(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO, Model model, MultipartHttpServletRequest files){
        int resultidx = bbsServiceIf.regist(bbsDTO);
        if(files!=null) {
            BbsFileDTO bbsFileDTO = BbsFileDTO.builder().bbsIdx(resultidx).userId(bbsDTO.getUserId()).build();
            bbsServiceIf.registFile(bbsFileDTO, files);
        }

        return "redirect:/";
    }

    @GetMapping("/modify")
    public void modifyGet(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO, Model model){
        BbsDTO viewDTO = bbsServiceIf.view(bbsDTO);
        List<BbsFileDTO> fileDTO = bbsServiceIf.listFile(pageRequestDTO, bbsDTO.getBbsIdx());
        model.addAttribute("bbsDTO",viewDTO);
        model.addAttribute("fileDTO",fileDTO);
    }
    @Transactional
    @PostMapping("/modify")
    public String modifyPost(BbsDTO bbsDTO, Model model, MultipartHttpServletRequest files){
        bbsServiceIf.modify(bbsDTO);
        if(files!=null) {
            BbsFileDTO bbsFileDTO = BbsFileDTO.builder().bbsIdx(bbsDTO.getBbsIdx()).userId(bbsDTO.getUserId()).build();
            bbsServiceIf.registFile(bbsFileDTO, files);
        }
        return "redirect:/";
    }

    @Transactional
    @GetMapping("/delete")
    public String delete(BbsDTO bbsDTO){
        bbsServiceIf.delete(bbsDTO);
        return "redirect:/";
    }
    @GetMapping("/deletefile")
    public String deleteFile(BbsFileDTO bbsFileDTO){
        bbsServiceIf.deleteFile(bbsFileDTO);
        return "redirect:/";
    }

    @GetMapping("/deletereply")
    public String deleteFile(BbsReplyDTO bbsReplyDTO){
        bbsServiceIf.deleteReply(bbsReplyDTO);
        return "redirect:/";
    }

}
