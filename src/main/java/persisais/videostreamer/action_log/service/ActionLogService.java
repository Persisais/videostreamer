package persisais.videostreamer.action_log.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.action_log.dto.ActionLogRequest;
import persisais.videostreamer.action_log.dto.ActionLogRequestPatch;
import persisais.videostreamer.action_log.dto.ActionLogResponse;
import persisais.videostreamer.action_log.mapper.ActionLogMapper;
import persisais.videostreamer.action_log.model.ActionLog;
import persisais.videostreamer.action_log.repository.ActionLogRepository;
import persisais.videostreamer.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActionLogService {
    private final ActionLogRepository actionLogRepository;
    private final ActionLogMapper actionLogMapper;
    private final UserRepository userRepository;

    public List<ActionLogResponse> getAllActionLogs() {
        List<ActionLog> actionLogs = actionLogRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return actionLogMapper.toActionLogResponseList(actionLogs);
    }

    public ActionLogResponse getActionLogById(Long id) {
        ActionLog actionLog = actionLogRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActionLog not found"));
        return actionLogMapper.toActionLogResponse(actionLog);
    }

    public List<ActionLogResponse> getAllActionLogByUser(Long userId) {
        List<ActionLog> actionLogs = actionLogRepository.findAllActionLogByUser(userId).stream().sorted().toList();
        return actionLogMapper.toActionLogResponseList(actionLogs);
    }

    public ActionLogResponse addActionLog(ActionLogRequest actionLogRequest) {
        ActionLog addedActionLog = actionLogRepository.save(actionLogMapper.toActionLog(actionLogRequest));
        return actionLogMapper.toActionLogResponse(addedActionLog);
    }

    public ActionLogResponse patchById(ActionLogRequestPatch actionLogRequestPatch, Long id) {
        ActionLog actionLog = actionLogRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActionLog not found"));
        if(actionLogRequestPatch.actionType() != null && !actionLogRequestPatch.actionType().equals(actionLog.getActionType())) {
            actionLog.setActionType(actionLogRequestPatch.actionType());
        }
        if(actionLogRequestPatch.time() != null && !actionLogRequestPatch.time().equals(actionLog.getTime())) {
            actionLog.setTime(actionLogRequestPatch.time());
        }
        if(actionLogRequestPatch.commentary() != null && !actionLogRequestPatch.commentary().equals(actionLog.getCommentary())) {
            actionLog.setCommentary(actionLogRequestPatch.commentary());
        }
        return actionLogMapper.toActionLogResponse(actionLogRepository.save(actionLog));
    }

    public void deleteById(Long id) {
        ActionLog actionLog = actionLogRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActionLog not found"));
        actionLogRepository.deleteById(id);
    }


}
