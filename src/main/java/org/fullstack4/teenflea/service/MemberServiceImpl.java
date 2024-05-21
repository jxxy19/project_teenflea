package org.fullstack4.teenflea.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.teenflea.domain.MemberEntity;
import org.fullstack4.teenflea.dto.MemberDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberServiceIf{
    private final ModelMapper modelMapper;
    /*private final MemberRepository memberRepository;*/
    @Override
    public int regist(MemberDTO memberDTO) {
       /* MemberEntity board = modelMapper.map(boardDTO,BoardEntity.class);
        int idx = boardRepository.save(board).getIdx();*/

        return 1;
    }

    @Override
    public MemberDTO view(String user_id) {
        return null;
    }

    @Override
    public void modify(MemberDTO memberDTO) {

    }

    @Override
    public void delete(int idx) {

    }
}
