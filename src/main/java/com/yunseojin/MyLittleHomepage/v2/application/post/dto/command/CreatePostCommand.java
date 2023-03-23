package com.yunseojin.MyLittleHomepage.v2.application.post.dto.command;

import com.yunseojin.MyLittleHomepage.v2.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.PostCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatePostCommand extends PostCommand<PostResponse> {

}
