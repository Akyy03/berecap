package com.sda.javacraiova.berecap.repository;

import com.sda.javacraiova.berecap.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM privilege WHERE id NOT IN(SELECT privilege_list_id FROM role_privilege_list WHERE role_list_id= ?1)")
    Optional<List<Privilege>> getUnassignedPrivileges(long roleId);
}
