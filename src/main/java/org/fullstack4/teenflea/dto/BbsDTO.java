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
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String category1;
    private String category2;
    private int read_cnt;
    private int reply_cnt;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
