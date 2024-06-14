package persisais.videostreamer.video.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public record VideoRequest(@NotBlank Long cameraId,
                           @NotBlank Instant dateStart,
                           @NotBlank Instant dateEnd,
                           @NotBlank String path) {

}
