package com.vhkhai.events;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class InterviewScheduledEventListener {
    private final CandidateRepository candidateRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(InterviewScheduledEvent event) {
        Candidate candidate = event.getCandidate();
        candidate.receiveNotification("Interview Scheduled", "You have an interview scheduled at " + event.getInterview().getStartTime());
        candidateRepository.update(candidate);
    }

}
