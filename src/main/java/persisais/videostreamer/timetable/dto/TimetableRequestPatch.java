package persisais.videostreamer.timetable.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;

public record TimetableRequestPatch(
        Long cameraId,
         LocalDateTime dateStart,
        LocalDateTime dateEnd,
        Boolean isUsed
) {}
