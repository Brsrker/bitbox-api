package com.jarvi.bitboxapi.config;

import com.jarvi.bitboxapi.persistence.entity.Privilege;
import com.jarvi.bitboxapi.persistence.entity.Role;
import com.jarvi.bitboxapi.persistence.entity.User;
import com.jarvi.bitboxapi.persistence.repository.PrivilegeRepository;
import com.jarvi.bitboxapi.persistence.repository.RoleRepository;
import com.jarvi.bitboxapi.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Set<Privilege> adminPrivileges = new HashSet<>();
        adminPrivileges.add(createPrivilegeIfNotFound("USER_CREATE"));
        adminPrivileges.add(createPrivilegeIfNotFound("USER_DELETE"));
        adminPrivileges.add(createPrivilegeIfNotFound("USER_LIST"));
        adminPrivileges.add(createPrivilegeIfNotFound("ITEM_DELETE"));

        Set<Privilege> userPrivileges = new HashSet<>();
        userPrivileges.add(createPrivilegeIfNotFound("ITEM_CREATE"));
        userPrivileges.add(createPrivilegeIfNotFound("ITEM_LIST"));
        userPrivileges.add(createPrivilegeIfNotFound("ITEM_UPDATE"));
        userPrivileges.add(createPrivilegeIfNotFound("ITEM_DEACTIVATE"));

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(createRoleIfNotFound("ROLE_ADMIN", adminPrivileges));
        adminRoles.add(createRoleIfNotFound("ROLE_USER", userPrivileges));

        createAdminIfNotFound(adminRoles);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(createRoleIfNotFound("ROLE_USER", userPrivileges));

        createUserIfNotFound(userRoles);

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(
            String name, Set<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private User createAdminIfNotFound(Set<Role> roles) {
        User admin = userRepository.findByUsername("admin");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.setFirstName("jarvi");
            admin.setLastName("melendez");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }
        return admin;
    }

    @Transactional
    private User createUserIfNotFound(Set<Role> roles) {
        User user = userRepository.findByUsername("user");
        if (user == null) {
            user = new User();
            user.setUsername("user");
            user.setFirstName("firstName");
            user.setLastName("lastName");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(roles);
            userRepository.save(user);
        }
        return user;
    }
}
