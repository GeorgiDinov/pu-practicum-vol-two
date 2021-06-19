package com.pu.georgidinov.pupracticumvoltwo.repository;

import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {

}