package com.vhkhai.events;

import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class JobRejectEventListener {
    private final CandidateRepository candidateRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(JobRejectEvent event) {
        var candidate = event.getJobApplication().getCandidate();
        candidate.receiveNotification("Your application was rejected", event.getJobApplication().getRejectReason());
        candidateRepository.update(candidate);
    }
}
