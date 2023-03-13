package com.yunseojin.MyLittleHomepage.v2.contract.handler;

import an.awesome.pipelinr.Pipeline;
import com.yunseojin.MyLittleHomepage.v2.contract.application.command.Command;
import com.yunseojin.MyLittleHomepage.v2.contract.application.query.Query;
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
