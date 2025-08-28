package com.oxQuiz.controller;

import com.oxQuiz.config.SessionInitialize;
import com.oxQuiz.dto.QuizDto;
import com.oxQuiz.dto.UserDto;
import com.oxQuiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController implements SessionInitialize {
    private final UserService userService;

    @GetMapping("list")
    public String getUserList(Model model) {
        List<UserDto> userDtoList = userService.findAllUser();
        model.addAttribute("userList", userDtoList);
        return "/user/list";
    }

    @GetMapping("signup")
    public String getSignupUser(Model model) {
        UserDto userDto = new UserDto();
        userDto.setUserStatus(false);
        model.addAttribute("userDto", userDto);
        return "/user/signup";
    }

    @PostMapping("signup")
    public String postSignupUser(@Valid @ModelAttribute("userDto") UserDto dto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (dto.getUserId().equals("root")) {
            dto.setUserStatus(true);
        }

        UserDto userdto = new UserDto();
        model.addAttribute("userDto", userdto);
        userService.insertUser(dto);
        return "/user/login";
    }

    @GetMapping("login")
    public String getLogin(Model model) {
        UserDto userdto = new UserDto();
        model.addAttribute("userDto", userdto);
        return "/user/login";
    }

    @PostMapping("login")
    public String getLogin(@Valid @ModelAttribute("userDto") UserDto dto,
                           BindingResult bindingResult,
                           Model model,
                           HttpSession session) {
        System.out.println("폼에서 넘겨받은 정보 :" + dto);
        UserDto loginResult = userService.findUser(dto.getUserId());
        System.out.println("로그인 정보 :" + loginResult);
        if (ObjectUtils.isEmpty(loginResult)) {
            bindingResult.rejectValue("userId",
                    "error.userId",
                    "아이디가 존재하지 않습니다.");
            System.out.println("로그인 실패 : 비엇음");
            return "/user/login";
        } else if (loginResult.getPassword().equals(dto.getPassword())) {
            System.out.println("로그인 성공");
            session.setAttribute("loginId", dto.getUserId());
            session.setAttribute("nickName", loginResult.getNickName());
            session.setMaxInactiveInterval(HALF_HOUR);
            return "redirect:/";
        } else {
            bindingResult.rejectValue("password",
                    "error.password",
                    "비밀번호가 일치하지 않습니다.");
            System.out.println("로그인 실패 : 비밀번호 불일치");
            return "/user/login";
        }
    }
    @GetMapping("logout")
    public String userLogout(HttpSession session) {
        // 세션 무효화
        session.invalidate();
        return "redirect:/user/login";
    }
    @PostMapping("update")
    public String GetUserUpdate(@RequestParam("userId") String id,
                                Model model) {
        System.out.println("아이디값 " + id);
        UserDto userDto = userService.findUser(id);
        System.out.println(userDto);
        model.addAttribute("userDto", userDto);
        return "/user/update";
    }

    @PostMapping("updateUser")
    public String postUpdateQuiz(@Valid @ModelAttribute("userDto")UserDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("업데이트 에러남");
            return "/user/update";
        }
        System.out.println(dto);
        userService.updateUser(dto);
        return "/user/list";
    }

}
