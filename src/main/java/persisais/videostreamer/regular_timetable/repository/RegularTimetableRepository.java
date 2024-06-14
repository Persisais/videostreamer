package persisais.videostreamer.regular_timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persisais.videostreamer.regular_timetable.model.RegularTimetable;
import persisais.videostreamer.timetable.model.Timetable;

import java.util.List;

@Repository
public interface RegularTimetableRepository extends JpaRepository<RegularTimetable, Long> {
    @Query("SELECT rt FROM RegularTimetable rt WHERE rt.camera.id = :cameraId")
    List<RegularTimetable> findAllRegularTimetablesByCamera(@Param("cameraId") Long cameraId);

    @Query("SELECT rt FROM RegularTimetable rt WHERE rt.isUsed = true")
    List<RegularTimetable> findUsedRegularTimetables();
}
