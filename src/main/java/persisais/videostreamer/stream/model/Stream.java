package persisais.videostreamer.stream.model;

import jakarta.persistence.*;
import lombok.*;
import persisais.videostreamer.camera.model.Camera;

@Entity
@Table(name = "streams")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "camera_id", referencedColumnName = "id")
    @ManyToOne
    private Camera camera;

    @Column(name = "output_url")
    private String outputUrl;

    @Column(name = "time")
    private Integer time;

    @Column(name = "is_used")
    private Boolean isUsed;
}
