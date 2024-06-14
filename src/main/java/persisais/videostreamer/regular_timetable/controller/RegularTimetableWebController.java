package persisais.videostreamer.regular_timetable.controller;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.camera.repository.CameraRepository;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableRequest;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableResponse;
import persisais.videostreamer.regular_timetable.mapper.RegularTimetableMapper;
import persisais.videostreamer.regular_timetable.model.RegularTimetable;
import persisais.videostreamer.regular_timetable.service.RegularTimetableService;
import persisais.videostreamer.stream.dto.StreamResponse;

@Controller
@RequestMapping("/regular-timetables")
public class RegularTimetableWebController {
    private final RegularTimetableService regularTimetableService;
    private final RegularTimetableMapper regularTimetableMapper;
    private final CameraRepository cameraRepository;

    @Autowired
    public RegularTimetableWebController(RegularTimetableService regularTimetableService, RegularTimetableMapper regularTimetableMapper, CameraRepository cameraRepository) {
        this.regularTimetableService = regularTimetableService;
        this.regularTimetableMapper = regularTimetableMapper;
        this.cameraRepository = cameraRepository;
    }

    @GetMapping
    public String listRegularTimetables(Model model) {
        List<RegularTimetableResponse> responses = regularTimetableService.getAllRegularTimetables();
        model.addAttribute("timetables", responses);
        return "regular-timetables";
    }

    @GetMapping("/add")
    public String showAddRegularTimetableForm(Model model) {
        model.addAttribute("regularTimetable", new RegularTimetableResponse(null, null, 1, LocalTime.now(), LocalTime.now().plusHours(1), true));
        return "add-regular-timetable"; // Thymeleaf шаблон для добавления
    }

    @PostMapping("/add")
    public String addRegularTimetable(@ModelAttribute RegularTimetableRequest regularTimetableRequest, Model model) {
        try {
            RegularTimetableResponse regularTimetableResponse = regularTimetableService.addRegularTimetable(regularTimetableRequest);
            return "redirect:/regular-timetables";
        } catch (ResponseStatusException e) {
            model.addAttribute("error", e.getReason());
            return "add-regular-timetable";
        }

    }
}