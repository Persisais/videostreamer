package persisais.videostreamer.timetable.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import persisais.videostreamer.camera.model.Camera;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "timetables")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name="camera_id", referencedColumnName = "id")
    @ManyToOne
    private Camera camera;

    @Column(name="date_start", nullable = false)
    private LocalDateTime dateStart;

    @Column(name="date_end", nullable = false)
    private LocalDateTime dateEnd;

    @Column(name="isUsed", nullable = false)
    private Boolean isUsed;
}
