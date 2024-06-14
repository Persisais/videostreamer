package persisais.videostreamer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import persisais.videostreamer.action_log.dto.ActionLogRequest;
import persisais.videostreamer.action_log.service.ActionLogService;
import persisais.videostreamer.camera.repository.CameraRepository;
import persisais.videostreamer.video.dto.VideoRequest;
import persisais.videostreamer.video.service.VideoService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FFMpegService {
  @Autowired
  private VideoService videoService;
  @Autowired
  private CameraRepository cameraRepository;
  @Async
  public void startRecording(String cameraUrl, String cameraName) {
    LocalDateTime now = LocalDateTime.now();
    String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    String timePath = now.format(DateTimeFormatter.ofPattern("HH_mm_ss"));
    String timePath2 = now.plusMinutes(2).format(DateTimeFormatter.ofPattern("HH_mm_ss"));

    String directoryPath = "D://Videostreamer/" + datePath + "/" + cameraName;
    String outputFilePath = directoryPath + "/" + timePath + "-"+ timePath2+ ".mp4";
    Long cameraId = cameraRepository.findByUrl(cameraUrl).get().getId();
    VideoRequest videoRequest = new VideoRequest(cameraId, Instant.now(), Instant.now().plusSeconds(120), outputFilePath);
    videoService.addVideo(videoRequest);

    // Создание директорий
    new File(directoryPath).mkdirs();

    ProcessBuilder builder = new ProcessBuilder();
    builder.command(
        "ffmpeg",
        "-i",
        cameraUrl,
        "-t",
        "120",
        "-vcodec",
        "copy",
        "-acodec",
        "copy",
        outputFilePath);

    try {
      Process process = builder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
      int exitCode = process.waitFor();
      System.out.println("Exited with code: " + exitCode);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
  @Async
  public void startFFmpegStream(String inputUrl, String outputUrl) {
    ProcessBuilder builder = new ProcessBuilder(
            "ffmpeg", "-i", inputUrl, "-c", "copy", "-f", "flv", outputUrl);
    try {
      Process process = builder.start();
      // Логирование или другие действия с процессом
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
