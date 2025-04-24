package com.vhkhai.dto.notification;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDto {
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
