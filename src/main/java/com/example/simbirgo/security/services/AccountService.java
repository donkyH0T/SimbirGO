package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.ERole;
import com.example.simbirgo.entity.Role;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.payload.request.LoginRequest;
import com.example.simbirgo.payload.request.SignupRequest;
import com.example.simbirgo.payload.request.UpdateRequest;
import com.example.simbirgo.payload.response.JwtResponse;
import com.example.simbirgo.payload.response.MessageResponse;
import com.example.simbirgo.repository.RoleRepository;
import com.example.simbirgo.repository.UserRepository;
import com.example.simbirgo.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;




    public ResponseEntity<?> me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user=userRepository.findByUsername(currentUserName).get();
        return ResponseEntity.ok(user);
    }


    public ResponseEntity<?> signIn(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
    }


    public ResponseEntity<?> signUp(SignupRequest signUpRequest){
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
        Set<Role> roles=new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_USER).get());
        user.setRoles(roles);
        user.setBalance(0.0);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> signOut(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok(new MessageResponse("User signed out successfully!"));
    }

    public ResponseEntity<?> update(UpdateRequest updateRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userRepository.findByUsername(currentUserName).get();
        if(userRepository.existsByUsername(updateRequest.getUsername())){
            return ResponseEntity.badRequest().build();
        }
        user.setUsername(updateRequest.getUsername());
        user.setPassword(encoder.encode(updateRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }
}
