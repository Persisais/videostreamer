package persisais.videostreamer.regular_timetable.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.action_log.model.ActionLog;
import persisais.videostreamer.action_log.repository.ActionLogRepository;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.camera.repository.CameraRepository;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableRequest;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableRequestPatch;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableResponse;
import persisais.videostreamer.regular_timetable.mapper.RegularTimetableMapper;
import persisais.videostreamer.regular_timetable.model.RegularTimetable;
import persisais.videostreamer.regular_timetable.repository.RegularTimetableRepository;
import persisais.videostreamer.user.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RegularTimetableService {
    private final RegularTimetableRepository regularTimetableRepository;
    private final RegularTimetableMapper regularTimetableMapper;
    private final CameraRepository cameraRepository;
    private final UserRepository userRepository;
    private final ActionLogRepository actionLogRepository;

    public List<RegularTimetableResponse> getAllRegularTimetables() {
        List<RegularTimetable> regularTimetables = regularTimetableRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return regularTimetableMapper.toRegularTimetableResponseList(regularTimetables);
    }

    public RegularTimetableResponse getRegularTimetableById(Long id) {
        RegularTimetable regularTimetable = regularTimetableRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RegularTimetable not found"));
        return regularTimetableMapper.toRegularTimetableResponse(regularTimetable);
    }

    public List<RegularTimetableResponse> getAllRegularTimetablesByCamera(Long cameraId) {
        List<RegularTimetable> regularTimetables = regularTimetableRepository.findAllRegularTimetablesByCamera(cameraId).stream().sorted().toList();
        return regularTimetableMapper.toRegularTimetableResponseList(regularTimetables);
    }

    public RegularTimetableResponse addRegularTimetable(RegularTimetableRequest regularTimetableRequest) {
        Camera camera = cameraRepository.findById(regularTimetableRequest.cameraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found"));
        RegularTimetable addedRegularTimetable = new RegularTimetable();
        addedRegularTimetable.setCamera(camera);
        addedRegularTimetable.setDayOfWeek(regularTimetableRequest.dayOfWeek());
        addedRegularTimetable.setTimeStart(regularTimetableRequest.timeStart());
        addedRegularTimetable.setTimeEnd(regularTimetableRequest.timeEnd());
        addedRegularTimetable.setIsUsed(regularTimetableRequest.isUsed());
        regularTimetableRepository.save(addedRegularTimetable);
        //RegularTimetable addedRegularTimetable = regularTimetableRepository.save(regularTimetableMapper.toRegularTimetable(regularTimetableRequest));
        ActionLog actionLog = new ActionLog();
        actionLog.setUser(userRepository.findById(1L).get());
        actionLog.setActionType("RegularTimetable added");
        actionLog.setTime(Instant.now());
        actionLog.setCamera(cameraRepository.findById(regularTimetableRequest.cameraId()).get());
        actionLog.setCommentary("Day:"+addedRegularTimetable.getDayOfWeek()+" "+addedRegularTimetable.getTimeStart()+"-"+addedRegularTimetable.getTimeEnd());
        actionLogRepository.save(actionLog);

        return regularTimetableMapper.toRegularTimetableResponse(addedRegularTimetable);
    }

    public RegularTimetableResponse patchById(RegularTimetableRequestPatch regularTimetableRequestPatch, Long id) {
        RegularTimetable regularTimetable = regularTimetableRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RegularTimetable not found"));
        if(regularTimetableRequestPatch.cameraId() != null && !regularTimetableRequestPatch.cameraId().equals(regularTimetable.getCamera().getId())) {
            regularTimetable.setCamera(cameraRepository.findById(regularTimetableRequestPatch.cameraId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found")));
        }
        if(regularTimetableRequestPatch.dayOfWeek() != null && !regularTimetableRequestPatch.dayOfWeek().equals(regularTimetable.getDayOfWeek())) {
            regularTimetable.setDayOfWeek(regularTimetableRequestPatch.dayOfWeek());
        }
        if(regularTimetableRequestPatch.timeStart() != null && !regularTimetableRequestPatch.timeStart().equals(regularTimetable.getTimeStart())) {
            regularTimetable.setTimeStart(regularTimetableRequestPatch.timeStart());
        }
        if(regularTimetableRequestPatch.timeEnd() != null && !regularTimetableRequestPatch.timeEnd().equals(regularTimetable.getTimeEnd())) {
            regularTimetable.setTimeEnd(regularTimetableRequestPatch.timeEnd());
        }
        if(regularTimetableRequestPatch.isUsed() != null && !regularTimetableRequestPatch.isUsed().equals(regularTimetable.getIsUsed())) {
            regularTimetable.setIsUsed(regularTimetableRequestPatch.isUsed());
        }
        return regularTimetableMapper.toRegularTimetableResponse(regularTimetableRepository.save(regularTimetable));
    }

    public void deleteById(Long id) {
        RegularTimetable regularTimetable = regularTimetableRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RegularTimetable not found"));
        regularTimetableRepository.deleteById(id);
    }
}
