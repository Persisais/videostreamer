package persisais.videostreamer.action_log.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record ActionLogRequest(
        @NotBlank Long userId,
        @NotBlank String actionType,
        @NotBlank Long cameraId,
        @NotBlank Instant time,
        @NotBlank String commentary) {}
