package org.fullstack4.teenflea.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BbsReplyDTO {
    private int replyIdx;
    @NotNull
    private int bbsIdx;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
