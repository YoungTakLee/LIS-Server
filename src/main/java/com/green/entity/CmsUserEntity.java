package com.green.entity;

import com.green.enums.UsedStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "cms_user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CmsUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loginId;
    private String password;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UsedStatus useYn = UsedStatus.Y;
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.now();

}
