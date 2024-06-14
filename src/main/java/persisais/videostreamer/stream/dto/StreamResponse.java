package persisais.videostreamer.stream.dto;

import jakarta.validation.constraints.NotBlank;

public record StreamResponse(Long id,
                             Long cameraId,
                             String outputUrl,
                             Integer time,
                             Boolean isUsed) {}
