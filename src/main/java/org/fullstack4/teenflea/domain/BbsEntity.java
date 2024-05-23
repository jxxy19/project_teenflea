package org.fullstack4.teenflea.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="flea_bbs")
public class BbsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int bbsIdx;
    @Column(length=20,nullable=false)
    private String userId;
    @Column(length=100,nullable=false)
    private String title;
    @Column(nullable=false, columnDefinition="LONGTEXT")
    private String content;
    @Column(length=10,nullable = true)
    private int readCnt;
    @Column(length =10 ,nullable=false)
    private String category1;
    @Column(length =30 ,nullable=true)
    private String category2;
    @Column(length = 15,nullable=true)
    @ColumnDefault("0")
    private int price;
    @Column(length = 100)
    private String thumbnailDirectory;
    @Column(length = 100)
    private String thumbnailFileName;
    @Column(length=20)
    private String phoneNumber;
    @Column(length=30)
    private String email;
    @Column(length =50)
    private String addr1;
    @Column(length =30)
    private String addr2;
    @Column(length =10)
    private String zipCode;

    public void modify(String title,String content,String category2){
        this.title=title;
        this.content=content;
        this.category2=category2;
        super.setModify_date(LocalDateTime.now());
    }

    public void modifyGoods(String title,String content,String category2){
        this.title=title;
        this.content=content;
        this.category2=category2;
        super.setModify_date(LocalDateTime.now());
    }

}
