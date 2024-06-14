package persisais.videostreamer.timetable.dto;

import org.springframework.format.annotation.DateTimeFormat;
import persisais.videostreamer.camera.dto.CameraResponse;

import java.time.Instant;
import java.time.LocalDateTime;

public record TimetableResponse(
        Long id,
        Long cameraId,
        LocalDateTime dateStart,
        LocalDateTime dateEnd,
        Boolean isUsed) {}
