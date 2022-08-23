package com.green.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "banner_file")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicUpdate
public class BannerFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_file_idx")
    private Long id;
    private String filePath;
    private String originalFileName;
    private String savedFileName;
    private int fileSeq;
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banner_idx")
    private BannerEntity bannerEntity;

    public void insertBannerEntity(BannerEntity bannerEntity) {
        this.bannerEntity = bannerEntity;
    }

    @Override
    public String toString() {
        return "BannerFileEntity{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", savedFileName='" + savedFileName + '\'' +
                ", fileSeq=" + fileSeq +
                ", regDate=" + regDate +
                '}';
    }
}
