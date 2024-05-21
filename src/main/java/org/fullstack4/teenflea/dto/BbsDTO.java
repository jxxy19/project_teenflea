package org.fullstack4.teenflea.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BbsDTO {
    private int bbsIdx;

    @NotEmpty
    private String userId;
    @NotEmpty
    @Size(min=1,max=100)
    private String title;
    @NotEmpty
    @Size(min=1,max=300)
    private String content;
    private String displayDate;
    @NotEmpty
    private String category1;

    private String category2;
    @NotEmpty
    private String img;
    
    private int read_cnt; // 조회 수

    private int reply_cnt; //댓글 수

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
