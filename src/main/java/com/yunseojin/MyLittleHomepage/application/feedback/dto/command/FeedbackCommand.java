package com.yunseojin.MyLittleHomepage.application.feedback.dto.command;

import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.response.FeedbackResponse;
import com.yunseojin.MyLittleHomepage.domain.feedback.vo.FeedbackType;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.MemberContainer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class FeedbackCommand extends MemberContainer implements Command<FeedbackResponse> {

    public abstract FeedbackType getFeedbackType();

    public Long getWriterId() {

        return getMemberId();
    }
}
