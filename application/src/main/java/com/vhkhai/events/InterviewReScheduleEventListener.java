package com.vhkhai.events;


import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.scheduler.ScheduledTask;
import com.vhkhai.port.scheduler.Scheduler;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class InterviewReScheduleEventListener {
    private final CandidateRepository candidateRepository;
    private final Scheduler scheduler;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreation(InterviewReScheduleEvent event) {
        var candidate = event.getCandidate();
        candidate.receiveNotification("Interview Re-scheduled",
                "Your interview has been re scheduled at " + event.getInterview().getStartTime());
        candidateRepository.update(candidate);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSchedule(InterviewReScheduleEvent event) {
        var interviewStartTime = event.getInterview().getStartTime();
        var scheduledTime = Date.from(interviewStartTime.atZone(ZoneId.systemDefault()).toInstant().minusSeconds(3600));
        if(scheduledTime.before(new Date())) return;
        var newTask = new ScheduledTask(event.getInterview().getId(), () -> {
            var candidate = candidateRepository.getById(event.getCandidate().getId())
                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
            candidate.receiveNotification("Interview Upcoming", "Your interview is coming up on " + event.getInterview().getStartTime());
            candidateRepository.update(candidate);
        });
        // id of the interview is the key of the scheduled task
        scheduler.cancel(event.getInterview().getId());
        scheduler.schedule(newTask, scheduledTime);
    }

}
