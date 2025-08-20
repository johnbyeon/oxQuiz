package com.oxQuiz.service;


import com.oxQuiz.dto.UserDto;
import com.oxQuiz.entity.UserEntitiy;
import com.oxQuiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public List<UserDto> findAllUser(){
        return userRepository.findAllOrderByID()
                .stream()
                .map(UserDto::fromEntity)
                .toList();
    }
    public void insertUser(UserDto dto) {
        UserEntitiy entity = UserDto.toDto(dto);
        userRepository.save(entity);
    }

    public void updateUser(UserDto dto) {
        UserEntitiy entity = UserDto.toDto(dto);
        userRepository.save(entity);
    }

    public UserDto findUser(String UserId) {
        UserEntitiy entity = userRepository.findById(UserId).orElse(null);
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        return UserDto.fromEntity(entity);
    }

    public void deleteUser(String UserId) {
        UserEntitiy Entity = userRepository.findById(UserId).orElse(null);
        if(!ObjectUtils.isEmpty(Entity)){
            userRepository.delete(Entity);
        }
    }

}
