package com.whales.eplant.SecurityConfig;//package com.oracleous.extention_manager.securityConfiguration;
//
//import com.oracleous.extention_manager.data.model.UserPrincipal;
//import com.oracleous.extention_manager.data.model.Users;
//import com.oracleous.extention_manager.data.repositories.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class MyUserDetailsService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users users = userRepository.findByEmail(username).
//                orElseThrow(()-> new UsernameNotFoundException("user not found"));
//        return new UserPrincipal(users);
//    }
//}
