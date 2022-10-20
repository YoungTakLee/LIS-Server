package com.green.entity;

import com.green.enums.UsedStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "tbl_ges_poll_master")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PollMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idx")
    private int idx;
    @Column(name = "master_id")
    private String masterId;
    private String title;
    private String description;
    @Column(name = "tbl_ges_poll_master.useYN")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UsedStatus useYn = UsedStatus.Y;
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
}
