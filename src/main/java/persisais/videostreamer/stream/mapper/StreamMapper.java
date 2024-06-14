package persisais.videostreamer.stream.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persisais.videostreamer.stream.dto.StreamRequest;
import persisais.videostreamer.stream.dto.StreamResponse;
import persisais.videostreamer.stream.model.Stream;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StreamMapper {
    @Mapping(source = "camera.id", target = "cameraId")
    StreamResponse toStreamResponse(Stream stream);
    Stream toStream(StreamRequest streamRequest);
    List<StreamResponse> toStreamResponseList(List<Stream> streamList);
}
