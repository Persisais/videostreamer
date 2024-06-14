package persisais.videostreamer.timetable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableResponse;
import persisais.videostreamer.timetable.dto.TimetableRequest;
import persisais.videostreamer.timetable.dto.TimetableResponse;
import persisais.videostreamer.timetable.mapper.TimetableMapper;
import persisais.videostreamer.timetable.model.Timetable;
import persisais.videostreamer.timetable.service.TimetableService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/timetables")
public class TimetableWebController {
    private final TimetableService timetableService;
    private final TimetableMapper timetableMapper;

    @Autowired
    public TimetableWebController(TimetableService timetableService, TimetableMapper timetableMapper) {
        this.timetableService = timetableService;
        this.timetableMapper = timetableMapper;
    }

    @GetMapping
    public String listTimetables(Model model) {
        List<TimetableResponse> timetableResponses = timetableService.getAllTimetables();
        model.addAttribute("timetables", timetableResponses);
        return "timetables";  // Thymeleaf шаблон для отображения списка расписаний
    }

    @GetMapping("/add")
    public String showAddTimetableForm(Model model) {
    model.addAttribute(
        "timetableRequest",new TimetableRequest(null, LocalDateTime.now(), LocalDateTime.now().plusHours(1), true));
        return "add-timetable";  // Форма для добавления нового расписания
    }

    @PostMapping("/add")
    public String addTimetable(@ModelAttribute TimetableRequest timetableRequest, Model model) {
        try {
            TimetableResponse timetableResponse = timetableService.addTimetable(timetableRequest);
            return "redirect:/timetables";
        } catch (ResponseStatusException e) {
            model.addAttribute("error", e.getReason());
            return "add-timetable";
        }
    }

}
