package com.epam.esm.service.logic_service;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class UserLogicService is implementation of interface UserService
 * The class presents service logic layer for User entity
 */
@Service("userLogicService")
public class UserLogicService implements UserService<User>, UserDetailsService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final BCryptPasswordEncoder encoder;

    private static final String NO_USER_WITH_THAT_ID_EXCEPTION = "noUserWithId";

    @Autowired
    public UserLogicService(UserDao userDao, RoleDao roleDao,
                            BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
    }

    @Override
    public User insert(User entity) {
        Optional<Role> roleUser = roleDao.findById(2L);
        roleUser.ifPresent(entity::addRoleToUser);
        String password = entity.getPassword();
        entity.setPassword(null);
        entity.setPassword(encoder.encode(password));
        return userDao.save(entity);
    }

    @Override
    public User getById(long id) {
        Optional<User> receivedUserById = userDao.findById(id);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException(NO_USER_WITH_THAT_ID_EXCEPTION);
        }
        return receivedUserById.get();
    }

    @Override
    public Page<User> getAll(int pageSize, int pageNumber) {
        return userDao.findAll(PageRequest.of(pageSize,pageNumber));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User foundedUser = findUserByUserLogin(login);
        return new org.springframework.security.core.userdetails.User(foundedUser.getLogin(),
                foundedUser.getPassword(), mapToGrantedAuthorities(foundedUser.getRoles()));
    }

    @Override
    public User findUserByUserLogin(String login) {
        Optional<User> receivedUserById = userDao.findByLogin(login);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException("Failed to retrieve user: ");
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
        Optional<User> receivedUserById = userDao.findById(id);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException("No user with that id exception");
        }
        userDao.delete(receivedUserById.get());
    }
}
