package persisais.videostreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VideostreamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideostreamerApplication.class, args);
	}

}
