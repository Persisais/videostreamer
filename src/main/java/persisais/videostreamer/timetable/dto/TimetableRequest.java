package persisais.videostreamer.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;

public record TimetableRequest(
        @NotBlank Long cameraId,
        @NotBlank LocalDateTime dateStart,
        @NotBlank LocalDateTime dateEnd,
        @NotBlank Boolean isUsed) {}
