package com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.vo.FeedbackType;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.MemberContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class FeedbackCommand extends MemberContainer implements Command<FeedbackType> {
    
    private FeedbackType feedbackType;

    public Long getWriterId() {

        return getMemberId();
    }
}
