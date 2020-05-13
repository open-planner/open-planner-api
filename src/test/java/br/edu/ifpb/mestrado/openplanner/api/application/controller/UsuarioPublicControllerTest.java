package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioPublicControllerTest extends BaseControllerTest {

    private static final String BASE_PATH = "/public/usuarios";

    @MockBean
    private UsuarioService usuarioServiceMock;

    @Test
    public void testCreate() {
        when(usuarioServiceMock.save(any())).thenReturn(new Usuario());

        String requestBody = "{\n" + 
                "  \"nome\": \"Test\",\n" + 
                "  \"dataNascimento\": \"" + LocalDate.now().minusYears(20) + "\",\n" + 
                "  \"email\": \"user.test@email.com\",\n" + 
                "  \"senha\": \"Pass@0987\"\n" + 
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post(buildUrl(BASE_PATH));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testActivate() {
        when(usuarioServiceMock.save(any())).thenReturn(new Usuario());
        
        String requestBody = "{\n" + 
                "  \"token\": \"1234-56-7890\"\n" + 
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post(buildUrl(BASE_PATH, "ativacao"));
        
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testUpdateSenhaByResetToken() {
        when(usuarioServiceMock.updateSenhaByResetToken(any(), any())).thenReturn(new Usuario());

        String requestBody = "{\n" + 
                "  \"token\": \"1234-56-7890\",\n" + 
                "  \"senha\": \"P@ss3210\"\n" + 
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().patch(buildUrl(BASE_PATH, "senha"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testRecoverSenha() {
        doNothing().when(usuarioServiceMock).recoverSenha(any());

        String requestBody = "{\n" + 
                "  \"email\": \"user.test@email.com\"\n" + 
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post(buildUrl(BASE_PATH, "recuperacao", "senha"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
