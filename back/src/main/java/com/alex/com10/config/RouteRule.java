package com.alex.com10.config;

import java.util.List;

public class RouteRule {

    private final String pattern;
    private final List<String> roles;

    public RouteRule(String pattern, List<String> roles) {
        this.pattern = pattern;
        this.roles = roles;
    }

    public String getPattern() {
        return pattern;
    }

    public List<String> getRoles() {
        return roles;
    }
}
