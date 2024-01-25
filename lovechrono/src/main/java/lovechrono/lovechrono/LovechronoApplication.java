package lovechrono.lovechrono;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "LoveChrono", version = "1", description = "API de desenvolvimento do projeto LoveChrono que consiste em uma aplicação de lembranças e uma linha do tempo."))
public class LovechronoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LovechronoApplication.class, args);
	}
}