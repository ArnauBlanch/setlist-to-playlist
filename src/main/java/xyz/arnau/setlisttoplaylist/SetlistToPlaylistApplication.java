package xyz.arnau.setlisttoplaylist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SetlistToPlaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(SetlistToPlaylistApplication.class, args);
	}

}
