package persisais.videostreamer.user.dto;

import jakarta.validation.constraints.NotBlank;
import persisais.videostreamer.user.enums.UserRoleEnum;

public record UserRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String userRole
) {}
