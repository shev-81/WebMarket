package com.webmarket.services;

import com.webmarket.entities.Role;
import com.webmarket.entities.User;
import com.webmarket.repositories.RoleRepository;
import com.webmarket.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * Репозитарий пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Реопзитарий ролей пользователя.
     */
    private final RoleRepository roleRepository;

    /**
     * Ищет пользователя в реопзитарии по его имени.
     * @param username
     * @return
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Создает нового пользователя с ролями.
     * @param user
     * @param role
     */
    @Transactional
    public void createNewUser(User user, String role){
        User u = userRepository.save(user);
        Role r = roleRepository.findRoleByName(role);
        u.getRoles().add(r);
        userRepository.save(u);
    }

    /**
     * Выбирает всех пользователей.
     * @return
     */
    public List<User> getAll(){
        return userRepository.getAll();
    }

    /**
     * Удаляет пользователя по Id.
      * @param id
     */
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    /**
     * Получает данные пользователя по его имени.
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}