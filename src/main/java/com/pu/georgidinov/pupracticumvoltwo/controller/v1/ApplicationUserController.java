package com.pu.georgidinov.pupracticumvoltwo.controller.v1;

import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ApplicationUserToApplicationUserDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.RegistrationRequestDtoToApplicationUserConverter;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.converter.ShoppingListDtoToShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ApplicationUserDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.RegistrationRequestDto;
import com.pu.georgidinov.pupracticumvoltwo.api.v1.dto.ShoppingListDto;
import com.pu.georgidinov.pupracticumvoltwo.domain.ApplicationUser;
import com.pu.georgidinov.pupracticumvoltwo.domain.ShoppingList;
import com.pu.georgidinov.pupracticumvoltwo.service.ApplicationUserService;
import com.pu.georgidinov.pupracticumvoltwo.util.TokenBlackList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("assistant/api/v1/user")
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;
    private final RegistrationRequestDtoToApplicationUserConverter toUser;
    private final ApplicationUserToApplicationUserDto toApplicationUserDto;
    private final ShoppingListDtoToShoppingList toShoppingList;


    @Operation(summary = "This Operation Creates Single Application User And Stored In DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Creates And Saves Single User",
                            content = {@Content(mediaType = "application/json")})
            })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationUserDto register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        log.info("ApplicationUserController::register, DTO passed = {}", registrationRequestDto);
        ApplicationUser user = this.toUser.convert(registrationRequestDto);
        ApplicationUser newUser = this.applicationUserService.saveApplicationUser(user);
        return this.toApplicationUserDto.convert(newUser);
    }

    @Operation(summary = "This Is Logout Operation Which Effectively Adds The User Token In Black List")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Puts User Token In Black List")
            })
    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void logout(HttpServletRequest request) {
        log.info("ApplicationUserController::logout");
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Token Candidate For Black List = {}", token);
        TokenBlackList.getInstance().addToken(token);
    }

    @Operation(summary = "This Operation Retrieves Single Application User Stored In DB By The User ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Retrieves Single User From DB",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If User With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApplicationUserDto findUserById(@PathVariable Long id) {
        log.info("ApplicationUserController::findUserById, ID passed = {}", id);
        ApplicationUser user = this.applicationUserService.findApplicationUserById(id);
        return this.toApplicationUserDto.convert(user);
    }

    @Operation(summary = "This Operation Adds Single Shopping List For User Stored In DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "Adds Single Shopping List For User",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Returns Error Message If User With The Passed ID Not Found In DB",
                            content = {@Content(mediaType = "application/json")})
            })
    @PostMapping("/addlist/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('shopping_list:read', 'shopping_list:write')")
    public ApplicationUserDto addShoppingList(@RequestBody ShoppingListDto dto, @PathVariable Long id) {
        log.info("ApplicationUserController::addShoppingList, DTO passed = {}, ID passed = {}", dto, id);
        ShoppingList shoppingList = this.toShoppingList.convert(dto);
        ApplicationUser user = this.applicationUserService.addShoppingList(shoppingList, id);
        return toApplicationUserDto.convert(user);
    }

}