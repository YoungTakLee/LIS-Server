package com.green.entity;


import com.green.enums.BannerType;
import com.green.enums.UsedStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicUpdate
public class BannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "banner_idx")
    private long id;
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "banner_type")
    private BannerType bannerType;
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

    @OneToMany(mappedBy = "bannerEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
