package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties.OAuth2Properties;
import br.edu.ifpb.mestrado.openplanner.api.application.service.exception.InformationNotFoundException;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Senha;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.usuario.Usuario;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.UsuarioService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.specification.SpecificationFactory;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.security.util.BcryptUtils;
import br.edu.ifpb.mestrado.openplanner.api.presentation.dto.usuario.UsuarioResponseTO;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.UsuarioBuilder;
import br.edu.ifpb.mestrado.openplanner.api.test.util.ControllerTestUtils;
import br.edu.ifpb.mestrado.openplanner.api.test.util.UsuarioTestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest extends BaseControllerTest {

    private static final String BASE_PATH = "/usuarios";

    @MockBean
    private UsuarioService usuarioServiceMock;

    private SpecificationFactory<Usuario> specificationFactoryMock;

    private List<Usuario> usuarioListMock;

    private Page<Usuario> usuarioPageMock;

    private Usuario usuarioAdminMock;

    @Autowired
    public UsuarioControllerTest(OAuth2Properties oauth2Properties) {
        super(oauth2Properties);
    }

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() throws Exception {
        specificationFactoryMock = mock(SpecificationFactory.class);

        usuarioAdminMock = UsuarioTestUtils.createAdminMock();

        usuarioListMock = new ArrayList<>();
        usuarioListMock.add(usuarioAdminMock);

        for (Long i = 2L; i <= 8; i++) {
            usuarioListMock.add(new UsuarioBuilder()
                .withId(i)
                .withNome(RandomStringUtils.random(10))
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail(RandomStringUtils.random(10).toLowerCase() + "@email.com")
                .withPendente(false)
                .withBloqueado(false)
                .withSenha(new Senha(BcryptUtils.encode(UsuarioTestUtils.MOCK_SENHA_PREFIX + i)))
                .build());
        }

        usuarioPageMock = new PageImpl<>(usuarioListMock, PageRequest.of(0, 10), 8);

        when(specificationFactoryMock.create(any(), any(Class.class))).thenReturn(Specification.where(null));
    }

    @Test
    public void testFindAll() {
        when(usuarioServiceMock.findAll(any(), any())).thenReturn(usuarioPageMock);

        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .when().get(buildUrl(BASE_PATH));

        ControllerTestUtils.assertPage(response, 10, 0, 8, 1, 8);

        response.then()
                .statusCode(HttpStatus.OK.value()).assertThat()
                .body("content[0].id", equalTo(usuarioAdminMock.getId().intValue()))
                .body("content[0].nome", equalTo(usuarioAdminMock.getNome()))
                .body("content[0].email", equalTo(usuarioAdminMock.getEmail()))
                .body("content[0].pendente", equalTo(usuarioAdminMock.getPendente()))
                .body("content[0].bloqueado", equalTo(usuarioAdminMock.getBloqueado()))
                .body("content[0].links.size()", equalTo(2));
    }

    @Test
    public void testFindAll_whenUnauthorized() {
        given()
                .when().get(buildUrl(BASE_PATH))
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value()).assertThat();
    }

    @Test
    public void testFindById() {
        when(usuarioServiceMock.findById(any())).thenReturn(usuarioAdminMock);

        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .when().get(buildUrl(BASE_PATH, 1));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        assertUsuarioResponseTO(response, usuarioAdminMock);
    }

    @Test
    public void testFindById_whenNotFound() {
        when(usuarioServiceMock.findById(any())).thenThrow(InformationNotFoundException.class);

        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .when().get(buildUrl(BASE_PATH, 100));

        response.then()
                .statusCode(HttpStatus.NOT_FOUND.value()).assertThat();
    }

    @Test
    public void testUpdate() {
        Usuario usuarioMock = new UsuarioBuilder()
                .withId(21L)
                .withNome("Test")
                .withDataNascimento(LocalDate.now().minusYears(20))
                .withEmail("user.test@email.com")
                .withPendente(false)
                .withBloqueado(false)
                .withSenha(new Senha(BcryptUtils.encode(UsuarioTestUtils.MOCK_SENHA_PREFIX + "user.test@email.com")))
                .build();
        when(usuarioServiceMock.update(any(), any())).thenReturn(usuarioMock);

        String requestBody = "{\n" + 
                "  \"nome\": \"Test\",\n" + 
                "  \"dataNascimento\": \"" + LocalDate.now().minusYears(20) + "\",\n" + 
                "  \"email\": \"user.test@email.com\"\n" + 
                "}";
        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put(buildUrl(BASE_PATH, usuarioMock.getId().intValue()));

        response.then()
                .statusCode(HttpStatus.OK.value()).assertThat();

        assertUsuarioResponseTO(response, usuarioMock);
    }

    private void assertUsuarioResponseTO(Response response, Usuario usuario) {
        UsuarioResponseTO usuarioResponseTO = response.then().extract().as(UsuarioResponseTO.class);

        UsuarioTestUtils.assertResponseTO(usuarioResponseTO, usuario);
    }

}
