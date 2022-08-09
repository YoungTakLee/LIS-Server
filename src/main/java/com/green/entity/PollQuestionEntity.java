package com.green.entity;

import com.green.enums.PollType;
import com.green.enums.UsedStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "tbl_ges_poll_question")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PollQuestionEntity {
    @Id
    @Column(name = "question_id")
    private String questionId;
    @Column(name = "question_title")
    private String questionTitle;
    @Column(name = "useYN")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PollType type = null;
    @Column(name = "tbl_ges_poll_question.useYN")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UsedStatus useYn = UsedStatus.Y;
    private int seq;
    @ManyToOne()
    @JoinColumn(name = "master_id")
    private PollMasterEntity pollMasterEntity;
}
