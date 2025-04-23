package com.vhkhai.utils;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.job_application.Interview;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class InterviewUpcomingNotification implements Runnable {

    private final Interview interview;
    private final Candidate candidate;
    private final CandidateRepository candidateRepository;

    @Override
    public void run() {
        candidate.receiveNotification("Interview Upcoming",
                "You have an interview scheduled at " + interview.getStartTime());
        candidateRepository.update(candidate);
    }

}
