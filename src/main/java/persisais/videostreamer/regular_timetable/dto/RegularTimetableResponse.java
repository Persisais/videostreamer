package persisais.videostreamer.regular_timetable.dto;

import java.time.LocalTime;

public record RegularTimetableResponse(
        Long id,
        Long cameraId,
        Integer dayOfWeek,
        LocalTime timeStart,
        LocalTime timeEnd,
        Boolean isUsed) {}
