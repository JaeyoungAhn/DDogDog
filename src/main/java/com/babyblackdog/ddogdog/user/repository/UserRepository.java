package com.babyblackdog.ddogdog.user.repository;

import com.babyblackdog.ddogdog.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u join fetch u.group g left join fetch g.permissions gp join fetch gp.permission where u.username = :username")
  Optional<User> findByUsername(@Param("username") String username);

  @Query("select u from User u join fetch u.group g left join fetch g.permissions gp join fetch gp.permission where u.provider = :provider and u.providerId = :providerId")
  Optional<User> findByProviderAndProviderId(@Param("provider") String provider,
      @Param("providerId") String providerId);

}
