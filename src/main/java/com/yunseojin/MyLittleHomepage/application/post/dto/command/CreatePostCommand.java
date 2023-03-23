package com.yunseojin.MyLittleHomepage.application.post.dto.command;

import com.yunseojin.MyLittleHomepage.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.application.post.dto.PostCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatePostCommand extends PostCommand<PostResponse> {

}
