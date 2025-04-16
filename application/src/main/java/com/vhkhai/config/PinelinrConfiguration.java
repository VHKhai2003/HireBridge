package com.vhkhai.config;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PinelinrConfiguration {
    @Bean
    Pipeline pipelinr(ApplicationContext context) {
        return new Pipelinr()
                .with(() -> context.getBeansOfType(Command.Handler.class).values().stream())
                .with(() -> context.getBeansOfType(Command.Middleware.class).values().stream());
    }

}
