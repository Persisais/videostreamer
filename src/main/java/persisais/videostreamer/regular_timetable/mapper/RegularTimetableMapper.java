package persisais.videostreamer.regular_timetable.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableRequest;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableResponse;
import persisais.videostreamer.regular_timetable.model.RegularTimetable;
import persisais.videostreamer.timetable.dto.TimetableRequest;
import persisais.videostreamer.timetable.dto.TimetableResponse;
import persisais.videostreamer.timetable.model.Timetable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegularTimetableMapper {
    @Mapping(source = "camera.id", target = "cameraId")
    List<RegularTimetableResponse> toRegularTimetableResponseList(List<RegularTimetable> regularTimetableList);

    RegularTimetableResponse toRegularTimetableResponse(RegularTimetable regularTimetable);
    RegularTimetable toRegularTimetable(RegularTimetableRequest regularTimetableRequest);
}
