package com.lizzy.kyle.springboot.web;


import com.lizzy.kyle.springboot.config.auth.LoginUser;
import com.lizzy.kyle.springboot.config.auth.dto.SessionUser;
import com.lizzy.kyle.springboot.domamin.user.User;
import com.lizzy.kyle.springboot.service.PostsService;
import com.lizzy.kyle.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

       //SessionUser user = (SessionUser) httpSession.getAttribute("user");  //기존 코드
        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }


    @GetMapping("/posts/save")
    public String postsSave(){
        return "post-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
