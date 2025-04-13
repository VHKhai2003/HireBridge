package com.vhkhai.events;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DomainEvent {
    private String message;
}
