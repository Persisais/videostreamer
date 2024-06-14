package persisais.videostreamer.video.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import persisais.videostreamer.video.dto.VideoRequest;
import persisais.videostreamer.video.dto.VideoRequestPatch;
import persisais.videostreamer.video.dto.VideoResponse;
import persisais.videostreamer.video.service.VideoService;

import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/videos")
@Tag(name = "Videos")
public class VideoController {
    private final VideoService videoService;

    @GetMapping
    public List<VideoResponse> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public VideoResponse getVideoById(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }

    @GetMapping("/camera/{cameraId}")
    public  List<VideoResponse> getAllVideosByCamera(@PathVariable Long cameraId) {
        return videoService.getAllVideosByCamera(cameraId);
    }

    @GetMapping("/camera-by-datetime/{cameraId}")
    public  List<VideoResponse> getALlVideosByDatetime(@PathVariable Long cameraId, @RequestBody Instant dateStart, @RequestBody Instant dateEnd) {
        return videoService.getAllVideosByDatetime(cameraId, dateStart, dateEnd);
    }

    @PostMapping
    public VideoResponse addVideo(@RequestBody VideoRequest videoRequest) {
        return videoService.addVideo(videoRequest);
    }

    @PatchMapping("/{id}")
    public VideoResponse patchVideoById(@PathVariable Long id, @RequestBody VideoRequestPatch videoRequestPatch) {
        return videoService.patchById(videoRequestPatch, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVideoById(@PathVariable Long id) {
        videoService.deleteById(id);
    }
}
