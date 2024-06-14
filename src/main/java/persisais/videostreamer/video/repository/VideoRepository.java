package persisais.videostreamer.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.video.model.Video;

import java.time.Instant;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.camera.id = :camera_id")
    List<Video> findAllVideoByCamera(@Param("camera_id") Long camera_id);

    //TODO Обдумать логику
    @Query("SELECT v FROM Video v WHERE v.camera.id = :camera_id AND v.dateStart > :date_start AND v.dateEnd < :date_end")
    List<Video> getALlVideosByDatetime(@Param("camera_id") Long camera_id, @Param("date_start") Instant date_start, @Param("date_end") Instant date_end);


}
