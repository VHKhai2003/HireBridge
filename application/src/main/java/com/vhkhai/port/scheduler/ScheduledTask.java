package com.vhkhai.port.scheduler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ScheduledTask {
    private UUID key;
    private Runnable task;
}
