package com.oxQuiz.controller;

import com.oxQuiz.dto.QuizDto;
import com.oxQuiz.dto.QuizHistoryDto;
import com.oxQuiz.service.QuizHistoryService;
import com.oxQuiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final QuizHistoryService quizHistoryService;
    @GetMapping
    public String makeQuiz(Model model){
        List<QuizDto> quizDtoList = quizService.findAllQuiz();
        model.addAttribute("questionList",quizDtoList);
        return "/quiz/list";
    }
    @GetMapping("insert")
    public String quizInsert(Model model){
        QuizDto quizDto = new QuizDto();
        quizDto.setAnswer(true);
        model.addAttribute("dto", quizDto);
        return "/quiz/insert";
    }

    @PostMapping("insert")
    public String quizSave(@Valid @ModelAttribute("dto")QuizDto dto,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/quiz/insert";
        }
        System.out.println(dto);
        quizService.insertQuiz(dto);
        return "redirect:/quiz/insert";
    }

    @GetMapping("play")
    public String getPlayQuiz(Model model,HttpSession session){
        String userId = (String) session.getAttribute("loginId");
        List<QuizDto> quizDtoList = quizService.findAllQuiz();
        /** 리스트c 가져온것중 문제를 랜덤으로 뽑는데 기존에 풀었던 문제는 제외하고 싶어요 */
        //기존문제 가져오기
        List<QuizHistoryDto> quizHistoryDtoList = quizHistoryService.findAllUserId(userId);
        //
        // 푼 퀴즈 ID 목록 추출
        Set<Long> solvedQuizIds = quizHistoryDtoList.stream()
                .map(QuizHistoryDto::getQuizID) // QuizHistoryDto에 quizId 필드가 있다고 가정
                .collect(Collectors.toSet());

        // 푼 퀴즈를 제외한 퀴즈 목록 필터링
        List<QuizDto> availableQuizzes = quizDtoList.stream()
                .filter(quiz -> !solvedQuizIds.contains(quiz.getId()))
                .toList();

        if (availableQuizzes.isEmpty()) {
            model.addAttribute("dto", null);
            return "/quiz/play";
        }
        Random random = new Random();
        QuizDto randomQuiz = availableQuizzes.get(random.nextInt(availableQuizzes.size()));
        model.addAttribute("quiz", randomQuiz);
        return "/quiz/play";
    }

    @GetMapping("/{id}")
    public String getUpdateQuiz(@PathVariable("id")Long id,
            Model model){
        QuizDto dto = quizService.findQuiz(id);
        System.out.println(dto);
        model.addAttribute("dto",dto);
        return "/quiz/update";
    }

    @PostMapping("update")
    public String postUpdateQuiz(@Valid @ModelAttribute("dto")QuizDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/quiz/update";
        }
        System.out.println(dto);
        quizService.updateQuiz(dto);
        return "redirect:/quiz/list";
    }

    @PostMapping("delete")
    public String postDeleteQuiz(@RequestParam("id")Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz";
    }

}
