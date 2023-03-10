package com.example.numbletimedealserver

import com.example.numbletimedealserver.config.CustomObjectMapper
import com.example.numbletimedealserver.config.SessionLoginHandlerMethodArgumentResolver
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.filter.HiddenHttpMethodFilter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@TestConfiguration
class MvcTestConfig :WebMvcConfigurer{

    @Bean
    fun objectMapper(): ObjectMapper = CustomObjectMapper()


}