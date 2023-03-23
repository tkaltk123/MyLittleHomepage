package com.yunseojin.MyLittleHomepage.application.feedback.dto.command.post;

import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.FeedbackCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PostFeedbackCommand extends FeedbackCommand {

    private Long postId;
}
