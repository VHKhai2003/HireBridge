package com.vhkhai.port.schedule;

import java.util.Date;
import java.util.UUID;

public interface Scheduler {
    void schedule(ScheduledTask scheduledTask, Date startTime);
    void cancel(UUID scheduledTaskId);
}
