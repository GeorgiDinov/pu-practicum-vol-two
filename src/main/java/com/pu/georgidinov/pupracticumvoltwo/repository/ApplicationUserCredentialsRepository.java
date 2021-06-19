package com.pu.georgidinov.pupracticumvoltwo.repository;

import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserCredentialsRepository extends CrudRepository<ApplicationUserCredentials, Long> {

    Optional<ApplicationUserCredentials> findByEmail(String email);

}