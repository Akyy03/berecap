package com.sda.javacraiova.berecap.controller;

import com.sda.javacraiova.berecap.common.utils.AuthenticationBean;
import com.sda.javacraiova.berecap.model.Role;
import com.sda.javacraiova.berecap.model.UserModel;
import com.sda.javacraiova.berecap.repository.RoleRepository;
import com.sda.javacraiova.berecap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(path = "/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }

    @GetMapping("/users")
    //@RequestMapping(method = {RequestMethod.GET})
    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    //@RequestMapping(method = {RequestMethod.POST})
    public void addUser(@RequestBody UserModel user) {
        userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(name = "id") Long userId){

        // We cut the User_Role link before deleting user because
        // User is child entity in the relation
        List<Role> roleList=roleRepository.findAll();
        for(Role role:roleList){
            role.getUserList().removeIf(a->a.getId()==userId);
        }
        roleRepository.saveAll(roleList);

        userRepository.deleteById(userId);
    }

    @GetMapping("/users/{id}")
    public UserModel getUser(@PathVariable(name = "id") Long userId){
        return userRepository.findById(userId).orElse(null);
    }
    @GetMapping("/users/getbyusername/{username}")
    public UserModel getUserByUsername(@PathVariable(name = "username") String userName){
        UserModel us=userRepository.getUserModelByUsername(userName).orElse(null);
        // Initialize roleList and privilegeList because we use lazyFetching
        for(Role role: us.getRoleList()){
            role.getPrivilegeList().size();
        }
        return us;
    }

    @PutMapping("/users")
    public void updateUser(@RequestBody UserModel user){
        UserModel userToBeUpdated=userRepository.findById(user.getId()).orElse(null);
        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setEmail(user.getEmail());
        userToBeUpdated.setUserType(user.getUserType());
        if(user.getNewPassword()!=null&&user.getNewPassword()!=""){
            userToBeUpdated.setPassword(user.getNewPassword());
        }
        userRepository.save(userToBeUpdated);
    }


}
