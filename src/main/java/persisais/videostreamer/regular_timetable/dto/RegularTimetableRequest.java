package persisais.videostreamer.regular_timetable.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import persisais.videostreamer.camera.model.Camera;

import java.time.LocalTime;

public record RegularTimetableRequest(
        @NotBlank Long cameraId,
        @NotBlank Integer dayOfWeek,
        @NotBlank LocalTime timeStart,
        @NotBlank LocalTime timeEnd,
        @NotBlank Boolean isUsed
) {}
