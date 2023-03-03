package com.example.numblebankingserverchallenge.config



import com.example.numblebankingserverchallenge.exception.CustomException
import com.example.numbletimedealserver.domain.Customer
import com.example.numbletimedealserver.dto.CustomerDto
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class SessionHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
         return CustomerDto::class.javaObjectType.isAssignableFrom(parameter.parameterType) && parameter.hasParameterAnnotation(
            SessionLoginChecker::class.java
        )
    }
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): CustomerDto {
        val request = webRequest.nativeRequest as? HttpServletRequest
        val member =
            request?.session?.getAttribute("user") as? CustomerDto ?: throw CustomException.NotLoggedInException()
        return member


    }
}