package persisais.videostreamer.regular_timetable.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;

public record RegularTimetableRequestPatch(
        Long cameraId,
        Integer dayOfWeek,
        LocalTime timeStart,
        LocalTime timeEnd,
        Boolean isUsed
) {}
