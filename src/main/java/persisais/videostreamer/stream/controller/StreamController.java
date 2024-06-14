package persisais.videostreamer.stream.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import persisais.videostreamer.stream.dto.StreamRequest;
import persisais.videostreamer.stream.dto.StreamRequestPatch;
import persisais.videostreamer.stream.dto.StreamResponse;
import persisais.videostreamer.stream.service.StreamService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/streams")
@Tag(name = "Streams")
public class StreamController {
    private final StreamService streamService;

    @GetMapping
    public List<StreamResponse> getAllStreams() {
        //System.out.println("CAMERA "+streamService.getUsedStreams());
        return streamService.getAllStreams();
    }

    @GetMapping("/{id}")
    public StreamResponse getStreamById(@PathVariable Long id) {
        return streamService.getStreamById(id);
    }

    @PostMapping
    public StreamResponse addStream(@RequestBody StreamRequest streamRequest) {
        return streamService.addStream(streamRequest);
    }

    @PatchMapping("/{id}")
    public StreamResponse patchStreamById(@PathVariable Long id, @RequestBody StreamRequestPatch streamRequestPatch) {
        return streamService.patchById(streamRequestPatch, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStreamById(@PathVariable Long id) {
        streamService.deleteById(id);
    }
}
