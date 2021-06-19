package com.pu.georgidinov.pupracticumvoltwo.service;

import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.exception.ResourceNotFoundException;
import com.pu.georgidinov.pupracticumvoltwo.repository.ApplicationUserRepository;
import com.pu.georgidinov.pupracticumvoltwo.repository.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final ShoppingListRepository shoppingListRepository;

    public List<ApplicationUser> findAllApplicationUsers() {
        log.info("ApplicationUserService::findAllApplicationUsers");
        List<ApplicationUser> users = new ArrayList<>();
        this.applicationUserRepository.findAll().forEach(users::add);
        return users;
    }

    public ApplicationUser findApplicationUserById(Long id) {
        log.info("ApplicationUserService::findApplicationUserById, ID passed = {}", id);
        return this.applicationUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User With ID = '" + id + "' NOT FOUND"));
    }

    public ApplicationUser saveApplicationUser(ApplicationUser user) {
        log.info("ApplicationUserService::saveApplicationUser");
        return this.applicationUserRepository.save(user);
    }

    public ApplicationUser addShoppingList(ShoppingList shoppingList, Long id) {
        log.info("ApplicationUserService::addShoppingList, Shopping List passed = {}, ID passed = {}", shoppingList, id);
        return this.applicationUserRepository.findById(id)
                .map(user -> {
                    ShoppingList savedList = this.shoppingListRepository.save(shoppingList);
                    user.addShoppingList(savedList);
                    return this.applicationUserRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User with ID = '" + id + "' NOT FOUND"));
    }

    public void deleteApplicationUserById(Long id) {
        log.info("ApplicationUserService::deleteApplicationUserById, ID passed = {}", id);
        if (!isApplicationUserWithIdExists(id)) {
            throw new ResourceNotFoundException("User With ID = '" + id + "' NOT FOUND");
        }
        this.applicationUserRepository.deleteById(id);
    }

    private boolean isApplicationUserWithIdExists(Long id) {
        return this.applicationUserRepository.findById(id).isPresent();
    }

}