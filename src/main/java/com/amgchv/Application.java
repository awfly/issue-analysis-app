package com.amgchv;

import com.amgchv.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        guestUser.setRole(guestRole);
        em.persist(guestUser);

        User developerUser = new User("developer", "developer", "developer", "test@test.com",passwordEncoder.encode("developer"));
        developerUser.setRole(developerRole);
        em.persist(developerUser);

        User adminUser = new User("admin", "admin", "admin", "test@test.com", passwordEncoder.encode("admin"));
        adminUser.setRole(adminRole);
        em.persist(adminUser);

//        Project project1 = new Project("Security Development", "Our Security Development Project");
//        em.persist(project1);
//
//        Project project2 = new Project("Core Development", "Our Core Development Project");
//        em.persist(project2);
//
//        Project project3 = new Project("Framework Development", "Our Framework Development Project");
//        em.persist(project3);
//
//        Scenario scenario = new Scenario("Login into the system scenario", "Scenario contains testcases associate with login into the system logic");
//        scenario.setProject(project1);
//        em.persist(scenario);
//
//        Scenario scenario2 = new Scenario("Logout from the system scenario", "Scenario contains testcases associate with logout from system logic");
//        scenario2.setProject(project2);
//        scenario2.setProject(project3);
//        em.persist(scenario2);
//
//        Testcase testcase1 = new Testcase("Login with correct credentials", "1. Open application.\n 2. Enter correct username \n 3. Enter correct password \n 4. Click login button \n ER = Login successful");
//        Testcase testcase2 = new Testcase("Login with incorrect credentials", "1. Open application.\n 2. Enter correct username \n 3. Enter incorrect password \n 4. Click login button \n ER = Login failed");
//        testcase1.setScenario(scenario);
//        testcase1.setScenario(scenario2);
//        em.persist(testcase1);
//        testcase2.setScenario(scenario);
//        testcase2.setScenario(scenario2);
//
//        em.persist(testcase2);
//
//        Scenario scenarioInDb = em.find(Scenario.class, scenario.getScenarioId());
//        scenarioInDb.getTestcases().forEach(System.out::println);
    }
}
