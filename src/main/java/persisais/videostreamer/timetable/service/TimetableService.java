package persisais.videostreamer.timetable.service;

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
import persisais.videostreamer.regular_timetable.model.RegularTimetable;
import persisais.videostreamer.timetable.dto.TimetableRequest;
import persisais.videostreamer.timetable.dto.TimetableRequestPatch;
import persisais.videostreamer.timetable.dto.TimetableResponse;
import persisais.videostreamer.timetable.mapper.TimetableMapper;
import persisais.videostreamer.timetable.model.Timetable;
import persisais.videostreamer.timetable.repository.TimetableRepository;
import persisais.videostreamer.user.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TimetableService {
    private final TimetableRepository timetableRepository;
    private final TimetableMapper timetableMapper;
    private final CameraRepository cameraRepository;
    private final UserRepository userRepository;
    private final ActionLogRepository actionLogRepository;

    public List<TimetableResponse> getAllTimetables() {
        List<Timetable> timetables = timetableRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return timetableMapper.toTimetableResponseList(timetables);
    }

    public TimetableResponse getTimetableById(Long id) {
        Timetable timetable = timetableRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Timetable not found"));
        return timetableMapper.toTimetableResponse(timetable);
    }

    public List<TimetableResponse> getAllTimetablesByCamera(Long cameraId) {
        List<Timetable> timetables = timetableRepository.findAllTimetablesByCamera(cameraId).stream().sorted().toList();
        return timetableMapper.toTimetableResponseList(timetables);
    }

    public TimetableResponse addTimetable(TimetableRequest timetableRequest) {
        //Timetable addedTimetable = timetableRepository.save(timetableMapper.toTimetable(timetableRequest));
        Camera camera = cameraRepository.findById(timetableRequest.cameraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found"));
    Timetable addedTimetable = new Timetable();
        addedTimetable.setCamera(camera);
        addedTimetable.setDateStart(timetableRequest.dateStart());
        addedTimetable.setDateEnd(timetableRequest.dateEnd());
        addedTimetable.setIsUsed(timetableRequest.isUsed());
        timetableRepository.save(addedTimetable);
        //RegularTimetable addedRegularTimetable = regularTimetableRepository.save(regularTimetableMapper.toRegularTimetable(regularTimetableRequest));
        ActionLog actionLog = new ActionLog();
        actionLog.setUser(userRepository.findById(1L).get());
        actionLog.setActionType("Timetable added");
        actionLog.setTime(Instant.now());
        actionLog.setCamera(camera);
        actionLog.setCommentary(timetableRequest.dateStart()+"-"+timetableRequest.dateEnd());
        actionLogRepository.save(actionLog);
        return timetableMapper.toTimetableResponse(addedTimetable);
    }

    public TimetableResponse patchById(TimetableRequestPatch timetableRequestPatch, Long id) {
        Timetable timetable = timetableRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Timetable not found"));
        if(timetableRequestPatch.cameraId() != null && !timetableRequestPatch.cameraId().equals(timetable.getCamera().getId())) {
            timetable.setCamera(cameraRepository.findById(timetableRequestPatch.cameraId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found")));
        }
        if(timetableRequestPatch.dateStart() != null && !timetableRequestPatch.dateStart().equals(timetable.getDateStart())) {
            timetable.setDateStart(timetableRequestPatch.dateStart());
        }
        if(timetableRequestPatch.dateEnd() != null && !timetableRequestPatch.dateEnd().equals(timetable.getDateEnd())) {
            timetable.setDateEnd(timetableRequestPatch.dateEnd());
        }
        if(timetableRequestPatch.isUsed() != null && !timetableRequestPatch.isUsed().equals(timetable.getIsUsed())) {
            timetable.setIsUsed(timetableRequestPatch.isUsed());
        }
        return timetableMapper.toTimetableResponse(timetableRepository.save(timetable));
    }

    public void deleteById(Long id) {
        Timetable timetable = timetableRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Timetable not found"));
        timetableRepository.deleteById(id);
    }




}
