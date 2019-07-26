package com.mantis.tech.polls.security;

import com.mantis.tech.polls.models.User;
import com.mantis.tech.polls.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmailAddress) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAddressOrUserName(userNameOrEmailAddress, userNameOrEmailAddress).
                orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + userNameOrEmailAddress));
        return UserPrincipal.create(user);
    }

    public UserDetails loadUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found with user id : " + userId)
        );

        return UserPrincipal.create(user);
    }

}
