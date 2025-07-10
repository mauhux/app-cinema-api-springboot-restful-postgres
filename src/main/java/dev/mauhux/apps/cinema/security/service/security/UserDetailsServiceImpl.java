package dev.mauhux.apps.cinema.security.service.security;

import dev.mauhux.apps.cinema.security.entity.UserEntity;
import dev.mauhux.apps.cinema.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUserNameIgnoreCase(username);

        if (isNull(userEntity)) {
            throw new UsernameNotFoundException("El usuario " + username + " no existe");
        }

        List<SimpleGrantedAuthority> authorities = userEntity.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthorityName())).toList();

        UserDetails userDetails = User.builder()
                .username(userEntity.getUserName())
                .password(userEntity.getHashedPassword())
                .authorities(authorities).build();

        return userDetails;
    }
}
