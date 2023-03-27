package com.yunseojin.MyLittleHomepage.application.board.dto.command;

import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCountIncreaseCommand implements Command<Void> {

    private Long boardId;
}
