package com.babyblackdog.ddogdog.user.repository;

import com.babyblackdog.ddogdog.user.model.Group;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByName(String name);

}
