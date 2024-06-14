package persisais.videostreamer.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserResponse(
        Long id,
        String name,
        String email,

        String userRole
) {}
