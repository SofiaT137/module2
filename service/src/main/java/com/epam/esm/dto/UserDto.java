package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserDto extends RepresentationModel<UserDto> {

    private Long id;
    private String firstName;
    private String lastName;

    public UserDto(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        if (!super.equals(o)) return false;
        UserDto userDto = (UserDto) o;
        return getId() == userDto.getId() && Objects.equals(getFirstName(), userDto.getFirstName())
                && Objects.equals(getLastName(), userDto.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
