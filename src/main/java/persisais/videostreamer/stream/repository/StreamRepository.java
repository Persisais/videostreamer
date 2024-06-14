package persisais.videostreamer.stream.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.stream.model.Stream;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Long> {
    @Query("SELECT s FROM Stream s WHERE s.camera.id = :cameraId")
    Optional<Stream> findByCamera(@Param("cameraId") Long cameraId);

    @Query("SELECT s FROM Stream s WHERE s.outputUrl = :outputUrl")
    Optional<Stream> findByOutputUrl(@Param("outputUrl") String outputUrl);

    @Query("SELECT s FROM Stream s WHERE s.isUsed = true")
    List<Stream> findUsedStreams();
}
