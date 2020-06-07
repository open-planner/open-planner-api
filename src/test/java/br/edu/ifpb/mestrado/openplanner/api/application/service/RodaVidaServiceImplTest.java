package br.edu.ifpb.mestrado.openplanner.api.application.service;

import static br.edu.ifpb.mestrado.openplanner.api.test.util.ServiceTestUtils.mockAdminAuth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

import br.edu.ifpb.mestrado.openplanner.api.domain.model.rodavida.RodaVida;
import br.edu.ifpb.mestrado.openplanner.api.domain.service.RodaVidaService;
import br.edu.ifpb.mestrado.openplanner.api.infrastructure.persistence.hibernate.repository.RodaVidaRepository;
import br.edu.ifpb.mestrado.openplanner.api.test.builder.RodaVidaBuilder;

@ActiveProfiles("test")
@SpringBootTest
public class RodaVidaServiceImplTest {

    private RodaVidaService rodaVidaService;

    private RodaVidaRepository rodaVidaRepository;

    @Autowired
    public RodaVidaServiceImplTest(RodaVidaService rodaVidaService, RodaVidaRepository rodaVidaRepository) {
        super();
        this.rodaVidaService = rodaVidaService;
        this.rodaVidaRepository = rodaVidaRepository;
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockAdminAuth();
    }

    @AfterEach
    public void tearDown() throws Exception {
        rodaVidaRepository.deleteAll();
    }

    @Test
    public void testFindByUsuarioAutenticado() {
        RodaVida rodaVida = rodaVidaService.findByUsuarioAutenticado();
        BigDecimal zero = BigDecimal.ZERO;

        assertNotNull(rodaVida.getId());
        assertEquals(rodaVida.getEspiritualidade(), zero);
        assertEquals(rodaVida.getEntretenimento(), zero);
        assertEquals(rodaVida.getDinheiro(), zero);
        assertEquals(rodaVida.getCarreira(), zero);
        assertEquals(rodaVida.getDesenvolvimentoPessoal(), zero);
        assertEquals(rodaVida.getRelacionamento(), zero);
        assertEquals(rodaVida.getSaude(), zero);
        assertEquals(rodaVida.getAmbiente(), zero);
    }

    @Test
    public void testUpdate() {
        RodaVida rodaVida = new RodaVidaBuilder()
                .withEspiritualidade(BigDecimal.valueOf(0.9))
                .withSaude(BigDecimal.valueOf(0.8))
                .withAmbiente(BigDecimal.valueOf(0.7))
                .build();
        Long id = rodaVidaService.findByUsuarioAutenticado().getId();
        RodaVida rodaVidaUpdated = rodaVidaService.update(id, rodaVida);

        assertEquals(rodaVidaUpdated.getId(), id);
        assertEquals(rodaVidaUpdated.getEspiritualidade(), rodaVida.getEspiritualidade());
        assertEquals(rodaVidaUpdated.getEntretenimento(), rodaVida.getEntretenimento());
        assertEquals(rodaVidaUpdated.getDinheiro(), rodaVida.getDinheiro());
        assertEquals(rodaVidaUpdated.getCarreira(), rodaVida.getCarreira());
        assertEquals(rodaVidaUpdated.getDesenvolvimentoPessoal(), rodaVida.getDesenvolvimentoPessoal());
        assertEquals(rodaVidaUpdated.getRelacionamento(), rodaVida.getRelacionamento());
        assertEquals(rodaVidaUpdated.getSaude(), rodaVida.getSaude());
        assertEquals(rodaVidaUpdated.getAmbiente(), rodaVida.getAmbiente());
    }

    @Test
    public void testUpdate_whenLessThanZero() {
        RodaVida rodaVida = new RodaVidaBuilder()
                .withSaude(BigDecimal.valueOf(-0.2))
                .build();
        Long id = rodaVidaService.findByUsuarioAutenticado().getId();

        assertThrows(TransactionSystemException.class, () -> rodaVidaService.update(id, rodaVida));
    }

    @Test
    public void testUpdate_whenGreaterThanOne() {
        RodaVida rodaVida = new RodaVidaBuilder()
                .withSaude(BigDecimal.valueOf(1.1))
                .build();
        Long id = rodaVidaService.findByUsuarioAutenticado().getId();

        assertThrows(TransactionSystemException.class, () -> rodaVidaService.update(id, rodaVida));
    }

    @Test
    public void testUpdate_whenGreaterThanTwoFractionalDigits() {
        RodaVida rodaVida = new RodaVidaBuilder()
                .withSaude(BigDecimal.valueOf(0.255))
                .build();
        Long id = rodaVidaService.findByUsuarioAutenticado().getId();

        assertThrows(TransactionSystemException.class, () -> rodaVidaService.update(id, rodaVida));
    }

}
