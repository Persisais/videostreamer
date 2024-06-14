package persisais.videostreamer.action_log.dto;

import java.time.Instant;

public record ActionLogResponse(
         Long id,
         Long userId,
         String actionType,
         Long cameraId,
         Instant time,
         String commentary) {

}
