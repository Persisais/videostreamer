package persisais.videostreamer.video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import persisais.videostreamer.camera.repository.CameraRepository;
import persisais.videostreamer.video.dto.VideoRequest;
import persisais.videostreamer.video.dto.VideoRequestPatch;
import persisais.videostreamer.video.dto.VideoResponse;
import persisais.videostreamer.video.mapper.VideoMapper;
import persisais.videostreamer.video.model.Video;
import persisais.videostreamer.video.repository.VideoRepository;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final CameraRepository cameraRepository;

    public List<VideoResponse> getAllVideos() {
        List<Video> videos = videoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return videoMapper.toVideoResponseList(videos);
    }

    public VideoResponse getVideoById(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found"));
        return videoMapper.toVideoResponse(video);
    }

    public List<VideoResponse> getAllVideosByCamera(Long cameraId) {
        List<Video> videos = videoRepository.findAllVideoByCamera(cameraId).stream().sorted().toList();
        return videoMapper.toVideoResponseList(videos);
    }
    public List<VideoResponse> getAllVideosByDatetime(Long cameraId, Instant dateStart, Instant dateEnd) {
        List<Video> videos = videoRepository.getALlVideosByDatetime(cameraId, dateStart, dateEnd).stream().sorted().toList();
        return videoMapper.toVideoResponseList(videos);
    }

    public VideoResponse addVideo(VideoRequest videoRequest) {
        //Video addedVideo = videoRepository.save(videoMapper.toVideo(videoRequest));
        Video addedVideo = new Video();
        addedVideo.setPath(videoRequest.path());
        addedVideo.setCamera(cameraRepository.findById(videoRequest.cameraId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found")));
        addedVideo.setDateStart(videoRequest.dateStart());
        addedVideo.setDateEnd(videoRequest.dateEnd());
        videoRepository.save(addedVideo);
        return videoMapper.toVideoResponse(addedVideo);
    }

    public VideoResponse patchById(VideoRequestPatch videoRequestPatch, Long id) {
        Video video = videoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found"));
        if(videoRequestPatch.cameraId() != null && !videoRequestPatch.cameraId().equals(video.getCamera().getId())) {
            video.setCamera(cameraRepository.findById(videoRequestPatch.cameraId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Camera not found")));
        }
        if(videoRequestPatch.dateStart() != null && !videoRequestPatch.dateStart().equals(video.getDateStart())) {
            video.setDateStart(videoRequestPatch.dateStart());
        }
        if(videoRequestPatch.dateEnd() != null && !videoRequestPatch.dateEnd().equals(video.getDateEnd())) {
            video.setDateEnd(videoRequestPatch.dateEnd());
        }
        if(videoRequestPatch.path() != null && !videoRequestPatch.path().equals(video.getPath())) {
            video.setPath(videoRequestPatch.path());
        }
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public void deleteById(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found"));
        videoRepository.deleteById(id);
    }

}
