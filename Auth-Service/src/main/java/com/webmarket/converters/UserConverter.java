package com.webmarket.converters;

import com.webmarket.entities.Role;
import com.webmarket.entities.User;
import com.webmarket.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import webmarket.auth.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Класс конвертер сущьности в ДТО и из ДТО в сущность.
 */
@Data
@AllArgsConstructor
@Component
public class UserConverter {

    /**
     * Сервис работы с пользователями.
     */
    private UserService userService;

    /**
     * Кодировщик пароля.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Конвертирует ДТО объект в сущность пользователя.
     * @param userDto
     * @return Сущность пользователя
     */
    public User dtoToEntity(UserDto userDto) {
        User u = new User();
        List<Role> roleList = new ArrayList<>();
        u.setId(0L);
        u.setUsername(userDto.getName());
        u.setFio(userDto.getFio());
        u.setPhone(userDto.getPhone());
        u.setEmail(userDto.getMail());
        u.setPassword(passwordEncoder.encode(userDto.getPass()));
        u.setRoles(roleList);
        return u;
    }

    /**
     * Конвертирует Сущность пользователя в ДТО объект.
     * @param user
     * @return ДТО объект пользователя
     */
    public UserDto entityToDto(User user) {
        UserDto uDto = new UserDto();
        uDto.setId(user.getId())
                .setName(user.getUsername())
                .setFio(user.getFio())
                .setRoles(getRole(user))
                .setMail(user.getEmail())
                .setPhone(user.getPhone())
                .setPass("");
        return uDto;
    }

    /**
     * Возваращает список ролей в виде строки. Роли разделены пробелами.
     * @param user
     * @return строковое представление списка ролей.
     */
    @Transactional
    public String getRole (User user){
        return userService.findByUsername(user.getUsername())
                .get()
                .getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
    }
}
