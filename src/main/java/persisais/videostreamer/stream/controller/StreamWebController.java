package persisais.videostreamer.stream.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.stream.dto.StreamRequest;
import persisais.videostreamer.stream.dto.StreamResponse;
import persisais.videostreamer.stream.service.StreamService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/streams")
public class StreamWebController {
    private final StreamService streamService;


    // Отображение страницы со списком стримов
    @GetMapping
    public String listStreams(Model model) {
        model.addAttribute("streams", streamService.getAllStreams());
        return "streams";
    }

    @GetMapping("/add")
    public String showAddStreamForm(Model model) {
        model.addAttribute("streamRequest", new StreamRequest(null, "", 0, false));
        return "add-stream";
    }
    @GetMapping("/{outputUrl}")
    public String showStream(@PathVariable String outputUrl, Model model) {
        StreamResponse stream = streamService.getStreamByUrl(outputUrl);
        model.addAttribute("stream", stream);
        return "stream-page";  // HTML страница для показа стрима
    }

    @PostMapping("/add")
    public String addStream(@ModelAttribute StreamRequest streamRequest, Model model) {
        try {
            StreamResponse streamResponse = streamService.addStream(streamRequest);
            return "redirect:/streams";
        } catch (ResponseStatusException e) {
            model.addAttribute("error", e.getReason());
            return "add-stream";
        }
    }

    // Методы для изменения и удаления стримов можно добавить аналогично
}
