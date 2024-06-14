package persisais.videostreamer.video.dto;

import java.time.Instant;

public record VideoResponse(Long id,
                            Long cameraId,
                            Instant dateStart,
                            Instant dateEnd,
                            String path) {}
