package com.green.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "center")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicUpdate
public class CenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "center_seq", updatable = false)
    private int centerSeq;
    @Column(name = "center_title",nullable = false)
    private String title;
    @Column(name = "center_password",nullable = false)
    private String password;
    @Column(updatable = false)
    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @JsonManagedReference
    @OneToMany(mappedBy = "centerEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LectureDetailEntity> detailEntityList;

    @Override
    public String toString() {
        return "CenterEntity{" +
                "centerSeq=" + centerSeq +
                ", title='" + title + '\'' +
                ", password='" + password + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
