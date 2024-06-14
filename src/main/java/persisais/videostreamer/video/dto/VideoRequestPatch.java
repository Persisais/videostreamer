package persisais.videostreamer.video.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record VideoRequestPatch(Long cameraId,
                                Instant dateStart,
                                Instant dateEnd,
                                String path) {}
