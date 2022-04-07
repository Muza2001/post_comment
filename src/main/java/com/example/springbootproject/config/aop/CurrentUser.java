package com.example.springbootproject.config.aop;

/**
 * aop is - Aspect oriented programming
 */


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)         //
@Documented                                 // This Documented supports @ApiIgnore
@AuthenticationPrincipal
public @interface CurrentUser {
}
