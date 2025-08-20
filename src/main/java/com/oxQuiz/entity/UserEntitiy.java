package com.oxQuiz.entity;

import com.oxQuiz.config.UserInitialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntitiy implements UserInitialize {
    @Id
    @Column(length = ID_SIZE, nullable = false)
    private String userId;

    @Column(length = PASSWORD_SIZE, nullable = false)
    private String password;

    @Column(length = NICK_NAME_SIZE)
    private String nickName;
    //valitation 널값 받지 않게 체크 @NotNull
    @Column(nullable = false)
    private Boolean userStatus;

    @PrePersist //mysql에 저장바로 직전에 Null이면 false로 값 변환후 저장
    private void init() {
        if (userStatus == null) {
            userStatus = DEFAULT_STATUS; // 저장 전 null 체크 후 초기화
        }
    }

}
