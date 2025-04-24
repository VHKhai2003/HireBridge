package com.vhkhai.events;

import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class JobOfferEventListener {

    private final CandidateRepository candidateRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(JobOfferEvent event) {
        var candidate = event.getJobApplication().getCandidate();
        candidate.receiveNotification("You have an offer",
                String.format("You have an offer from %s", event.getJobApplication().getJobPosting().getCompany().getName()));
        candidateRepository.update(candidate);
    }
}
