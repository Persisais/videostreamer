package persisais.videostreamer.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import persisais.videostreamer.camera.dto.CameraResponse;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.camera.repository.CameraRepository;
import persisais.videostreamer.camera.serivce.CameraService;
import persisais.videostreamer.stream.dto.StreamResponse;
import persisais.videostreamer.stream.service.StreamService;
import persisais.videostreamer.user.repository.UserRepository;

import java.util.List;

@Component
public class RecordingInitializer {
    @Autowired
    private CameraService cameraService;
    @Autowired
    private StreamService streamService;
    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private FFMpegService ffmpegService;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {

        List<CameraResponse> cameras = cameraService.getUsedCameras();
        cameras.forEach(camera -> {
            ffmpegService.startRecording(camera.url(), camera.name());
        });
        List<StreamResponse> streams = streamService.getUsedStreams();
        streams.forEach(stream -> {
            Camera camera = cameraRepository.findById(stream.cameraId()).get();
            ffmpegService.startFFmpegStream(camera.getUrl(), stream.outputUrl());
        });
    }
}
