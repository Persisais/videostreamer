package persisais.videostreamer.regular_timetable.model;

import jakarta.persistence.*;
import lombok.*;
import persisais.videostreamer.camera.model.Camera;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "regular_timetables")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegularTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name="camera_id", referencedColumnName = "id")
    @ManyToOne
    private Camera camera;

    @Column(name="day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name="time_start", nullable = false)
    private LocalTime timeStart;

    @Column(name="time_end", nullable = false)
    private LocalTime timeEnd;

    @Column(name="is_used", nullable = false)
    private Boolean isUsed;
}
