package com.vhkhai.query.candidate;

import com.vhkhai.dto.notification.NotificationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.NotificationMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public record GetNotificationsQuery(UUID accountId) implements Query<List<NotificationResponseDto>> {
}

@Service
@RequiredArgsConstructor
class GetNotificationsQueryHandler implements Query.Handler<GetNotificationsQuery, List<NotificationResponseDto>> {

    private final NotificationMapper notificationMapper;
    private final CandidateRepository candidateRepository;

    @Override
    @PreAuthorize("hasRole('CANDIDATE')")
    public List<NotificationResponseDto> handle(GetNotificationsQuery command) {
        var candidate = candidateRepository.findByAccountId(command.accountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        return candidate.getNotifications().stream().map(notificationMapper::toDto).toList();
    }
}
