package persisais.videostreamer.stream.dto;

import jakarta.validation.constraints.NotBlank;

public record StreamRequestPatch(Long cameraId,
                                 String outputUrl,
                                 Integer time,
                                 Boolean isUsed) {}
