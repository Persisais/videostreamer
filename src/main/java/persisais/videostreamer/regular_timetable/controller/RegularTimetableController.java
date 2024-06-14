package persisais.videostreamer.regular_timetable.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableRequest;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableRequestPatch;
import persisais.videostreamer.regular_timetable.dto.RegularTimetableResponse;
import persisais.videostreamer.regular_timetable.service.RegularTimetableService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/regular-timetables")
@Tag(name = "RegularTimetables")
public class RegularTimetableController {
    private final RegularTimetableService regularTimetableService;

    @GetMapping
    public List<RegularTimetableResponse> getAllRegularTimetables() {
        return regularTimetableService.getAllRegularTimetables();
    }

    @GetMapping("/{id}")
    public RegularTimetableResponse getRegularTimetableById(@PathVariable Long id) {
        return regularTimetableService.getRegularTimetableById(id);
    }

    @GetMapping("/camera/{cameraId}")
    public  List<RegularTimetableResponse> getAllRegularTimetablesByCamera(@PathVariable Long cameraId) {
        return regularTimetableService.getAllRegularTimetablesByCamera(cameraId);
    }

    @PostMapping
    public RegularTimetableResponse addRegularTimetable(@RequestBody RegularTimetableRequest regularTimetableRequest) {
        return regularTimetableService.addRegularTimetable(regularTimetableRequest);
    }

    @PatchMapping("/{id}")
    public RegularTimetableResponse patchRegularTimetableById(@PathVariable Long id, @RequestBody RegularTimetableRequestPatch regularTimetableRequestPatch) {
        return regularTimetableService.patchById(regularTimetableRequestPatch, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegularTimetableById(@PathVariable Long id) {
        regularTimetableService.deleteById(id);
    }
}
