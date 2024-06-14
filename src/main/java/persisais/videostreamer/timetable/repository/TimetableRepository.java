package persisais.videostreamer.timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persisais.videostreamer.stream.model.Stream;
import persisais.videostreamer.timetable.model.Timetable;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long>  {
    @Query("SELECT t FROM Timetable t WHERE t.camera.id = :cameraId")
    List<Timetable> findAllTimetablesByCamera(@Param("cameraId") Long cameraId);

    @Query("SELECT t FROM Timetable t WHERE t.isUsed = true")
    List<Timetable> findUsedTimetables();
}
