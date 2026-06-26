package com.cts.ecommerce.service;

import com.cts.ecommerce.entity.User;

public interface AuthService {

    User register(User user);

    String login(User user);

    String getRoleByUsername(String username);
}