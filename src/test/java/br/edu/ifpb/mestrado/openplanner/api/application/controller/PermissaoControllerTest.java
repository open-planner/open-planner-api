package br.edu.ifpb.mestrado.openplanner.api.application.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties.OAuth2Properties;
import br.edu.ifpb.mestrado.openplanner.api.domain.model.permissao.Permissao;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.PermissaoService;
import br.edu.ifpb.mestrado.openplanner.api.test.util.PermissaoTestUtils;
import io.restassured.response.Response;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PermissaoControllerTest extends BaseControllerTest {

    private static final String BASE_PATH = "/permissoes";

    @MockBean
    private PermissaoService permissaoServiceMock;

    private List<Permissao> permissaoListMock;

    private Permissao permissaoAdminMock;

    @Autowired
    public PermissaoControllerTest(OAuth2Properties oauth2Properties) {
        super(oauth2Properties);
    }

    @BeforeEach
    public void setUp() throws Exception {
        permissaoAdminMock = PermissaoTestUtils.createAdminMock();

        permissaoListMock = new ArrayList<>();
        permissaoListMock.add(permissaoAdminMock);
    }

    @Test
    public void testFindAll() {
        when(permissaoServiceMock.findAll()).thenReturn(permissaoListMock);

        Response response = given()
                .auth().oauth2(givenAccessTokenAsAdmin())
                .log().ifValidationFails()
                .when().get(buildUrl(BASE_PATH));

        response.then()
                .statusCode(HttpStatus.OK.value()).assertThat()
                .body("size()", equalTo(permissaoListMock.size()))
                .body("[0].id", equalTo(permissaoAdminMock.getId().intValue()))
                .body("[0].papel", equalTo(permissaoAdminMock.getPapel().name()))
                .body("[0].descricao", equalTo(permissaoAdminMock.getDescricao()))
                .body("[0].links.size()", equalTo(0));
    }

}
