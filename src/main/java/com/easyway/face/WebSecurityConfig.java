package com.easyway.face;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/ewface/login","/").permitAll()//任何人(包括没有经过验证的)都可以访问”/ewface/login”和”/”
                .antMatchers("/ewface/**").authenticated()
                .and()
                .formLogin()// POST提交到”/ewface/login”时，需要用”user”和”password”进行验证。
                .loginPage("/ewface/login")//登录页面 ,用GET访问”/ewface/login”时，显示登录页面
                .loginProcessingUrl("/ewface/login")//登录处理路径  
                .usernameParameter("username")//登录用户名参数  
                .passwordParameter("password")//登录密码参数  
                .defaultSuccessUrl("/ewface/menu")//登录成功路径                
                 .and()
                .logout()
                .permitAll()
               .and()
                .csrf().disable();
    }
   
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }   
    
    
    @Override  
    public void configure(WebSecurity web) throws Exception {  
           web.ignoring().antMatchers("/assets/**").antMatchers("/ewface/getPhoto/**"); 
    }  

}