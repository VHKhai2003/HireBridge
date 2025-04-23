package com.vhkhai.scheduler;

import com.vhkhai.exception.ErrorCode;
import com.vhkhai.exception.InfrastructureException;
import com.vhkhai.port.schedule.ScheduledTask;
import com.vhkhai.port.schedule.Scheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class SchedulerService implements Scheduler {
    private final ThreadPoolTaskScheduler scheduler;
    private Map<UUID, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();


    @Override
    public void schedule(ScheduledTask scheduledTask, Date startTime) {
        if(startTime.before(new Date())){ // startTime < now
            throw new InfrastructureException(ErrorCode.INVALID_START_TIME_FOR_SCHEDULED_TASK);
        }
        if(scheduledTasks.containsKey(scheduledTask.getId())){ // already scheduled
            throw new InfrastructureException(ErrorCode.SCHEDULED_TASK_ALREADY_EXISTS);
        }

        ScheduledFuture<?> future = scheduler.schedule(scheduledTask.getTask(), startTime);
        scheduledTasks.put(scheduledTask.getId(), future);
    }

    @Override
    public void cancel(UUID scheduledTaskId) {
        ScheduledFuture<?> future = scheduledTasks.get(scheduledTaskId);
        if(future == null){
            throw new InfrastructureException(ErrorCode.SCHEDULED_TASK_NOT_FOUND);
        }
        future.cancel(true);
        scheduledTasks.remove(scheduledTaskId);
    }


    @Scheduled(cron = "0 0 */6 * * *")
    public void cleanUp() {
        scheduledTasks.entrySet().removeIf(entry -> entry.getValue().isDone());
    }

}
