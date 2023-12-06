package com.wbt.userservice.util;

import com.wbt.userservice.dto.UserDto;
import com.wbt.userservice.entity.User;

public record ConvertUtil() {
    public static UserDto toUserDto(final User user) {
        return new UserDto(user.id(), user.name(), user.balance());
    }

    public static User toUser(final UserDto userDto) {
        return new User(userDto.id(), userDto.name(), userDto.balance());
    }

}
