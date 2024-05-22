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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UrlPathHelper;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping({"/goods", "/board", "/notice"})
public class BbsController {
    private final BbsServiceIf bbsServiceIf;
    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model,
                     HttpServletRequest request){
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);

        if(originalURL.contains("board")) {
            pageRequestDTO.setCategory1("자유게시판");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("menu", "자유게시판");
            model.addAttribute("category1", "board");
        } else if(originalURL.contains("notice")) {
            pageRequestDTO.setCategory1("공지사항");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("menu", "공지사항");
            model.addAttribute("category1", "notice");
        } else if(originalURL.contains("goods")) {
            pageRequestDTO.setCategory1("중고플리");
            pageRequestDTO.setPage_size(9);
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            List<Integer> categoryTwoList = new ArrayList<>();
            int categoryTwoTotalCount = 0;
            String category2List[] = {"디지털기기","가구/인테리어","의류","잡화","생활가전","스포츠/레저","취미","뷰티","도서","기타"};
            for(String category2 : category2List){
                categoryTwoList.add(bbsServiceIf.countCategoryTwo(category2));
                categoryTwoTotalCount += bbsServiceIf.countCategoryTwo(category2);
            }
            model.addAttribute("categoryTwoTotalCount" , categoryTwoTotalCount);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("categoryTwoCount" , categoryTwoList);
            model.addAttribute("menu", "중고플리");
            model.addAttribute("category1", "goods");
            return "/goods/list";
        }
        return "/board/list";
    }

    @GetMapping("/view")
    public String view(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO, Model model, HttpServletRequest request){
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            pageRequestDTO.setCategory1("자유게시판");
            model.addAttribute("menu", "자유게시판");
        } else if(originalURL.contains("notice")) {
            pageRequestDTO.setCategory1("공지사항");
            model.addAttribute("menu", "공지사항");
        } else if(originalURL.contains("goods")) {
            pageRequestDTO.setCategory1("중고플리");
            model.addAttribute("menu", "중고플리");
            return "/goods/view";
        }
        BbsDTO resultbbsDTO = bbsServiceIf.view(bbsDTO);
        model.addAttribute("bbsDTO",resultbbsDTO);
        return "/board/view";
    }

    @GetMapping("/regist")
    public String regist(Model model, HttpServletRequest request){
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            model.addAttribute("menu", "자유게시판");
        } else if(originalURL.contains("notice")) {
            model.addAttribute("menu", "공지사항");
        } else if(originalURL.contains("goods")) {
            model.addAttribute("menu", "중고플리");
            return "/goods/regist";
        }
        return "/board/regist";
    }
    @Transactional
    @PostMapping("/regist")
    public String registPost(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO, Model model, MultipartHttpServletRequest files){
        //user_id ->세션으로 변경예정
        bbsDTO.setUserId("test");

        int resultidx = bbsServiceIf.regist(bbsDTO);
        if(files!=null) {
            BbsFileDTO bbsFileDTO = BbsFileDTO.builder().bbsIdx(resultidx).userId(bbsDTO.getUserId()).build();
            bbsServiceIf.registFile(bbsFileDTO, files);
        }

        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modifyGet(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO, Model model, HttpServletRequest request){
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            model.addAttribute("menu", "자유게시판");
        } else if(originalURL.contains("notice")) {
            model.addAttribute("menu", "공지사항");
        } else if(originalURL.contains("goods")) {
            model.addAttribute("menu", "중고플리");
            return "/goods/modify";
        }
        BbsDTO viewDTO = bbsServiceIf.view(bbsDTO);
        List<BbsFileDTO> fileDTO = bbsServiceIf.listFile(pageRequestDTO, bbsDTO.getBbsIdx());
        model.addAttribute("bbsDTO",viewDTO);
        model.addAttribute("fileDTO",fileDTO);
        return "/board/modify";
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
