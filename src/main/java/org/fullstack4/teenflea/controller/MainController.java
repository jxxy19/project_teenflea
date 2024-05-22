package org.fullstack4.teenflea.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.dto.BbsDTO;
import org.fullstack4.teenflea.dto.PageRequestDTO;
import org.fullstack4.teenflea.dto.PageResponseDTO;
import org.fullstack4.teenflea.service.BbsServiceIf;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UrlPathHelper;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final BbsServiceIf bbsServiceIf;
    @GetMapping("/main")
    public void mainGET(PageRequestDTO pageRequestDTO, Model model){
        pageRequestDTO.setCategory1("중고플리");
        pageRequestDTO.setPage_size(6);
        PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
        List<Integer> categoryTwoList = new ArrayList<>();
        int categoryTwoTotalCount = 0;
        String category2List[] = {"디지털기기","가구/인테리어","의류","잡화","생활가전","스포츠/레저","취미","뷰티","도서","기타"};
        for(String category2 : category2List){
            categoryTwoList.add(bbsServiceIf.countCategoryTwo(category2));
            categoryTwoTotalCount += bbsServiceIf.countCategoryTwo(category2);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        model.addAttribute("categoryTwoTotalCount" , categoryTwoTotalCount);
        model.addAttribute("pageResponseDTO" , pageResponseDTO);
        model.addAttribute("categoryTwoCount" , categoryTwoList);
        model.addAttribute("menu", "중고플리");
        model.addAttribute("category1", "goods");
        model.addAttribute("menu", "메인");
    }
}
