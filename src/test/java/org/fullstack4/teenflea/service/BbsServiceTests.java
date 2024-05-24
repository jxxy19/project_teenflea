package org.fullstack4.teenflea.service;

import org.fullstack4.teenflea.dto.BbsDTO;
import org.fullstack4.teenflea.dto.BbsReplyDTO;
import org.fullstack4.teenflea.dto.PageRequestDTO;
import org.fullstack4.teenflea.dto.PageResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BbsServiceTests {
    @Autowired
    private BbsServiceIf bbsServiceIf;

    @Test
    public void testRegist(){
        IntStream.rangeClosed(0,10).forEach(i-> {
            BbsDTO bbsDTO = BbsDTO.builder().category1("공지사항").title("공지제목"+i).content("공지내용"+i).userId("test").build();
            bbsServiceIf.regist(bbsDTO);
        });
    }

    @Test
    public void testModify(){
        BbsDTO bbsDTO = BbsDTO.builder().bbsIdx(1).category2("카테고리2").title("제목수정").content("내용수정").userId("바뀌면안됨").build();
        bbsServiceIf.modify(bbsDTO);
    }

    @Test
    public void testDelete(){
        BbsDTO bbsDTO = BbsDTO.builder().bbsIdx(1).build();
        bbsServiceIf.delete(bbsDTO);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setCategory1("자유게시판");
        pageRequestDTO.setPage(2);
        PageResponseDTO<BbsDTO> bbsDTOList = bbsServiceIf.list(pageRequestDTO);
    }

    @Test
    public void testView(){
        BbsDTO bbsDTO = BbsDTO.builder().bbsIdx(2).build();
    }
    @Test
    public void testRegistReply(){
        IntStream.rangeClosed(0,10).forEach(i-> {
            BbsReplyDTO bbsDTO = BbsReplyDTO.builder().bbsIdx(2).content("댓글 내용"+i).userId("test").build();
            bbsServiceIf.registReply(bbsDTO);
        });
    }
    @Test
    public void testDeleteReply(){
        BbsReplyDTO bbsReplyDTO = BbsReplyDTO.builder().replyIdx(4).build();
        bbsServiceIf.deleteReply(bbsReplyDTO);
    }
    @Test
    public void testListReply(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setCategory1("자유게시판");
        pageRequestDTO.setPage(2);
        List<BbsReplyDTO> bbsDTOList = bbsServiceIf.listReply(pageRequestDTO,2);
    }
    @Test
    public void testViewReply(){
        BbsDTO bbsDTO = BbsDTO.builder().bbsIdx(2).build();
    }
}
