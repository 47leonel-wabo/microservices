package com.wbt.userservice.controller;

import com.wbt.userservice.dto.UserDto;
import com.wbt.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/users"})
public record UserController(UserService userService) {
    @GetMapping
    public Flux<UserDto> allUsers() {
        return this.userService.getAll();
    }

    @GetMapping(path = {"/{id}"})
    public Mono<ResponseEntity<UserDto>> getUserById(final @PathVariable(name = "id") Long id) {
        return this.userService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> addUser(final @RequestBody Mono<UserDto> dtoMono) {
        return this.userService.save(dtoMono);
    }

    @PutMapping(path = {"/{id}"})
    public Mono<ResponseEntity<UserDto>> update(final @PathVariable(name = "id") Long id, final @RequestBody Mono<UserDto> dtoMono) {
        return this.userService.update(id, dtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping(path = {"/{id}"})
    public Mono<ResponseEntity<Void>> delete(final @PathVariable(name = "id") Long id) {
        return this.userService.delete(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
