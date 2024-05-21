package org.fullstack4.teenflea.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class MemberDTO {
    @NotNull
    private int memberIdx;
    @NotEmpty
    private String userId;
    @NotEmpty
    private String pwd;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String birthday;
    @NotEmpty
    private String addr1;
    @NotEmpty
    private String addr2;
    @NotEmpty
    private String zipCode;

    @NotEmpty
    private String userState;

    private LocalDateTime regDate;

    private LocalDateTime leaveDate;

    private LocalDateTime modifyDate;
}
