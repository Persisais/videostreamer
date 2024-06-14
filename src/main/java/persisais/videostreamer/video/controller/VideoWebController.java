package persisais.videostreamer.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import persisais.videostreamer.video.dto.VideoResponse;
import persisais.videostreamer.video.mapper.VideoMapper;
import persisais.videostreamer.video.model.Video;
import persisais.videostreamer.video.service.VideoService;

import java.util.List;

@Controller
@RequestMapping("/videos")
public class VideoWebController {
    private final VideoService videoService;
    private final VideoMapper videoMapper;

    @Autowired
    public VideoWebController(VideoService videoService, VideoMapper videoMapper) {
        this.videoService = videoService;
        this.videoMapper = videoMapper;
    }

    @GetMapping
    public String listVideos(Model model) {
        List<VideoResponse> videoResponses = videoService.getAllVideos();
        model.addAttribute("videos", videoResponses);
        return "videos"; // HTML страница для отображения списка видео
    }

    // Методы для добавления, редактирования и удаления видео
}
