package htw.gruppe.backend.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.repository.KandidatenRepository;

@Configuration
public class AdminSeeder { 
  
@Bean
  CommandLineRunner seedAdmin(KandidatenRepository repo, BCryptPasswordEncoder enc) {
    return args -> {
      if (repo.findByMatrikelnummer("admin").isEmpty()) {
        Kandidat admin = new Kandidat();
        admin.setMatrikelnummer("admin");
        admin.setPassword(enc.encode("Verwaltung321"));
        admin.setRole("ADMIN");

        
        admin.setFachbereich("Verwaltung");
        admin.setVorname("Admin");
        admin.setNachname("Account");
        admin.setStudiengang("Verwaltung");

        repo.save(admin);
      }
    };
  }
 
}
