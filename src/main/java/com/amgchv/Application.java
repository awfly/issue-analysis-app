package com.amgchv;

import com.amgchv.models.Permission;
import com.amgchv.models.Role;
import com.amgchv.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PersistenceContext
    EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Add Data of Permission, Role, and User
     */
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Permission readIssue = new Permission("readIssue");
        Permission writeIssue = new Permission("writeIssue");
        Permission manageUser = new Permission("manageUser");

        em.persist(readIssue);
        em.persist(writeIssue);
        em.persist(manageUser);

        Role adminRole = new Role("admin");
        adminRole.setPermissions(new HashSet<Permission>() {{
            add(readIssue);
            add(writeIssue);
            add(manageUser);
        }});
        em.persist(adminRole);

        Role developerRole = new Role("developer");
        developerRole.setPermissions(new HashSet<Permission>() {{
            add(readIssue);
            add(writeIssue);
        }});
        em.persist(developerRole);

        Role guestRole = new Role("guest");
        guestRole.setPermissions(new HashSet<Permission>() {{
            add(readIssue);
        }});
        em.persist(guestRole);

        User guestUser = new User("guest", "guest", "guest", "test@test.com", passwordEncoder.encode("guest"));
        guestUser.setRoles(new HashSet<Role>() {{
            add(guestRole);
        }});
        em.persist(guestUser);

        User developerUser = new User("developer", "developer", "developer", "test@test.com",passwordEncoder.encode("developer"));
        developerUser.setRoles(new HashSet<Role>() {{
            add(developerRole);
        }});
        em.persist(developerUser);

        User adminUser = new User("admin", "admin", "admin", "test@test.com", passwordEncoder.encode("admin"));
        adminUser.setRoles(new HashSet<Role>() {{
            add(adminRole);
        }});
        em.persist(adminUser);
    }

}
