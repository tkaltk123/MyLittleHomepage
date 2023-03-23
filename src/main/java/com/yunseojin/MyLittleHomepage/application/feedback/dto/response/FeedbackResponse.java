package com.yunseojin.MyLittleHomepage.application.feedback.dto.response;

import com.yunseojin.MyLittleHomepage.domain.feedback.vo.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private FeedbackType feedbackType;
}
