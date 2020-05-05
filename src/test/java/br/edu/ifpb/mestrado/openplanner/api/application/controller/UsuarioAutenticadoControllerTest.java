package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties.OAuth2Properties;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.test.util.UsuarioTestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioAutenticadoControllerTest extends BaseControllerTest {

    private static final String BASE_PATH = "/me";

    @MockBean
    private UsuarioService usuarioServiceMock;

    private Usuario usuarioAdminMock;

    @Autowired
    public UsuarioAutenticadoControllerTest(OAuth2Properties oauth2Properties) {
        super(oauth2Properties);
    }

    @BeforeEach
    public void setUp() throws Exception {
        usuarioAdminMock = UsuarioTestUtils.createAdminMock();
    }

    @Test
    public void testFind() {
        when(usuarioServiceMock.getAutenticado()).thenReturn(usuarioAdminMock);

        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .when().get(buildUrl(BASE_PATH));

        response.then()
                .statusCode(HttpStatus.OK.value()).assertThat();

        assertUsuarioResponseTO(response, usuarioAdminMock);
    }

    @Test
    public void testFind_whenUnauthorized() {
        given()
                .when().get(buildUrl(BASE_PATH))
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value()).assertThat();
    }

    @Test
    public void testUpdate() {
        when(usuarioServiceMock.updateAutenticado(any())).thenReturn(usuarioAdminMock);

        String requestBody = "{\n" + 
                "        \"nome\": \"Administrador do Sistema\",\n" + 
                "        \"dataNascimento\": \"" + usuarioAdminMock.getDataNascimento() + "\",\n" + 
                "        \"email\": \"admin@email.com\"\n" + 
                "}";
        System.out.println(requestBody);
        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .contentType(ContentType.JSON)
                .body(requestBody)
                .then().log().ifValidationFails()
                .when().put(buildUrl(BASE_PATH));

        response.then()
                .statusCode(HttpStatus.OK.value()).assertThat();

        assertUsuarioResponseTO(response, usuarioAdminMock);
    }

    @Test
    public void testUpdateSenha() {
        when(usuarioServiceMock.updateSenhaAutenticado(any(), any())).thenReturn(usuarioAdminMock);

        String requestBody = "{\n" + 
                "        \"senhaAtual\": \"admin\",\n" + 
                "        \"senhaNova\": \"Aadmin@123\"\n" + 
                "}";
        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().patch(buildUrl(BASE_PATH, "senha"));

        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value()).assertThat();
    }

    private void assertUsuarioResponseTO(Response response, Usuario usuario) {
        UsuarioResponseTO usuarioResponseTO = response.then().extract().as(UsuarioResponseTO.class);

        UsuarioTestUtils.assertResponseTO(usuarioResponseTO, usuario);
    }

}
