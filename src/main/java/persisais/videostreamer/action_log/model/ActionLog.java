package persisais.videostreamer.action_log.model;

import jakarta.persistence.*;
import lombok.*;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.user.model.User;

import java.time.Instant;

@Entity
@Table(name = "action_logs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;

    @Column(name="action_type", nullable = false)
    private String actionType;

    @JoinColumn(name = "camera_id", referencedColumnName = "id")
    @ManyToOne
    private Camera camera;

    @Column(name="time", nullable = false)
    private Instant time;

    @Column(name="commentary", nullable = false)
    private String commentary;

}
