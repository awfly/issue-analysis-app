package com.amgchv.redirect;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class RedirectHelper {

    public static String getPreviousLocation(RedirectAttributes redirectAttributes, String referer) {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().forEach(redirectAttributes::addAttribute);
        return components.getPath();
    }
}
