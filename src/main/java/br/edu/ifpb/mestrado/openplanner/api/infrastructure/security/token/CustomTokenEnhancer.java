package br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.auth.UsuarioAuth;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.service.OAuth2UserDetailsService;

public class CustomTokenEnhancer implements TokenEnhancer {

    private OAuth2UserDetailsService userDetailsService;

    public CustomTokenEnhancer(OAuth2UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UsuarioAuth usuarioAuth = userDetailsService.getUsuarioAuth(authentication);

        Map<String, Object> info = new HashMap<>();
        info.put("nome", usuarioAuth.getUsuario().getNome());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }

}
