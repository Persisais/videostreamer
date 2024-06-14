package persisais.videostreamer.camera.dto;

public record CameraResponse(Long id,
                             String name,
                             String url,
                             Boolean isUsed,
                             Boolean isUsingTimetable) {}
