package com.pu.georgidinov.pupracticumvoltwo.bootstrap;

import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import com.pu.georgidinov.pupracticumvoltwo.repository.UnitOfMeasureRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public void run(String... args) throws Exception {
        this.loadUnitsOfMeasure();
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

}