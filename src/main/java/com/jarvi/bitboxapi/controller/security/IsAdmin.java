package com.jarvi.bitboxapi.controller.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('USER_CREATE') or hasRole('USER_DELETE') or hasRole('USER_LIST') or hasRole('ITEM_DELETE')")
public @interface IsAdmin {
}
