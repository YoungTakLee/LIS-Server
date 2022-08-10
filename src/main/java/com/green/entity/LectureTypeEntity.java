package com.green.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "lecture_type")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LectureTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_type_seq", updatable = false)
    private int lectureTypeSeq;
    @Column(name = "lecture_type_title")
    private String title;
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @JsonManagedReference
    @OneToMany(mappedBy = "lectureType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LectureDetailEntity> detailEntityList;

    @Override
    public String toString() {
        return "LectureTypeEntity{" +
                "lectureTypeSeq=" + lectureTypeSeq +
                ", title='" + title + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
