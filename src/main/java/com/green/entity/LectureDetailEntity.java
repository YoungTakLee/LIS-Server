package com.green.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "lecture_detail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicUpdate
public class LectureDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lecture_detail_seq",updatable = false)
    private int lectureDetailSeq;
    @Column(name = "lecture_title")
    private String title;
    private String method;
    private String instructor;
    @Column(name = "recruitment_count")
    private String recruitmentCount;
    @Column(name = "room_no")
    private String roomNo;
    @Column(name = "week_day")
    private String weekDay;
    @Column(name = "lecture_time")
    private String lectureTime;
    private String desc;
    @Column(name = "material_costs")
    private String materialCosts;
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.now();
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_seq")
    private CenterEntity centerEntity;

    @Override
    public String toString() {
        return "LectureDetailEntity{" +
                "lectureDetailSeq=" + lectureDetailSeq +
                ", title='" + title + '\'' +
                ", method='" + method + '\'' +
                ", instructor='" + instructor + '\'' +
                ", recruitmentCount=" + recruitmentCount +
                ", roomNo='" + roomNo + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", lectureTime='" + lectureTime + '\'' +
                ", desc='" + desc + '\'' +
                ", materialCosts='" + materialCosts + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
