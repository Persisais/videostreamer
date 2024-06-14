package persisais.videostreamer.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persisais.videostreamer.user.dto.UserRequest;
import persisais.videostreamer.user.dto.UserResponse;
import persisais.videostreamer.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //@Mapping(target = "userRole", expression = "java(user.getUserRole().name())")
    UserResponse toUserResponse(User user);

    User toUser(UserRequest userRequest);
    List<UserResponse> toUserResponseList(List<User> users);

}
