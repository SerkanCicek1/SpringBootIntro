package com.tpe.security.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // !!! bu calssda 1.amacim : Security katmanina User objelerimi verip UserDetails
    // turune cevrilmesini saglamak kisaca kendi Userlarimi security katmanina tanitmis
    // olacagiz
    // 2.amacimiz : Role bilgilerini Granted Auth. a cevirmek

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username).orElseThrow(()->
                new ResourceNotFoundException("user not found with username : " + username));

        if(user !=null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    buildGrantedAuthority(user.getRole())
            );
        } else {
            throw  new UsernameNotFoundException("USer not found with username : " + username);
        }

    }

    private static List<SimpleGrantedAuthority> buildGrantedAuthority(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }

        return authorities;
    }
}