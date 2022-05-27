package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class UserDto extends RepresentationModel and helps to create UserDto entity
 */
public class UserDto extends RepresentationModel<UserDto> {

    private Long id = 0L;
    private String login;
    private String password;

    public UserDto(Long id,String login,String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public UserDto(String login,String password) {
        this.login = login;
        this.password = password;
    }

    public UserDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getLogin(), userDto.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLogin());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
