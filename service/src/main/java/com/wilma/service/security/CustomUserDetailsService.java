
package com.wilma.service.security;

import com.wilma.entity.forum.users.UserAccount;
import com.wilma.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userRepository;

    /**
     * Override the default method to enable users to be found via username or email.
     * Also forces the method to use the custom user details implementation including user roles and granted authorities
     * @param username The user's username or email address
     * @return Custom user details instance
     * @throws UsernameNotFoundException Thrown when a username or email is given but cannot be found on record
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        if (user == null) user = userRepository.findByEmail(username);//or email
        CustomUserDetails userDetails;
        if (user != null) {
            userDetails = new CustomUserDetails();
            userDetails.setUser(user);
        } else {
            throw new UsernameNotFoundException("User not found with name : " + username);
        }
        return userDetails;
    }

}
