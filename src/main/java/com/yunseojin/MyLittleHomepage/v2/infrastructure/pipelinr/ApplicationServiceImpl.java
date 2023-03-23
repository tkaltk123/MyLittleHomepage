package com.yunseojin.MyLittleHomepage.v2.infrastructure.pipelinr;

import an.awesome.pipelinr.Pipeline;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final Pipeline commandPipeline;

    private final Pipeline queryPipeline;

    @Override
    public <R> R executeCommand(Command<R> command) {
        return command.execute(commandPipeline);
    }

    @Override
    public <R> R executeQuery(Query<R> query) {
        return query.execute(queryPipeline);
    }
}
