package com.xdstudios.usermanagementservice.repository;

import com.xdstudios.usermanagementservice.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    @Override
    User save(User user);

    @Override
    void deleteById(Long id);

    @Override
    Optional<User> findById(Long id);
}
