package persisais.videostreamer.camera.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "cameras")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Camera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "is_using_timetable", nullable = false)
    private Boolean isUsingTimetable;

}
