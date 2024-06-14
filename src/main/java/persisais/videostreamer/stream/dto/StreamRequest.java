package persisais.videostreamer.stream.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StreamRequest(@NotNull Long cameraId,
                            @NotBlank String outputUrl,
                            @NotBlank Integer time,
                            @NotBlank Boolean isUsed) {}
