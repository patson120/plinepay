/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.authservice.security;

import com.plinepay.core.entities.User;
import com.plinepay.core.repositories.UserRepository;
import com.plinepay.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class PlinePayUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String[] datas = username.split("#");
        Collection<GrantedAuthority> autorities = new ArrayList<>();

        switch (datas[1]) {

            case Constants.TYPE_USER_ADMIN: {
                User user = userRepository.findByUsernameOrPhoneOrEmail(datas[0]);
                if (user == null) {
                    throw new UsernameNotFoundException(username);
                }

                autorities.add(new SimpleGrantedAuthority(Constants.TYPE_USER_ADMIN));
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), autorities);
            }

        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
