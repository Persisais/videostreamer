package persisais.videostreamer.camera.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persisais.videostreamer.camera.model.Camera;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {

    @Query("SELECT c FROM Camera c WHERE c.url = :url")
    Optional<Camera> findByUrl(@Param("url") String url);

    @Query("SELECT c FROM Camera c WHERE c.isUsed = true AND c.isUsingTimetable = false")
    List<Camera> findUsedCameras();

    @Query("SELECT c FROM Camera c WHERE c.isUsed = true AND c.isUsingTimetable = true")
    List<Camera> findUsedTimetableCameras();
}
