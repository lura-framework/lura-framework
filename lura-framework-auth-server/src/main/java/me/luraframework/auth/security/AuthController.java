package me.luraframework.auth.security;

import lombok.RequiredArgsConstructor;
import me.luraframework.core.commons.JsonUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private String jwtKey = "securityKey";

    @PostMapping("login")
    public Object login(@RequestBody AuthUserDto authUserDto) {

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authUserDto.getUsername(), authUserDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        Jwt encode = JwtHelper.encode(JsonUtils.toStr(authentication.getPrincipal()), new MacSigner(jwtKey));
        return encode.getEncoded();
    }
}
