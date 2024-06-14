package persisais.videostreamer.camera.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import persisais.videostreamer.camera.dto.CameraRequest;
import persisais.videostreamer.camera.dto.CameraRequestPatch;
import persisais.videostreamer.camera.dto.CameraResponse;
import persisais.videostreamer.camera.serivce.CameraService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cameras")
@Tag(name = "Camera")
public class CameraController {
    private final CameraService cameraService;

    @GetMapping
    public List<CameraResponse> getAllCameras() {
    //System.out.println("CAMERA "+cameraService.getUsedCameras());
        return cameraService.getAllCameras();
    }

    @GetMapping("/{id}")
    public CameraResponse getCameraById(@PathVariable Long id) {
        return cameraService.getCameraById(id);
    }

    @PostMapping
    public CameraResponse addCamera(@RequestBody CameraRequest cameraRequest) {
        return cameraService.addCamera(cameraRequest);
    }

    @PatchMapping("/{id}")
    public CameraResponse patchCameraById(@PathVariable Long id, @RequestBody CameraRequestPatch cameraRequestPatch) {
        return cameraService.patchById(cameraRequestPatch, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCameraById(@PathVariable Long id) {
        cameraService.deleteById(id);
    }
}
