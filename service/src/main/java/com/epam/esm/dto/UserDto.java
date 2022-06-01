package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Class UserDto extends RepresentationModel and helps to create UserDto entity
 */
public class UserDto extends RepresentationModel<UserDto> {

    private Long id = 0L;
    @NotEmpty(message = "{loginCannotBeNull}")
    @Size(min = 4 ,max = 30,message = "{thisUserNameLengthIsForbidden}" )
    private String login;
    @NotEmpty(message = "{passwordCannotBeNull}")
    private String password;
    private boolean enabled;

    public UserDto(Long id,String login,String password,boolean enabled) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.enabled = enabled;
    }

    public UserDto(String login,String password,boolean enabled) {
        this.login = login;
        this.password = password;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
