package persisais.videostreamer.timetable.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import persisais.videostreamer.timetable.dto.TimetableRequest;
import persisais.videostreamer.timetable.dto.TimetableRequestPatch;
import persisais.videostreamer.timetable.dto.TimetableResponse;
import persisais.videostreamer.timetable.service.TimetableService;


import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/timetables")
@Tag(name = "Timetables")
public class TimetableController {
    private final TimetableService timetableService;

    @GetMapping
    public List<TimetableResponse> getAllTimetables() {
        return timetableService.getAllTimetables();
    }

    @GetMapping("/{id}")
    public TimetableResponse getTimetableById(@PathVariable Long id) {
        return timetableService.getTimetableById(id);
    }

    @GetMapping("/camera/{cameraId}")
    public  List<TimetableResponse> getAllTimetablesByCamera(@PathVariable Long cameraId) {
        return timetableService.getAllTimetablesByCamera(cameraId);
    }

    @PostMapping
    public TimetableResponse addTimetable(@RequestBody TimetableRequest timetableRequest) {
        return timetableService.addTimetable(timetableRequest);
    }

    @PatchMapping("/{id}")
    public TimetableResponse patchTimetableById(@PathVariable Long id, @RequestBody TimetableRequestPatch timetableRequestPatch) {
        return timetableService.patchById(timetableRequestPatch, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimetableById(@PathVariable Long id) {
        timetableService.deleteById(id);
    }

}
