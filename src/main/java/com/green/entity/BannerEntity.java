package com.green.entity;


import com.green.enums.UsedStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "banner")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_idx")
    private long id;
    private String title;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UsedStatus useYn = UsedStatus.Y;
    private LocalDate bannerStartDate;
    private LocalDate bannerEndDate;
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.now();

    @OneToMany(mappedBy = "bannerEntity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Builder.Default
    private List<BannerFileEntity> bannerFileEntity = new ArrayList<>();


    @Override
    public String toString() {
        return "BannerEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", useYn=" + useYn +
                ", bannerStartDate=" + bannerStartDate +
                ", bannerEndDate=" + bannerEndDate +
                ", regDate=" + regDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
