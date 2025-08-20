package com.oxQuiz.controller;

import com.oxQuiz.config.SessionInitialize;
import com.oxQuiz.dto.UserDto;
import com.oxQuiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController implements SessionInitialize {
    private final UserService userService;

    @GetMapping("signup")
    public String getSignupUser(Model model){
        UserDto userDto = new UserDto();
        userDto.setUserStatus(false);
        model.addAttribute("userDto", userDto);
        return "/user/signup";
    }

    @PostMapping("signup")
    public String postSignupUser(@Valid @ModelAttribute("userDto") UserDto dto,
                                 BindingResult bindingResult,
                                 Model model) {
        if(dto.getUserId().equals("root")){
            dto.setUserStatus(true);
        }

        UserDto userdto = new UserDto();
        model.addAttribute("userDto",userdto);
        userService.insertUser(dto);
        return "/user/login";
    }

    @GetMapping("login")
    public String getLogin(Model model) {
        UserDto userdto = new UserDto();
        model.addAttribute("userDto",userdto);
        return "/user/login";
    }
    @PostMapping("login")
    public String getLogin(@Valid @ModelAttribute("userDto")UserDto dto,
                           BindingResult bindingResult,
                           Model model,
                           HttpSession session) {
        System.out.println("폼에서 넘겨받은 정보 :" + dto);
        UserDto LoginResult = userService.findUser(dto.getUserId());
        System.out.println("로그인 정보 :" + LoginResult);
        if(ObjectUtils.isEmpty(LoginResult)) {
            // 사용자 정보가 없으면 로그인 실패
            bindingResult.rejectValue("userId", "error.userId", "아이디가 존재하지 않습니다.");
            System.out.println("로그인 실패 : 비엇음");
            return "/user/login";
        }else if(LoginResult.getPassword().equals(dto.getPassword())){
            if(LoginResult.getUserStatus() == true)
            {
                System.out.println("로그인 성공");
                // 세션열기
                session.setAttribute("loginId", dto.getUserId());
                session.setMaxInactiveInterval(HALF_HOUR);
                // 로그인 성공
                return "redirect:/";
            }else {
                bindingResult.rejectValue("userId", "error.userId", "이제는 더이상 물러날곳이 없다");
                return "/user/login";
            }

        } else {
            bindingResult.rejectValue("password", "error.password", "비밀번호가 일치하지 않습니다.");
            System.out.println("로그인 실패 : 비밀번호 불일치");
            // 비밀번호가 일치하지 않으면 로그인 실패
            return "/user/login";
        }

    }

}
