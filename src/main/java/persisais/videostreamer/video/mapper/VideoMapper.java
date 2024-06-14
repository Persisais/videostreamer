package persisais.videostreamer.video.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persisais.videostreamer.camera.dto.CameraRequest;
import persisais.videostreamer.camera.dto.CameraResponse;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.video.dto.VideoRequest;
import persisais.videostreamer.video.dto.VideoResponse;
import persisais.videostreamer.video.model.Video;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    @Mapping(source = "camera.id", target = "cameraId")
    VideoResponse toVideoResponse(Video video);

    Video toVideo(VideoRequest videoRequest);
    List<VideoResponse> toVideoResponseList(List<Video> videoList);

}
