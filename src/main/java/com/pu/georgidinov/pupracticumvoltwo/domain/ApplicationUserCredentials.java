package com.pu.georgidinov.pupracticumvoltwo.domain;

import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseEntity;
import com.pu.georgidinov.pupracticumvoltwo.security.role.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApplicationUserCredentials implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;


    @OneToOne(mappedBy = "applicationUserCredentials", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ApplicationUser applicationUser;

    @Enumerated(value = EnumType.STRING)
    private ApplicationUserRole userRole;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApplicationUserCredentials that = (ApplicationUserCredentials) o;
        if (!id.equals(that.id)) {
            return false;
        }
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    //== builder methods ==
    public ApplicationUserCredentials id(Long id) {
        this.id = id;
        return this;
    }

    public ApplicationUserCredentials email(String email) {
        this.email = email;
        return this;
    }

    public ApplicationUserCredentials password(String password) {
        this.password = password;
        return this;
    }

    public ApplicationUserCredentials applicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
        return this;
    }

    public ApplicationUserCredentials userRole(ApplicationUserRole userRole) {
        this.userRole = userRole;
        return this;
    }
}