package com.krug.teste.repositories;

import com.krug.teste.model.Role;
import com.krug.teste.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
