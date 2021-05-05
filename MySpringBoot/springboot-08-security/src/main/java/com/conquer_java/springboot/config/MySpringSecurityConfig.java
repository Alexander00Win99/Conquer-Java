package com.conquer_java.springboot.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 【官方文档自学思路】
 * 一） 编写自定义类MySpringSecurityConfig extends WebSecurityConfigurerAdapter；
 * 二） [ALT+Insert] -> [Override Methods] -> 选择一个configure()父类重载方法进行重写； —— 参考父类configure()和formLogin()方法；
 * 三） 类名_NativeMethodName，可以查看JVM本地方法（HotSpot底层使用C/C++编写的方法）；
 *
 * 【Cross Site Request Forgery - CSRF跨站请求伪造攻击】
 * 1) 开启CSRF保护功能，logout触发动作必须是POST方法；关闭CSRF保护功能，HTTP任何方法均可触发logout注销动作；
 * 2) logoutUrl("xxx")设置触发注销动作的URL地址，默认是"/logout"（开启CSRF就是POST /logout，否则GET /logout也能工作）；
 * 3) logoutSuccessUrl("xxx")设置成功注销以后跳转的页面；
 */
@EnableWebSecurity
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 【参考如下】——configure(AuthenticationManagerBuilder auth：
     * 	 * protected void configure(AuthenticationManagerBuilder auth) {
     * 	 * 	auth
     * 	 * 	// enable in memory based authentication with a user named
     * 	 * 	// &quot;user&quot; and &quot;admin&quot;
     * 	 * 	.inMemoryAuthentication().withUser(&quot;user&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;).and()
     * 	 * 			.withUser(&quot;admin&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;, &quot;ADMIN&quot;);
     * 	 * }
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth); // 参考super.configure()方法照猫画虎书写以下内容

        // SecurityControl-01：鉴权认证功能 —— 设定用户密码，分配用户角色
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("root").password(new BCryptPasswordEncoder().encode("999999")).roles("USER", "ADMIN", "ROOT")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("888888")).roles("USER", "ADMIN")
                .and()
                .withUser("normal").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("000000")).roles();
    }

    /**
     * 【参考如下】——WebSecurityConfigurerAdapter#configure(HttpSecurity http)：
     *  * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); // 参考super.configure()方法照猫画虎书写以下内容

        // SecurityControl-02：授权访问功能 —— 首页开放访问，功能页面限制访问
        // 效果Result-01：功能页面报错：“There was an unexpected error (type=Forbidden, status=403).”
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("USER")
                .antMatchers("/level2/**").hasRole("ADMIN")
                .antMatchers("/level3/**").hasRole("ROOT");

        // SecurityControl-02：授权访问功能 —— 若未登录：功能页面无权访问->自动跳转登录页面
        // 效果Result-02：自动跳转符合预期（开了跳转登录页面，不开报错"403 Forbidden"）
        // 注意：跳转页面是Spring Security内部自建页面/login而非用户自写/login页面，即使两者重名，也是前者覆盖后者
        http.formLogin().loginPage("/login-page")
                .loginProcessingUrl("/login").failureUrl("/auth/login?failed")
                .usernameParameter("myUsername").passwordParameter("myPassword");
        // 自定义跳转的登录页面，以此避免自建/login页面
        //http.formLogin().loginPage("/login-page");

        // 关闭CSRF（此处如若不关CSRF，要想实现注销功能，必须使用Form表单发送POST请求）
        http.csrf().disable();

        // 开启RememberMe功能
        http.rememberMe().rememberMeCookieName("Remember-U");

        // 开启注销功能（去活Session、删除Cookie、清理RememberMe、清理SecurityContextHolder等等，而非仅仅设定logout页面跳转）
        http.logout()
                .deleteCookies("Remember-U").invalidateHttpSession(true).clearAuthentication(true)
                .logoutUrl("/custom-logout").logoutSuccessUrl("/");
    }
}
