package com.vhkhai.query.iquery;

import an.awesome.pipelinr.Command;

public interface Query<R> extends Command<R> {
    interface Hanldler<T extends Query<R>, R> extends Command.Handler<T, R> {
    }
}
