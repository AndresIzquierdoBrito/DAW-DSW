package es.cifpcm.AndresNauzetFarmaciasWeb;

import es.cifpcm.AndresNauzetFarmaciasWeb.data.ImplementsInterface;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AndresNauzetFarmaciasWebApplication extends SpringBootServletInitializer {

	@Autowired
	private ImplementsInterface pst;

	public static void main(String[] args) {
		SpringApplication.run(AndresNauzetFarmaciasWebApplication.class, args);
	}

	@PostConstruct
	public void onInit() {
		pst.openJSON();
	}

	@PreDestroy
	public void onExit() {
		pst.closeJSON();
	}
}