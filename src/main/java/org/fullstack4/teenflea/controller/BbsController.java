package org.fullstack4.teenflea.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.dto.*;
import org.fullstack4.teenflea.service.BbsServiceIf;
import org.fullstack4.teenflea.util.CommonFileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private final CommonFileUtil commonFileUtil;
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
            model.addAttribute("registLink", "location.href='/board/regist'");
        } else if(originalURL.contains("notice")) {
            pageRequestDTO.setCategory1("공지사항");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("menu", "공지사항");
            model.addAttribute("category1", "notice");
            model.addAttribute("registLink", "location.href='/notice/regist'");
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
        BbsDTO resultbbsDTO = bbsServiceIf.view(bbsDTO);
        List<BbsFileDTO> bbsFileDTOList = bbsServiceIf.listFile(pageRequestDTO, bbsDTO.getBbsIdx());
        List<BbsReplyDTO> bbsReplyDTOList = bbsServiceIf.listReply(pageRequestDTO, bbsDTO.getBbsIdx());
        model.addAttribute("bbsDTO",resultbbsDTO);
        model.addAttribute("bbsFileDTOList",bbsFileDTOList);
        model.addAttribute("bbsReplyDTOList",bbsReplyDTOList);
        if(resultbbsDTO.getCategory1().equals("자유게시판")){
            model.addAttribute("category1", "board");
        }
        else if(resultbbsDTO.getCategory1().equals("공지사항")){
            model.addAttribute("category1", "notice");
        }
        else{
            model.addAttribute("category1", "goods");
        }
        if(originalURL.contains("board")) {
            model.addAttribute("menu", "자유게시판");
        } else if(originalURL.contains("notice")) {
            model.addAttribute("menu", "공지사항");
        } else if(originalURL.contains("goods")) {
            model.addAttribute("menu", "중고플리");
            return "/goods/view";
        }
        return "/board/view";
    }

    @GetMapping("/regist")
    public String regist(Model model, HttpServletRequest request){
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            model.addAttribute("menu", "자유게시판");
            model.addAttribute("category1", "board");
        } else if(originalURL.contains("notice")) {
            model.addAttribute("menu", "공지사항");
            model.addAttribute("category1", "notice");
        } else {
            model.addAttribute("menu", "중고플리");
            model.addAttribute("category1", "goods");
            return "/goods/regist";
        }
        return "/board/regist";
    }
    @Transactional
    @PostMapping("/regist")
    public String registPost(BbsDTO bbsDTO, MultipartHttpServletRequest files, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        bbsDTO.setUserId(memberDTO.getUserId());
        if(files!=null) {
            bbsDTO = bbsServiceIf.registThumbnail(bbsDTO,files);
        }
        int resultidx = bbsServiceIf.regist(bbsDTO);
        if(files!=null) {
            BbsFileDTO bbsFileDTO = BbsFileDTO.builder().bbsIdx(resultidx).userId(bbsDTO.getUserId()).build();
            bbsServiceIf.registFile(bbsFileDTO, files);
        }
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(bbsDTO.getCategory1().equals("자유게시판")) {
            return "redirect:/board/view?bbsIdx="+resultidx;
        }else if(bbsDTO.getCategory1().equals("공지사항")){
            return "redirect:/notice/view?bbsIdx="+resultidx;
        }
        else {
            return "redirect:/goods/view?bbsIdx="+resultidx;
        }
    }

    @GetMapping("/modify")
    public String modifyGet(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO, Model model, HttpServletRequest request){
        BbsDTO viewDTO = bbsServiceIf.view(bbsDTO);
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!memberDTO.getUserId().equals(viewDTO.getUserId()) && memberDTO.getRole().equals("user")){
            return "redirect:/";
        }
        List<BbsFileDTO> fileDTOList = bbsServiceIf.listFile(pageRequestDTO, bbsDTO.getBbsIdx());
        model.addAttribute("bbsDTO",viewDTO);
        model.addAttribute("bbsFileDTOList",fileDTOList);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            model.addAttribute("menu", "자유게시판");
            model.addAttribute("category1", "board");
        } else if(originalURL.contains("notice")) {
            model.addAttribute("menu", "공지사항");
            model.addAttribute("category1", "notice");
        } else {
            model.addAttribute("menu", "중고플리");
            model.addAttribute("category1", "goods");
            return "/goods/modify";
        }
        return "/board/modify";
    }
    @Transactional
    @PostMapping("/modify")
    public String modifyPost(BbsDTO bbsDTO, MultipartHttpServletRequest files, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!memberDTO.getUserId().equals(bbsDTO.getUserId()) && memberDTO.getRole().equals("user")){
            return "redirect:/";
        }
        if(files!=null) {
            bbsServiceIf.modifyThumbnail(bbsDTO,files);
        }
        if(files!=null) {
            BbsFileDTO bbsFileDTO = BbsFileDTO.builder().bbsIdx(bbsDTO.getBbsIdx()).userId(bbsDTO.getUserId()).build();
            bbsServiceIf.registFile(bbsFileDTO, files);
        }
        if(bbsDTO.getCategory1().contains("중고플리")){
            bbsServiceIf.modifyGoods(bbsDTO);
            return "redirect:/goods/view?bbsIdx="+bbsDTO.getBbsIdx();
        }
        else if(bbsDTO.getCategory1().contains("자유게시판")){
            bbsServiceIf.modify(bbsDTO);
            return "redirect:/board/view?bbsIdx=" + bbsDTO.getBbsIdx();
        }
        else {
            bbsServiceIf.modify(bbsDTO);
            return "redirect:/notice/view?bbsIdx=" + bbsDTO.getBbsIdx();
        }
    }

    @Transactional
    @GetMapping("/delete")
    public String delete(BbsDTO bbsDTO, HttpServletRequest request){
        BbsDTO viewDTO = bbsServiceIf.view(bbsDTO);
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!memberDTO.getUserId().equals(viewDTO.getUserId()) && memberDTO.getRole().equals("user")){
            return "redirect:/";
        }
        bbsServiceIf.deleteFileAll(bbsDTO.getBbsIdx());
        bbsServiceIf.deleteThumbnail(bbsDTO.getBbsIdx());
        bbsServiceIf.deleteReplyAll(bbsDTO.getBbsIdx());
        bbsServiceIf.delete(bbsDTO);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            return "redirect:/board/list";
        }
        else if(originalURL.contains("notice")){
            return "redirect:/notice/list";
        }
        else{
            return "redirect:/goods/list";
        }
    }

    @GetMapping("/downloadfile")
    public void downloadFile(BbsFileDTO bbsFileDTO, HttpServletResponse response, HttpServletRequest request){
        String saveDirectory = "D:\\java4\\teenflea\\src\\main\\resources\\static\\upload";
        commonFileUtil.fileDownload(saveDirectory,bbsFileDTO.getFileName(),response,request);
    }
    @ResponseBody
    @GetMapping("/deletefile")
    public HttpStatus deleteFile(BbsFileDTO bbsFileDTO){
        bbsServiceIf.deleteFile(bbsFileDTO);

        return HttpStatus.OK;
    }
    @Transactional
    @PostMapping("/registreply")
    public String registReplyPost(BbsReplyDTO bbsReplyDTO,HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        bbsReplyDTO.setUserId(memberDTO.getUserId());

        bbsServiceIf.registReply(bbsReplyDTO);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            return "redirect:/board/view?bbsIdx="+bbsReplyDTO.getBbsIdx();
        }
        else {
            return "redirect:/notice/view?bbsIdx="+bbsReplyDTO.getBbsIdx();
        }
    }
    @Transactional
    @PostMapping("/modifyreply")
    public String modifyReplyPost(BbsReplyDTO bbsReplyDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!memberDTO.getUserId().equals(bbsReplyDTO.getUserId()) && memberDTO.getRole().equals("user")){
            return "redirect:/";
        }

        bbsServiceIf.modifyReply(bbsReplyDTO);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            return "redirect:/board/view?bbsIdx="+bbsReplyDTO.getBbsIdx();
        }
        else {
            return "redirect:/notice/view?bbsIdx="+bbsReplyDTO.getBbsIdx();
        }
    }

    @GetMapping("/deletereply")
    public String deleteReply(BbsReplyDTO bbsReplyDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!memberDTO.getUserId().equals(bbsReplyDTO.getUserId()) && memberDTO.getRole().equals("user")){
            return "redirect:/";
        }
        bbsServiceIf.deleteReply(bbsReplyDTO);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originalURL = urlPathHelper.getOriginatingRequestUri(request);
        if(originalURL.contains("board")) {
            return "redirect:/board/view?bbsIdx="+bbsReplyDTO.getBbsIdx();
        }
        else {
            return "redirect:/notice/view?bbsIdx="+bbsReplyDTO.getBbsIdx();
        }
    }



}
