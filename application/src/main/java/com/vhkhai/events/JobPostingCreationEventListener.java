package com.vhkhai.events;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.candidate.Following;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingCreationEventListener {

    private final CandidateRepository candidateRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(JobPostingCreationEvent event) {
        Company company = event.getJobPosting().getCompany();
        List<Candidate> candidates = candidateRepository.findAllByFollowedCompanyId(company.getId())
                .stream()
                .map(Following::getCandidate)
                .toList();

        for (Candidate candidate : candidates) {
            candidate.receiveNotification(company.getName() + " has posted a new job posting", event.getJobPosting().getTitle());
            candidateRepository.update(candidate);
        }

    }
}
