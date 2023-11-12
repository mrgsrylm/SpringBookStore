package io.github.mrgsrylm.store.util;

import io.github.mrgsrylm.store.model.enums.BeanScope;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * A component representing the identity of the currently authenticated user.
 * This class provides a method to retrieve the custom user details associated with the authenticated user.
 */
@Component
@Scope(value = BeanScope.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class Identity {
    /**
     * Retrieve the custom user details of the currently authenticated user.
     *
     * @return The CustomUserDetails object containing user details.
     */
    public CustomUserDetails getCustomUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return (CustomUserDetails) userDetails;
    }
}
