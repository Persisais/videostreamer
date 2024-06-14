package persisais.videostreamer.camera.mapper;

import persisais.videostreamer.camera.dto.CameraRequest;
import persisais.videostreamer.camera.dto.CameraResponse;
import org.mapstruct.Mapper;
import persisais.videostreamer.camera.model.Camera;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CameraMapper {
    CameraResponse toCameraResponse(Camera camera);
    Camera toCamera(CameraRequest cameraRequest);
    List<CameraResponse> toCameraResponseList(List<Camera> cameraList);
}
