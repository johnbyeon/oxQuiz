package com.oxQuiz.controller;

import com.oxQuiz.dto.QuizDto;
import com.oxQuiz.dto.QuizHistoryDto;
import com.oxQuiz.dto.QuizHistoryOutput;
import com.oxQuiz.dto.UserDto;
import com.oxQuiz.service.QuizHistoryService;
import com.oxQuiz.service.QuizService;
import com.oxQuiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {
    private final UserService userService;
    private final QuizService quizService;
    private final QuizHistoryService quizHistoryService;

    @GetMapping
    public String makeQuiz(Model model) {
        List<QuizDto> quizDtoList = quizService.findAllQuiz();
        model.addAttribute("questionList", quizDtoList);
        return "/quiz/list";
    }

    @GetMapping("insert")
    public String quizInsert(Model model, HttpSession session) {
        QuizDto quizDto = new QuizDto();
        //정답은 O로 시작하게
        quizDto.setAnswer(true);
        String userId = (String) session.getAttribute("loginId");
        UserDto uesrDto = userService.findUser(userId);
        quizDto.setWriter(uesrDto.getNickName());
        model.addAttribute("successMessage", "");
        model.addAttribute("dto", quizDto);
        return "/quiz/insert";
    }

    @PostMapping("insert")
    public String quizSave(@Valid @ModelAttribute("dto") QuizDto dto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "/quiz/insert";
        }
        System.out.println(dto);
        quizService.insertQuiz(dto);
        // 성공 메시지 추가
        model.addAttribute("successMessage", "퀴즈가 성공적으로 추가되었습니다!");
        // 새로운 퀴즈 DTO를 다시 제공하여 폼 초기화
        QuizDto newQuizDto = new QuizDto();
        newQuizDto.setAnswer(true);
        newQuizDto.setWriter(dto.getWriter());
        model.addAttribute("dto", newQuizDto);
        return "/quiz/insert";
    }

    @GetMapping("history")
    public String GetQuizHistory(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("loginId");
        List<QuizHistoryDto> quizHistoryDtoList = quizHistoryService.findAllUserId(userId);
        UserDto userDto = userService.findUser(userId);
        List<QuizHistoryOutput> historyOutputs = quizHistoryDtoList.stream()
                .map(history -> {
                    return QuizHistoryOutput.builder()
                            .id(history.getId())
                            .question(history.getQuestion())
                            .answer(history.getAnswer()? "O" : "X")
                            .writer(userDto.getNickName())
                            .isCorrect(history.getIsCorrect() ? "O" : "X")
                            .quizTime(history.getQuizTime())
                            .build();
                }).toList();

        model.addAttribute("historyOutput", historyOutputs);

        return "/quiz/history";
    }

    @GetMapping("play")
    public String getPlayQuiz(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("loginId");
        List<QuizDto> quizDtoList = quizService.findAllQuiz();
        /** 리스트c 가져온것중 문제를 랜덤으로 뽑는데 기존에 풀었던 문제는 제외하고 싶어요 */
        //기존문제 가져오기
        List<QuizHistoryDto> quizHistoryDtoList = quizHistoryService.findAllUserId(userId);

        // 푼 퀴즈 ID 목록 추출
        Set<Long> solvedQuizIds = quizHistoryDtoList.stream()
                .map(QuizHistoryDto::getQuestionId)
                .collect(Collectors.toSet());

        // 푼 퀴즈를 제외한 퀴즈 목록 필터링
        List<QuizDto> availableQuizzes = quizDtoList.stream()
                .filter(quiz -> !solvedQuizIds.contains(quiz.getId()))
                .toList();
        //퀴즈가 비어있다면 널로 바꿔서 전달
        if (availableQuizzes.isEmpty()) {
            model.addAttribute("dto", null);
            return "/quiz/play";
        }
        Random random = new Random();
        QuizDto randomQuiz = availableQuizzes.get(random.nextInt(availableQuizzes.size()));
        model.addAttribute("quiz", randomQuiz);
        return "/quiz/play";
    }

    @PostMapping("check")
    public String postQuizCheck(@RequestParam("answer") Boolean answer,
                                @RequestParam("id") Long id,
                                HttpSession session,
                                Model model) {
        QuizDto quizDto = quizService.findQuiz(id);
        String userId = (String) session.getAttribute("loginId");
        Timestamp quizTime = Timestamp.valueOf(LocalDateTime.now());
        QuizHistoryDto dto = QuizHistoryDto.builder()
                .userId(userId)
                .questionId(quizDto.getId())
                .question(quizDto.getQuestion())
                .answer(quizDto.getAnswer())
                .quizTime(quizTime)
                .build();
        if (quizDto.getAnswer().equals(answer)) {
            dto.setIsCorrect(true);
            model.addAttribute("checkTitle", "정답");
            model.addAttribute("checkComment", "정답입니다.");
        } else {
            dto.setIsCorrect(false);
            model.addAttribute("checkTitle", "오답");
            model.addAttribute("checkComment", "오답입니다.");
        }

        quizHistoryService.insertHistory(dto);
        return "/quiz/check";
    }

    @GetMapping("/{id}")
    public String getUpdateQuiz(@PathVariable("id") Long id,
                                Model model) {
        QuizDto dto = quizService.findQuiz(id);
        System.out.println(dto);
        model.addAttribute("dto", dto);
        return "/quiz/update";
    }

    @PostMapping("update")
    public String postUpdateQuiz(@Valid @ModelAttribute("dto") QuizDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("에러남");
            System.out.println(dto);
            return "/quiz/update";
        }
        System.out.println(dto);
        quizService.updateQuiz(dto);
        return "redirect:/quiz";
    }


    @PostMapping("delete")
    public String postDeleteQuiz(@RequestParam("id") Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz";
    }

}
