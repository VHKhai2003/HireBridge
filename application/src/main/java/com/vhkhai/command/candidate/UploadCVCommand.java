package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class UploadCVCommand implements Command<CandidateResponseDto> {
    private final UUID candidateId;
    private final MultipartFile file;
}
