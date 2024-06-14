package persisais.videostreamer.stream.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.action_log.model.ActionLog;
import persisais.videostreamer.action_log.repository.ActionLogRepository;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.camera.repository.CameraRepository;
import persisais.videostreamer.stream.dto.StreamRequest;
import persisais.videostreamer.stream.dto.StreamRequestPatch;
import persisais.videostreamer.stream.dto.StreamResponse;
import persisais.videostreamer.stream.mapper.StreamMapper;
import persisais.videostreamer.stream.model.Stream;
import persisais.videostreamer.stream.repository.StreamRepository;
import persisais.videostreamer.user.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StreamService {
    private final StreamRepository streamRepository;
    private final StreamMapper streamMapper;
    private final CameraRepository cameraRepository;
    private final UserRepository userRepository;
    private final ActionLogRepository actionLogRepository;

    public List<StreamResponse> getAllStreams() {
        List<Stream> streams = streamRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return streamMapper.toStreamResponseList(streams);
    }

    public List<StreamResponse> getUsedStreams() {
        List<Stream> streams = streamRepository.findUsedStreams();
        return streamMapper.toStreamResponseList(streams);
    }

    public StreamResponse getStreamById(Long id) {
        Stream stream = streamRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found"));
        //System.out.println(stream.getIsUsed());
        return streamMapper.toStreamResponse(stream);
    }

    public StreamResponse getStreamByUrl(String outputUrl) {
        Stream stream = streamRepository.findByOutputUrl(outputUrl).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found"));
        //System.out.println(stream.getIsUsed());
        return streamMapper.toStreamResponse(stream);
    }


    public StreamResponse addStream(StreamRequest streamRequest) {
        if (streamRepository.findByCamera(streamRequest.cameraId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stream already exists");
        }
        Camera camera = cameraRepository.findById(streamRequest.cameraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found"));
        Stream addedStream = new Stream();
        addedStream.setCamera(camera);
        addedStream.setOutputUrl(streamRequest.outputUrl());
        addedStream.setTime(streamRequest.time());
        addedStream.setIsUsed(streamRequest.isUsed());

        streamRepository.save(addedStream);
        ActionLog actionLog = new ActionLog();
        actionLog.setUser(userRepository.findById(1L).get());
        actionLog.setActionType("Stream added");
        actionLog.setTime(Instant.now());
        actionLog.setCamera(camera);
        actionLog.setCommentary("");
        actionLogRepository.save(actionLog);

        return streamMapper.toStreamResponse(addedStream);
        //Stream addedStream = streamRepository.save(streamMapper.toStream(streamRequest));

        //return streamMapper.toStreamResponse(addedStream);
    }

    public StreamResponse patchById(StreamRequestPatch streamRequestPatch, Long id) {
        Stream stream = streamRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found"));
        if(streamRequestPatch.cameraId() != null && !streamRequestPatch.cameraId().equals(stream.getCamera().getId())) {
            stream.setCamera(cameraRepository.findById(streamRequestPatch.cameraId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found")));
        }
        if(streamRequestPatch.outputUrl() != null && !streamRequestPatch.outputUrl().equals(stream.getOutputUrl())) {
            stream.setOutputUrl(streamRequestPatch.outputUrl());
        }
        if(streamRequestPatch.time() != null && !streamRequestPatch.time().equals(stream.getTime())) {
            stream.setTime(streamRequestPatch.time());
        }
        if(streamRequestPatch.isUsed() != null && !streamRequestPatch.isUsed().equals(stream.getIsUsed())) {
            stream.setIsUsed(streamRequestPatch.isUsed());
        }
        return streamMapper.toStreamResponse(streamRepository.save(stream));
    }
    public void deleteById(Long id) {
        Stream stream = streamRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stream not found"));
        streamRepository.deleteById(id);
    }

}
