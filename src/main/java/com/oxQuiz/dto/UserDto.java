package com.oxQuiz.dto;

import com.oxQuiz.config.MessageInitialize;
import com.oxQuiz.config.UserInitialize;
import com.oxQuiz.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements UserInitialize, MessageInitialize {
    @NotNull
    @NotBlank(message = ID + REQUIRED_INPUT)
    @Size(max = ID_SIZE, message = ID + ID_SIZE + IMPOSSIBLE_LENGTH_EXCEEDED)
    private String userId;

    @NotBlank(message = PASSWORD + REQUIRED_INPUT)
    @Size(max = PASSWORD_SIZE, message = PASSWORD + PASSWORD_SIZE + IMPOSSIBLE_LENGTH_EXCEEDED)
    private String password;

    @Size(max = NICK_NAME_SIZE, message = NICK_NAME + NICK_NAME_SIZE + IMPOSSIBLE_LENGTH_EXCEEDED)
    private String nickName;

    private Boolean userStatus;

    public static UserDto fromEntity(UserEntity entity) {
        return new UserDto(
                entity.getUserId(),
                entity.getPassword(),
                entity.getNickName(),
                entity.getUserStatus()
        );
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static UserEntity toDto(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setUserId(dto.getUserId());
        entity.setPassword(dto.getPassword());
        entity.setNickName(dto.getNickName());
        entity.setUserStatus(dto.getUserStatus());
        return entity;
    }
}
