package com.yunseojin.MyLittleHomepage.v2.post.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatePostCommand extends PostCommand<PostResponse> {

}
