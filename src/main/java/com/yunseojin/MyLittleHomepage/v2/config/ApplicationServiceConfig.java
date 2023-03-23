package com.yunseojin.MyLittleHomepage.v2.config;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Command.Handler;
import an.awesome.pipelinr.CommandHandlers;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ApplicationServiceConfig {

    @Bean
    public Pipeline commandPipeline(ObjectProvider<Command.Handler> handlers) {
        return new Pipelinr().with(getCommandServices(handlers));
    }

    private CommandHandlers getCommandServices(ObjectProvider<Handler> handlers) {
        return () -> handlers.stream()
                .filter(CommandHandler.class::isInstance);
    }

    @Bean
    public Pipeline queryPipeline(ObjectProvider<Command.Handler> handlers) {
        return new Pipelinr().with(getQueryServices(handlers));
    }

    private CommandHandlers getQueryServices(ObjectProvider<Handler> handlers) {
        return () -> handlers.stream()
                .filter(QueryHandler.class::isInstance);
    }
}
