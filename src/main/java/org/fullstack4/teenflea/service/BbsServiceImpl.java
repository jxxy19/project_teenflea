package org.fullstack4.teenflea.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.domain.BbsEntity;
import org.fullstack4.teenflea.domain.BbsFileEntity;
import org.fullstack4.teenflea.domain.BbsReplyEntity;
import org.fullstack4.teenflea.dto.*;
import org.fullstack4.teenflea.repository.BbsFileRepository;
import org.fullstack4.teenflea.repository.BbsReplyRepository;
import org.fullstack4.teenflea.repository.BbsRepository;
import org.fullstack4.teenflea.util.CommonFileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BbsServiceImpl implements BbsServiceIf{
    private final ModelMapper modelMapper;
    private final BbsRepository bbsRepository;
    private final BbsFileRepository bbsFileRepository;
    private final BbsReplyRepository bbsReplyRepository;
    private final CommonFileUtil commonFileUtil;
    @Override
    public int regist(BbsDTO bbsDTO) {
        BbsEntity bbsEntity = modelMapper.map(bbsDTO, BbsEntity.class);
        return bbsRepository.save(bbsEntity).getBbsIdx();
    }

    @Override
    public BbsDTO view(BbsDTO bbsDTO) {
        log.info("bbsDTO.getBbsIdx() ; " +bbsDTO.getBbsIdx());
        Optional<BbsEntity> result = bbsRepository.findById(bbsDTO.getBbsIdx());
        BbsEntity bbsEntity =result.orElse(null);
        return modelMapper.map(bbsEntity, BbsDTO.class);
    }

    @Override
    public void modify(BbsDTO bbsDTO) {
        Optional<BbsEntity> result = bbsRepository.findById(bbsDTO.getBbsIdx());
        BbsEntity bbsEntity =result.orElse(null);
        if (bbsEntity != null) {
            bbsEntity.modify(bbsDTO.getTitle(),bbsDTO.getContent(),bbsDTO.getCategory2());
            bbsRepository.save(bbsEntity);
        }

    }

    @Override
    public void delete(BbsDTO bbsDTO) {
        bbsRepository.deleteById(bbsDTO.getBbsIdx());
    }

    @Override
    public PageResponseDTO<BbsDTO> list(PageRequestDTO pageRequestDTO) {
        PageRequest pageable = pageRequestDTO.getPageable();
        Page<BbsEntity> result = null;
        String category = pageRequestDTO.getCategory1();
        String category2 = pageRequestDTO.getCategory2();
        String search_word = pageRequestDTO.getSearch_word();
        String location = pageRequestDTO.getLocation();
        if(location != null || category2 != null){
            result = bbsRepository.findAllByCategory2ContainsAndLocationContainsAndTitleContains(pageable,category2,location,search_word);
        }
        else if(pageRequestDTO.getSearch_word()!=null && !pageRequestDTO.getSearch_word().isEmpty()) {
            result = bbsRepository.findAllByCategory1AndTitleContainsOrContentContainsAndCategory1ContainsOrUserIdContainsAndCategory1ContainsOrderByBbsIdxDesc(
                    pageable, category, search_word,search_word,category,search_word,category
            );
        }
        else{
            result = bbsRepository.findAllByCategory1OrderByBbsIdxDesc(pageable, pageRequestDTO.getCategory1());
        }
        List<BbsDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,BbsDTO.class))
                .collect(Collectors.toList());
        return PageResponseDTO.<BbsDTO>withAll().pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList).total_count((int) result.getTotalElements()).build();
    }

    @Transactional
    @Override
    public void registFile(BbsFileDTO bbsFileDTO, MultipartHttpServletRequest files) {
        String saveDirectory = "D:\\java4\\teenflea\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.fileuploads(files,saveDirectory);;
        if(filenames!=null) {
            for (String filename : filenames) {
                bbsFileDTO.setFileName(filename);
                bbsFileDTO.setDirectory(saveDirectory);
                BbsFileEntity bbsFileEntity = modelMapper.map(bbsFileDTO, BbsFileEntity.class);
                bbsFileRepository.save(bbsFileEntity);
            }
        }
    }
    @Transactional
    @Override
    public void deleteFile(BbsFileDTO bbsFileDTO) {
        BbsFileEntity bbsFileEntity = modelMapper.map(bbsFileDTO, BbsFileEntity.class);
        bbsFileRepository.delete(bbsFileEntity);
        commonFileUtil.fileDelite(bbsFileDTO.getDirectory(),bbsFileDTO.getFileName());

    }

    @Transactional
    @Override
    public void deleteFileAll(int bbsIdx) {
        List<BbsFileEntity> bbsFileEntityList = bbsFileRepository.findAllByBbsIdx(bbsIdx);
        for(BbsFileEntity bbsFileEntity : bbsFileEntityList){
            commonFileUtil.fileDelite(bbsFileEntity.getDirectory(),bbsFileEntity.getFileName());
        }
        bbsFileRepository.deleteAllByBbsIdx(bbsIdx);
    }

    @Override
    public List<BbsFileDTO> listFile(PageRequestDTO pageRequestDTO, int bbsIdx) {
        List<BbsFileEntity> result = bbsFileRepository.findAllByBbsIdx(bbsIdx);
        List<BbsFileDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,BbsFileDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public void registReply(BbsReplyDTO bbsReplyDTO) {
        BbsReplyEntity bbsReplyEntity = modelMapper.map(bbsReplyDTO, BbsReplyEntity.class);
        bbsReplyRepository.save(bbsReplyEntity);
    }

    @Override
    public void modifyReply(BbsReplyDTO bbsReplyDTO) {
        log.info("getReplyIdx: " +bbsReplyDTO.getReplyIdx());
        Optional<BbsReplyEntity> result = bbsReplyRepository.findById(bbsReplyDTO.getReplyIdx());
        BbsReplyEntity bbsReplyEntity =result.orElse(null);
        if (bbsReplyEntity != null) {
            bbsReplyEntity.modify(bbsReplyDTO.getContent());
            bbsReplyRepository.save(bbsReplyEntity);
        }

    }
    @Override
    public void deleteReply(BbsReplyDTO bbsReplyDTO) {
        BbsReplyEntity bbsReplyEntity = modelMapper.map(bbsReplyDTO, BbsReplyEntity.class);
        bbsReplyRepository.delete(bbsReplyEntity);
    }
    @Override
    public void deleteReplyAll(int bbsIdx) {
        bbsReplyRepository.deleteAllByBbsIdx(bbsIdx);
    }

    @Override
    public List<BbsReplyDTO> listReply(PageRequestDTO pageRequestDTO,int bbsIdx) {
        List<BbsReplyEntity> result = bbsReplyRepository.findAllByBbsIdxOrderByReplyIdxDesc(bbsIdx);
        List<BbsReplyDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,BbsReplyDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public int countCategoryTwo(String category2) {
        bbsRepository.countByCategory2(category2);
        if(bbsRepository.countByCategory2(category2)!=0) {
            return bbsRepository.countByCategory2(category2);
        }
        else{
            return 0;
        }
    }


}
