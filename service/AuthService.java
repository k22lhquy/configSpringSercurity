package com.last.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.last.dto.AuthResponse;
import com.last.dto.LoginRequest;
import com.last.dto.RegisterRequest;
import com.last.entity.Role;
import com.last.entity.User;
import com.last.repository.UserRepository;
import com.last.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;

	public String register(RegisterRequest request) {

		if (userRepository.existsByUsername(request.getUsername())) {
			return "Username đã tồn tại";
		}

		User user = new User();
		user.setUsername(request.getUsername());

		// BCrypt mã hóa mật khẩu
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setRole(Role.USER);
		userRepository.save(user);

		return "Đăng ký thành công";
	}

	public AuthResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

		String token = jwtService.generateToken(user);

		return new AuthResponse(token);

	}

}