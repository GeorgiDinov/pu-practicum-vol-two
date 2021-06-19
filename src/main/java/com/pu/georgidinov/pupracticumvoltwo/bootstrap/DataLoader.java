package com.pu.georgidinov.pupracticumvoltwo.bootstrap;

import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUserCredentials;
import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.repository.ApplicationUserRepository;
import com.pu.georgidinov.pupracticumvoltwo.repository.UnitOfMeasureRepository;
import com.pu.georgidinov.pupracticumvoltwo.security.role.ApplicationUserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        this.loadUnitsOfMeasure();
        this.createAdmin();
    }


    private void loadUnitsOfMeasure() {
        String[] uomArray = new String[]{
                "meter", "kilogram", "gram", "pair", "piece"
        };
        for (String uom : uomArray) {
            UnitOfMeasure savedUom = this.unitOfMeasureRepository.save(new UnitOfMeasure().description(uom));
            log.info("Saving unit of measure = '{}'", savedUom);
        }
        log.info("Loaded {} units of measure", this.unitOfMeasureRepository.count());
    }

    private void createAdmin() {
        ApplicationUserCredentials credentials = new ApplicationUserCredentials();
        credentials.email("admin@example.com").password(passwordEncoder.encode("admin")).userRole(ApplicationUserRole.ADMIN);

        ApplicationUser admin = new ApplicationUser();
        admin.firstName("Admin_First_Name").lastName("Admin_Last_Name").credentials(credentials);
        this.applicationUserRepository.save(admin);
        log.info("Created Mock Administrator");
    }

}