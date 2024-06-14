package persisais.videostreamer.camera.controller;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.camera.dto.CameraRequest;
import persisais.videostreamer.camera.dto.CameraResponse;
import persisais.videostreamer.camera.serivce.CameraService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cameras")
public class CameraWebController {
    private final CameraService cameraService;

    @GetMapping
    public String getAllCameras(Model model) {
        model.addAttribute("cameras", cameraService.getAllCameras());
        return "cameras"; // Возвращаем имя шаблона Thymeleaf
    }
    // Показать форму добавления новой камеры
    @GetMapping("/add")
    public String showAddCameraForm(Model model) {
        model.addAttribute("cameraRequest", new CameraRequest("", "", false, false));
        return "add-camera";
    }

    // Обработать добавление новой камеры
    @PostMapping("/add")
    public String addCamera(@ModelAttribute CameraRequest cameraRequest, Model model) {
        try {
            CameraResponse cameraResponse = cameraService.addCamera(cameraRequest);
            return "redirect:/cameras";  // Перенаправить на список камер после успешного добавления
        } catch (ResponseStatusException e) {
            model.addAttribute("error", e.getReason());
            return "add-camera";  // Оставаться на странице добавления в случае ошибки
        }
    }
}
