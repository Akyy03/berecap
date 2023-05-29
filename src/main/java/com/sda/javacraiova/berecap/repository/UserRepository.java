package com.sda.javacraiova.berecap.repository;


import com.sda.javacraiova.berecap.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public Optional<UserModel> getUserModelByUsername(String username);

    @Query(nativeQuery = true,
            value = "SELECT * FROM user_model WHERE id NOT IN(SELECT user_list_id FROM role_user_list WHERE role_list_id= ?1)")
    Optional<List<UserModel>> getUnassignedUsers(long roleId);
}
