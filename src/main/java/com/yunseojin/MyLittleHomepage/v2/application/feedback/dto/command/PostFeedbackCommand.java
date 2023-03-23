package com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostFeedbackCommand extends FeedbackCommand {

    private Long postId;
}
