package persisais.videostreamer.camera.dto;

import jakarta.validation.constraints.NotBlank;

public record CameraRequestPatch(String name,
                                 String url,
                                 Boolean isUsed,
                                 Boolean isUsingTimetable) {}
