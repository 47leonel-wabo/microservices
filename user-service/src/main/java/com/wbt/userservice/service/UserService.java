package com.wbt.userservice.service;

import com.wbt.userservice.dto.UserDto;
import com.wbt.userservice.entity.User;
import com.wbt.userservice.repository.UserRepository;
import com.wbt.userservice.util.ConvertUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public record UserService(UserRepository userRepository) {

    public Flux<UserDto> getAll() {
        return this.userRepository.findAll().map(ConvertUtil::toUserDto);
    }

    public Mono<UserDto> getById(final Long userId) {
        return this.userRepository.findById(userId).map(ConvertUtil::toUserDto);
    }

    public Mono<UserDto> save(final Mono<UserDto> dtoMono) {
        return dtoMono.map(ConvertUtil::toUser)
                .flatMap(this.userRepository::save)
                .map(ConvertUtil::toUserDto);
    }

    public Mono<UserDto> update(final Long userId, final Mono<UserDto> userDtoMono) {
        return userDtoMono.flatMap(userDto -> this.userRepository.findById(userId)
                .map(user -> new User(userId, userDto.name(), userDto.balance()))
                .flatMap(this.userRepository::save)
                .map(ConvertUtil::toUserDto));
    }

    public Mono<Void> delete(final Long userId) {
        return this.userRepository.deleteById(userId);
    }
}
