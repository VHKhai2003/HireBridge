package com.vhkhai.events;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.port.schedule.ScheduledTask;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.utils.InterviewUpcomingNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ScheduleEventListener {
    private final CandidateRepository candidateRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(InterviewScheduledEvent event) {
// schedule the interview
//        Date startTime = Date.from(Instant.from(event.getStartTime()).plusSeconds(3600));
//        ScheduledTask task = ScheduledTask.builder()
//                .id(event.getId())
//                .task(new InterviewUpcomingNotification(interview, jobApplication.getCandidate(), candidateRepository))
////                .task(() -> {
////                    // fire new interview event
////
////                    // Create new class implement Runnable and override run method to do this task
////
////                    // candidate.receiveNotification("Interview Upcomming", "");
////                    // candidateRepository.update(candidate);
////                })
//                .build();
//        scheduler.schedule(ScheduledTask.builder().build(), startTime);
    }

}
