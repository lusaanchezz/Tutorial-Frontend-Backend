package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long DELETE_LOAN_ID = 6L;
    public static final String CLIENT_NAME = "Fernando";

    public static final int TOTAL_LOANS = 6;
    public static final int PAGE_SIZE = 5;

    public static final String TITLE_GAME = "title game";
    public static final String CLIENT_ID_PARAM = "idClient";
    private static final LocalDate INIT_DATE = LocalDate.now();
    private static final LocalDate END_DATE = LocalDate.now().plusDays(3);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {
        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnFirstPage() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
        assertEquals(2, response.getBody().getTotalPages());
    }

    @Test
    public void findWithFilterGameShouldReturnCorrectLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(1L);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    public void findWithFilterClientShouldReturnCorrectLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(1L);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    public void findWithFilterDateShouldReturnCorrectLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setSearchDate(LocalDate.now());

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
    }

    @Test
    public void findWithFilterGameAndClientShouldReturnCorrectLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(1L);
        searchDto.setClientId(1L);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    public void findWithFilterGameAndDateShouldReturnCorrectLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(1L);
        searchDto.setSearchDate(LocalDate.now());

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    public void findWithFilterClientAndDateShouldReturnCorrectLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(1L);
        searchDto.setSearchDate(LocalDate.now());

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    public void findWithFilterGameClientAndDateShouldReturnCorrectLoan() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(3L);
        searchDto.setClientId(3L);
        searchDto.setSearchDate(LocalDate.of(2024, 12, 1));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoan() {
        long newLoanId = TOTAL_LOANS + 1;
        long newLoanSize = TOTAL_LOANS + 1;

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(6L);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(4L);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setInitDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(3));

        ResponseEntity<ResponsePage<LoanDto>> r = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), responseTypePage);
        assertEquals(HttpStatus.OK, r.getStatusCode());

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(newLoanSize, response.getBody().getTotalElements()); //Comprueba si se ha aumentado el número de préstamos guardados
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Más de 14 días de préstamo
     */
    @Test
    public void saveWithoutValidDatesShouldThrowException() {
        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);
        gameDto.setTitle(TITLE_GAME);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setName(CLIENT_NAME);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setInitDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(15));

        ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El número de días del préstamo no puede ser superior a 14 días", response.getBody());
    }

    @Test
    public void saveWithoutValidGameShouldThrowException() {
        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);
        gameDto.setTitle(TITLE_GAME);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(2L);
        clientDto.setName(CLIENT_NAME);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setInitDate(LocalDate.of(2024, 11, 25));
        dto.setEndDate(LocalDate.of(2024, 11, 27));

        ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class);

        assertEquals("Juego no disponible en estas fechas. Cambie de fechas o de juego", response.getBody());
    }

    @Test
    public void saveWithoutValidClientShouldThrowException() {
        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(5L);
        gameDto.setTitle(TITLE_GAME);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(2L);
        clientDto.setName(CLIENT_NAME);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setInitDate(LocalDate.of(2024, 11, 26));
        dto.setEndDate(LocalDate.of(2024, 12, 3));

        ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cliente con préstamo en esas fechas. Cambie las fechas.", response.getBody());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {
        long newLoanSize = TOTAL_LOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID, HttpMethod.DELETE, null, Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {
        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteLoanId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
