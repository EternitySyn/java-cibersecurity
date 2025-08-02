package ar.edu.centro8.ps.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
		
		 System.out.println("Started Application, debug below");
		 System.out.println(new BCryptPasswordEncoder().encode("1234"));
		 System.out.println("End of debug");
	}

}
