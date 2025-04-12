package com.vhkhai.aggrerates.recruitment;

import com.vhkhai.enumerations.ApplicationStatus;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class JobApplication {
    private UUID id;
    private ApplicationStatus status;
    private UUID candidateId;
    private UUID jobPostingId;

    public void accept() {
        if (this.status == ApplicationStatus.REJECTED || this.status == ApplicationStatus.OFFERED) {
            throw new IllegalStateException("Can only accept applications under review");
        }
        this.status = ApplicationStatus.OFFERED;
    }
}
