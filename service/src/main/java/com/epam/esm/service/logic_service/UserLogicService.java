package com.epam.esm.service.logic_service;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

/**
 * Class UserLogicService is implementation of interface UserService
 * The class presents service logic layer for User entity
 */
@Service("userLogicService")
public class UserLogicService implements UserService<User>, UserDetailsService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final UserValidator userValidator;

    private final BCryptPasswordEncoder encoder;

    private static final String NO_USER_WITH_THAT_ID_EXCEPTION = "noUserWithId";

    @Autowired
    public UserLogicService(UserDao userDao, RoleDao roleDao, UserValidator userValidator,
                            BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userValidator = userValidator;
        this.encoder = encoder;
    }

    @Override
    public User insert(User entity) {
        Optional<Role> roleUser = roleDao.getById(2L);
        roleUser.ifPresent(entity::addRoleToUser);
        String password = entity.getPassword();
        entity.setPassword(null);
        entity.setPassword(encoder.encode(password));
        Optional<User> insertedUser = userDao.insert(entity);
        if (!insertedUser.isPresent()){
            throw new CannotInsertEntityException("Cannot insert this user",CANNOT_INSERT_ENTITY_CODE);
        }
        return insertedUser.get();
    }

    @Override
    public User getById(long id) {
        userValidator.checkID(id);
        Optional<User> receivedUserById = userDao.getById(id);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException(NO_USER_WITH_THAT_ID_EXCEPTION,NO_SUCH_ENTITY_CODE);
        }
        return receivedUserById.get();
    }

    @Override
    public List<User> getAll(int pageSize, int pageNumber) {
        return userDao.getAll(pageSize,pageNumber);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = userDao.findByUserLogin(login).get();
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), mapToGrantedAuthorities(u.getRoles()));

    }

//        return userDao.findByUserLogin(login)
//                .map(user -> new org.springframework.security.core.userdetails.User(
//                        user.getLogin(),
//                        user.getPassword(),
//                        mapToGrantedAuthorities(user.getRoles())
//                ))
//                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + login));


    @Override
    public User findUserByUserLogin(String login) {
        Optional<User> receivedUserById = userDao.findByUserLogin(login);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException("Failed to retrieve user: ",NO_SUCH_ENTITY_CODE);
        }
        return receivedUserById.get();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getRoleName())
                ).collect(Collectors.toList());
    }

    @Override
    public void deleteByID(long id) {
        userValidator.checkID(id);
        Optional<User> receivedUserById = userDao.getById(id);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException("No user with that id exception",NO_SUCH_ENTITY_CODE);
        }
        userDao.delete(receivedUserById.get());
    }
}
