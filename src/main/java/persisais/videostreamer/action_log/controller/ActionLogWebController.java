package persisais.videostreamer.action_log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import persisais.videostreamer.action_log.dto.ActionLogResponse;
import persisais.videostreamer.action_log.mapper.ActionLogMapper;
import persisais.videostreamer.action_log.service.ActionLogService;

import java.util.List;

@Controller
@RequestMapping("/action-logs")
public class ActionLogWebController {
    private final ActionLogService actionLogService;
    private final ActionLogMapper actionLogMapper;

    @Autowired
    public ActionLogWebController(ActionLogService actionLogService, ActionLogMapper actionLogMapper) {
        this.actionLogService = actionLogService;
        this.actionLogMapper = actionLogMapper;
    }

    @GetMapping
    public String listActionLogs(Model model) {
        List<ActionLogResponse> logResponses = actionLogService.getAllActionLogs();
        model.addAttribute("actionLogs", logResponses);
        return "action-logs";
    }
}
