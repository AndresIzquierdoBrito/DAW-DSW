package es.cifpcm.IzquierdoAndresFarmaciasWeb;

import es.cifpcm.IzquierdoAndresFarmaciasWeb.data.PersitanceImplFarm;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IzquierdoAndresFarmaciasWebApplication extends SpringBootServletInitializer {

	@Autowired
	private PersitanceImplFarm pst;

	public static void main(String[] args) {
		SpringApplication.run(IzquierdoAndresFarmaciasWebApplication.class, args);
	}

	@PostConstruct
	public void onInit() {
		pst.openJson();
	}

	@PreDestroy
	public void onExit() {
		pst.closeJson();
	}
}