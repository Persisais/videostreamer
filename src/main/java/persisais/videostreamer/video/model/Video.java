package persisais.videostreamer.video.model;

import jakarta.persistence.*;
import lombok.*;
import persisais.videostreamer.camera.model.Camera;
import persisais.videostreamer.timetable.model.Timetable;

import java.time.Instant;

@Entity
@Table(name = "videos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "camera_id", referencedColumnName = "id")
    @ManyToOne
    private Camera camera;

    @Column(name="date_start", nullable = false)
    private Instant dateStart;

    @Column(name="date_end", nullable = false)
    private Instant dateEnd;

    @Column(name="path", nullable = false)
    private String path;
}
