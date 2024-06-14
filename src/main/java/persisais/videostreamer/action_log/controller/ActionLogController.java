package persisais.videostreamer.action_log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import persisais.videostreamer.action_log.dto.ActionLogRequest;
import persisais.videostreamer.action_log.dto.ActionLogRequestPatch;
import persisais.videostreamer.action_log.dto.ActionLogResponse;
import persisais.videostreamer.action_log.service.ActionLogService;

import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/action-logs")
@Tag(name = "ActionLogs")
public class ActionLogController {
    private final ActionLogService actionLogService;

    @GetMapping
    public List<ActionLogResponse> getAllActionLogs() {
        return actionLogService.getAllActionLogs();
    }

    @GetMapping("/{id}")
    public ActionLogResponse getActionLogById(@PathVariable Long id) {
        return actionLogService.getActionLogById(id);
    }

    @GetMapping("/user/{userId}")
    public  List<ActionLogResponse> getAllActionLogByUser(@PathVariable Long userId) {
        return actionLogService.getAllActionLogByUser(userId);
    }

    @PostMapping
    public ActionLogResponse addActionLog(@RequestBody ActionLogRequest actionLogRequest) {
        return actionLogService.addActionLog(actionLogRequest);
    }

    @PatchMapping("/{id}")
    public ActionLogResponse patchActionLogById(@PathVariable Long id, @RequestBody ActionLogRequestPatch actionLogRequestPatch) {
        return actionLogService.patchById(actionLogRequestPatch, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActionLogById(@PathVariable Long id) {
        actionLogService.deleteById(id);
    }

}
