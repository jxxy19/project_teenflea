package org.fullstack4.teenflea.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="flea_bbs_file")
public class BbsFileEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int bbsFileIdx;
    private int bbsIdx;
    @Column(length = 20, nullable = false)
    private String category;
    @Column(length = 100, nullable = false)
    private String directory;
    @Column(length = 100, nullable = false)
    private String fileName;
    @Column(length = 20, nullable = false)
    private String userId;
}
