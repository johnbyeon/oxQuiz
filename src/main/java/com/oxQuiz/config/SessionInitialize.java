package com.oxQuiz.config;
/** 세션과 관련된 시간 및 다양한 설정들을 저장합니다. */
public interface SessionInitialize {

    int ONE_HOUR = 60 * 60;
    /** 세션 유지 시간 설정 (30분) */
    int HALF_HOUR = 60 * 30;
    int ONE_MINUTE = 60;
    int ONE_SECOND = 1;
}
