package com.wittypuppy.backend.auth.config;

import com.wittypuppy.backend.auth.filter.HeaderFilter;
import com.wittypuppy.backend.auth.interceptor.JwtTokenInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class LoginWebConfig implements WebMvcConfigurer {

//
////    // 정적 자원에 접근을 허용하게 하기 위함
//    @Value("${image.add-resource-locations}")
//    private String ADD_RESOURCE_LOCATION;
//
//    @Value("${image.add-resource-handler}")
//    private String ADD_RESOURCE_HANDLER;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler(ADD_RESOURCE_HANDLER)
//                .addResourceLocations(ADD_RESOURCE_LOCATION);
//    }

    @Bean(name = "filterRegistrationBean")
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean(){
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean(name = "headerFilter")
    public HeaderFilter createHeaderFilter(){
        return new HeaderFilter();
    }

    @Bean(name = "jwtTokenInterceptor")
    public JwtTokenInterceptor jwtTokenInterceptor(){
        return new JwtTokenInterceptor();
    }
}
