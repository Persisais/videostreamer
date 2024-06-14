package persisais.videostreamer.camera.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.action_log.dto.ActionLogRequest;
import persisais.videostreamer.action_log.model.ActionLog;
import persisais.videostreamer.action_log.repository.ActionLogRepository;
import persisais.videostreamer.camera.dto.CameraRequest;
import persisais.videostreamer.camera.dto.CameraRequestPatch;
import persisais.videostreamer.camera.dto.CameraResponse;
import persisais.videostreamer.camera.mapper.CameraMapper;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.camera.repository.CameraRepository;
import persisais.videostreamer.service.FFMpegService;
import persisais.videostreamer.user.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CameraService {
    private final CameraRepository cameraRepository;
    private final CameraMapper cameraMapper;
    private final UserRepository userRepository;
    private final ActionLogRepository actionLogRepository;
    private final FFMpegService ffMpegService;

    public List<CameraResponse> getAllCameras() {
        List<Camera> cameras = cameraRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return cameraMapper.toCameraResponseList(cameras);
    }

    public List<CameraResponse> getUsedCameras() {
        List<Camera> cameras = cameraRepository.findUsedCameras();
        return cameraMapper.toCameraResponseList(cameras);
    }

    public CameraResponse getCameraById(Long id) {
        Camera camera = cameraRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found"));
    //System.out.println(camera.getIsUsed());
        return cameraMapper.toCameraResponse(camera);
    }

    public CameraResponse addCamera(CameraRequest cameraRequest) {
        if (cameraRepository.findByUrl(cameraRequest.url()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Camera already exists");
        }
        Camera addedCamera = cameraRepository.save(cameraMapper.toCamera(cameraRequest));
        ActionLog actionLog = new ActionLog();
        actionLog.setUser(userRepository.findById(1L).get());
        actionLog.setActionType("Camera added");
        actionLog.setCamera(cameraRepository.findByUrl(cameraRequest.url()).get());
        actionLog.setTime(Instant.now());
        actionLog.setCommentary("");
        actionLogRepository.save(actionLog);
        if (cameraRequest.isUsed()) {
            ffMpegService.startFFmpegStream(cameraRequest.url(),cameraRequest.name());
        }
        return cameraMapper.toCameraResponse(addedCamera);
    }

    public CameraResponse patchById(CameraRequestPatch cameraRequestPatch, Long id) {
        Camera camera = cameraRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found"));
        if(cameraRequestPatch.name() != null && !cameraRequestPatch.name().equals(camera.getName())) {
            camera.setName(cameraRequestPatch.name());
        }
        if(cameraRequestPatch.url() != null && !cameraRequestPatch.url().equals(camera.getUrl())) {
            camera.setUrl(cameraRequestPatch.url());
        }
        if(cameraRequestPatch.isUsed() != null && !cameraRequestPatch.isUsed().equals(camera.getIsUsed())) {
            camera.setIsUsed(cameraRequestPatch.isUsed());
        }
        if(cameraRequestPatch.isUsingTimetable() != null && !cameraRequestPatch.isUsingTimetable().equals(camera.getIsUsingTimetable())) {
            camera.setIsUsingTimetable(cameraRequestPatch.isUsingTimetable());
        }
        return cameraMapper.toCameraResponse(cameraRepository.save(camera));
    }
    public void deleteById(Long id) {
        Camera camera = cameraRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found"));
        cameraRepository.deleteById(id);
    }
}
