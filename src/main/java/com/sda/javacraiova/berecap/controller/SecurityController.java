package com.sda.javacraiova.berecap.controller;

import com.sda.javacraiova.berecap.model.Privilege;
import com.sda.javacraiova.berecap.model.Role;
import com.sda.javacraiova.berecap.model.UserModel;
import com.sda.javacraiova.berecap.model.UserType;
import com.sda.javacraiova.berecap.repository.PrivilegeRepository;
import com.sda.javacraiova.berecap.repository.RoleRepository;
import com.sda.javacraiova.berecap.repository.UserRepository;
import com.sda.javacraiova.berecap.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class SecurityController {

    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    // Roles
    @GetMapping("/role")
    public List<Role> getRoles() {
        List<Role> roleList= roleRepository.findAll();
        return roleList;
    }

    @PostMapping("/role")
    public void addRole(@RequestBody Role role) {
        roleRepository.save(role);
    }

    @DeleteMapping("/role/{id}")
    public void deleteRole(@PathVariable(name = "id") Long roleId){
        roleRepository.deleteById(roleId);
    }

    @GetMapping("/role/{id}")
    public Role getRole(@PathVariable(name = "id") Long roleId){
        Role role=roleRepository.findById(roleId).orElse(null);
        role.getPrivilegeList().size();
        role.getUserList().size();
        return role;
    }

    @PutMapping("/role")
    public void updateRole(@RequestBody Role role){
        roleRepository.save(role);
    }

    @PostMapping("/role/assignprivileges/{id}")
    public void assignPrivilegesToRole(@PathVariable(name = "id") Long roleId, @RequestBody List<Privilege> privilegeList) {
        Role role= roleRepository.findById(roleId).orElse(null);
        role.getPrivilegeList().addAll(privilegeList);
        roleRepository.save(role);
    }

    @PostMapping("/role/unassignprivileges/{id}")
    public void unassignPrivilegesFromRole(@PathVariable(name = "id") Long roleId, @RequestBody List<Privilege> privilegeList) {
        Role role= roleRepository.findById(roleId).orElse(null);
        role.getPrivilegeList().removeIf(a->privilegeList.stream().filter(b->b.getId()==a.getId()).collect(Collectors.toList()).size()>0);
        roleRepository.save(role);
    }

    @GetMapping("/role/unassignedprivileges/{id}")
    public List<Privilege> getUnassignedPrivileges(@PathVariable(name = "id") Long roleId){
        return privilegeRepository.getUnassignedPrivileges(roleId).orElse(null);
    }

    //Assign Users
    @PostMapping("/role/assignusers/{id}")
    public void assignUsersToRole(@PathVariable(name = "id") Long roleId, @RequestBody List<UserModel> userList) {
        Role role= roleRepository.findById(roleId).orElse(null);
        role.getUserList().addAll(userList);
        roleRepository.save(role);
    }

    @PostMapping("/role/unassignusers/{id}")
    public void unassignUsersFromRole(@PathVariable(name = "id") Long roleId, @RequestBody List<UserModel> userList) {
        Role role= roleRepository.findById(roleId).orElse(null);
        role.getUserList().removeIf(a->userList.stream().filter(b->b.getId()==a.getId()).collect(Collectors.toList()).size()>0);
        roleRepository.save(role);
    }

    @GetMapping("/role/unassignedusers/{id}")
    public List<UserModel> getUnassignedUsers(@PathVariable(name = "id") Long roleId){
        return userRepository.getUnassignedUsers(roleId).orElse(null);
    }

    // Privileges
    @GetMapping("/privilege")
    public List<Privilege> getPrivileges() {
        List<Privilege> privList= privilegeRepository.findAll();
        return privList;
    }

    @PostMapping("/privilege")
    public void addPrivilege(@RequestBody Privilege privilege) {
        privilegeRepository.save(privilege);
    }

    @DeleteMapping("/privilege/{id}")
    public void deletePrivilege(@PathVariable(name = "id") Long privId){
        privilegeRepository.deleteById(privId);
    }

    @GetMapping("/privilege/{id}")
    public Privilege getPrivilege(@PathVariable(name = "id") Long privId){
        return privilegeRepository.findById(privId).orElse(null);
    }

    @PutMapping("/privilege")
    public void updatePrivilege(@RequestBody Privilege privilege){
        privilegeRepository.save(privilege);
    }
}
