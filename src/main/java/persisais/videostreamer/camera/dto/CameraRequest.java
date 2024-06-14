package persisais.videostreamer.camera.dto;

import jakarta.validation.constraints.NotBlank;

public record CameraRequest(@NotBlank String name,
                            @NotBlank String url,
                            @NotBlank Boolean isUsed,
                            @NotBlank Boolean isUsingTimetable) {}
