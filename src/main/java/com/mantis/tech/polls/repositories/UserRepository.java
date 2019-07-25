package com.mantis.tech.polls.repositories;

import com.mantis.tech.polls.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddressOrUserName(String emailAddress, String userName);

    Optional<User> findByEmailAddress(String emailAddress);

    List<User> findByUserIdIn(List<Long> userIds);

    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmailAddress(String emailAddress);
}
