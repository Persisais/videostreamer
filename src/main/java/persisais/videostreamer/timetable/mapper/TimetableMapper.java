package persisais.videostreamer.timetable.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persisais.videostreamer.timetable.dto.TimetableRequest;
import persisais.videostreamer.timetable.dto.TimetableResponse;
import persisais.videostreamer.timetable.model.Timetable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimetableMapper {
    @Mapping(source = "camera.id", target = "cameraId")
    List<TimetableResponse> toTimetableResponseList(List<Timetable> timetableList);

    TimetableResponse toTimetableResponse(Timetable timetable);
    Timetable toTimetable(TimetableRequest timetableRequest);
}
