package org.fullstack4.teenflea.service;

import org.fullstack4.teenflea.dto.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BbsServiceIf {
    int regist(BbsDTO bbsDTO);
    BbsDTO registThumbnail(BbsDTO bbsDTO, MultipartHttpServletRequest files);
    BbsDTO view(BbsDTO bbsDTO);
    void modify(BbsDTO bbsDTO);
    void delete(BbsDTO bbsDTO);
    PageResponseDTO<BbsDTO> list(PageRequestDTO pageRequestDTO);

    void registFile(BbsFileDTO bbsFileDTO, MultipartHttpServletRequest files);
    void deleteFile(BbsFileDTO bbsFileDTO);
    void deleteFileAll(int bbsIdx);
    List<BbsFileDTO> listFile(PageRequestDTO pageRequestDTO, int bbsIdx);

    void registReply(BbsReplyDTO bbsReplyDTO);
    void modifyReply(BbsReplyDTO bbsReplyDTO);
    void deleteReply(BbsReplyDTO bbsReplyDTO);
    void deleteReplyAll(int bbsIdx);
    List<BbsReplyDTO> listReply(PageRequestDTO pageRequestDTO,int bbsIdx);

    int countCategoryTwo(String category2);
}
