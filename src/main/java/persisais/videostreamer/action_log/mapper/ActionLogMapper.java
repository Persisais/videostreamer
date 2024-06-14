package persisais.videostreamer.action_log.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persisais.videostreamer.action_log.dto.ActionLogRequest;
import persisais.videostreamer.action_log.dto.ActionLogResponse;
import persisais.videostreamer.action_log.model.ActionLog;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionLogMapper {
    @Mapping(source = "camera.id", target = "cameraId")
    @Mapping(source = "user.id", target = "userId")
    ActionLogResponse toActionLogResponse(ActionLog actionLog);
    ActionLog toActionLog(ActionLogRequest actionLogRequest);
    List<ActionLogResponse> toActionLogResponseList(List<ActionLog> actionLogList);
}
