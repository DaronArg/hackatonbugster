package com.ganatan.backend_java;

import com.ganatan.backend_java.modules.tenant.Tenant;
import com.ganatan.backend_java.modules.tenant.TenantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StarterAppApplication {

	public static void main(String[] args) {
		System.out.println(" [ganatan] >>>Lancement de l'application StarterAppApplication");		
		SpringApplication.run(StarterAppApplication.class, args);
	}

    @Bean
    CommandLineRunner commandLineRunner(TenantRepository tenantRepository) {
        return args -> {
            if (tenantRepository.findByTenantId("tenant1").isEmpty()) {
                tenantRepository.save(new Tenant(null, "tenant1", "Cliente 1"));
                System.out.println("Tenant 'tenant1' creado.");
            }
            if (tenantRepository.findByTenantId("tenant2").isEmpty()) {
                tenantRepository.save(new Tenant(null, "tenant2", "Cliente 2"));
                System.out.println("Tenant 'tenant2' creado.");
            }
        };
    }

}
