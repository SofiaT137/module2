package com.epam.esm.service.logic_service;

import com.epam.esm.exceptions.NoPermissionException;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class UserLogicService is implementation of interface UserService
 * The class presents service logic layer for User entity
 */
@Service("userLogicService")
public class UserLogicService implements UserService<User>, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder encoder;

    private final Pagination<User> pagination;

    private static final String NO_USER_WITH_THAT_ID_EXCEPTION = "noUserWithId";

    @Autowired
    public UserLogicService(UserRepository userRepository, RoleRepository roleRepository,
                            BCryptPasswordEncoder encoder, Pagination<User> pagination) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.pagination = pagination;
    }

    @Override
    @Transactional
    public User insert(User entity) {
        Optional<Role> roleUser = roleRepository.findById(2L);
        roleUser.ifPresent(entity::addRoleToUser);
        String password = entity.getPassword();
        entity.setPassword(null);
        entity.setPassword(encoder.encode(password));
        return userRepository.save(entity);
    }

    @Override
    public User getById(long id) {
        Optional<User> receivedUserById = userRepository.findById(id);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException(NO_USER_WITH_THAT_ID_EXCEPTION);
        }
        return receivedUserById.get();
    }

    @Override
    public Page<User> getAll(int pageNumber, int pageSize) {
        return pagination.checkHasContent(userRepository.findAll(PageRequest.of(pageNumber,pageSize)));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User foundedUser = findUserByUserLogin(login);
        return new org.springframework.security.core.userdetails.User(foundedUser.getLogin(),
                foundedUser.getPassword(), mapToGrantedAuthorities(foundedUser.getRoles()));
    }

    @Override
    public User findUserByUserLogin(String login) {
        Optional<User> receivedUserById = userRepository.findUserByLogin(login);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException("Failed to retrieve user: ");
        }
        return receivedUserById.get();
    }

    @Override
    @Transactional
    public User blockUser(String login) {
       User userForBlock = findUserByUserLogin(login);
       Optional<User> user = userRepository.blockUser(userForBlock);
       if (!user.isPresent()){
           throw new NoPermissionException("Cannot block this user");
       }
       return user.get();
    }

    @Override
    @Transactional
    public User unblockUser(String login) {
        User userForUnblock = findUserByUserLogin(login);
        Optional<User> user = userRepository.unblockUser(userForUnblock);
        if (!user.isPresent()){
            throw new NoPermissionException("Cannot unblock this user");
        }
        return user.get();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
