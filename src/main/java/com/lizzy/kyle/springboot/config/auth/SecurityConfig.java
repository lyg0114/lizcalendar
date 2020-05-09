package com.lizzy.kyle.springboot.config.auth;

import com.lizzy.kyle.springboot.domamin.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화 시켜 준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

    //antMatchers()
    // 1.권한 관리 대상을 지정하는 옵션
    // 2.URL, HTTP 메소드별로 관리가 가능합니다.
    // 3."/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었음!
    // 4.POST 메소드 이면서 "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 허용


    /*
    * anyRequest() --> 설정된 값들 이외의 URL
    * 1. 여기서는 authenticated()을 추가하여 나머지 url들은 모두 인증된 사용자(로그인한 사용자!)들에게만 허용
    * */

    /*
    * logout().logoutSuccessUrl("/")
    * 1. logout() --> 로그아웃 기능에 대한 여러 설정의 진입점
    * 2. 로그아웃 성공 시 "/" 로 이동!
    * */

    /*
    * oauth2Login()
    * 1. OAuth2 로그인 기능에 대한 여러 설정의 진입점
    * 2. userInfoEndpoint --> OAuth2 로그인 성공 이후 사용자 정보를 가져올 대의 설정들을 담당!!
    * */


    /*
    * userService()
    * 1. 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
    * 2. 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
    * */



}
