package com.vhkhai.port.scheduler;

import java.util.Date;
import java.util.UUID;

public interface Scheduler {
    void schedule(ScheduledTask scheduledTask, Date startTime);
    void cancel(UUID scheduledTaskId);
}
