package persisais.videostreamer.action_log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persisais.videostreamer.action_log.model.ActionLog;

import java.util.List;

@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
    @Query("SELECT al FROM ActionLog al WHERE al.user.id =:userId")
    List<ActionLog> findAllActionLogByUser(@Param("userId") Long userId);
}
