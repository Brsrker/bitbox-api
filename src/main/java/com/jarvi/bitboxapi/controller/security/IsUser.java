package com.jarvi.bitboxapi.controller.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('ITEM_CREATE') or hasRole('ITEM_LIST') or hasRole('ITEM_UPDATE') or hasRole('ITEM_DEACTIVATE')")
public @interface IsUser {
}
