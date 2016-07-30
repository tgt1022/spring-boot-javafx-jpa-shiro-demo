/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import demo.model.Permission;
import demo.model.Role;
import demo.model.User;
import demo.services.PermissionService;
import demo.services.RoleService;
import demo.services.UserManagerService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.shiro.authc.credential.DefaultPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;


/**
 * @author Thomas Darimont
 */
@Lazy
@SpringBootApplication
public class App extends AbstractJavaFxApplicationSupport {
    @Autowired
    private DefaultPasswordService passwordService;
    
    @Autowired
    private UserManagerService userManagerService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PermissionService permissionService;
    
    
    @Override
    public void start(Stage stage) throws Exception {
         // define permissions
        final Permission p1 = new Permission();
        p1.setPermissionname("VIEW_ALL_USERS");
        permissionService.addPermission(p1);
        final Permission p2 = new Permission();
        p2.setPermissionname("DO_SOMETHING");
        permissionService.addPermission(p2);
        // define roles
        final Role roleAdmin = new Role();
        roleAdmin.setRolename("ADMIN");
        roleAdmin.getPermissionList().add(p1);
        roleService.addRole(roleAdmin);
        // define user
        final User user = new User();
        user.setUsername("yunus");
        user.setPassword(passwordService.encryptPassword("123qwe"));
        user.getRoleList().add(roleAdmin);
        userManagerService.addUser(user);
    }

    public static void main(String[] args) {
        launchApp(App.class, args);
    }

}