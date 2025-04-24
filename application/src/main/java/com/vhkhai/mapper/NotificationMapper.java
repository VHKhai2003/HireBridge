package com.vhkhai.mapper;

import com.vhkhai.aggrerates.candidate.Notification;
import com.vhkhai.dto.notification.NotificationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponseDto toDto(Notification notification);
}
