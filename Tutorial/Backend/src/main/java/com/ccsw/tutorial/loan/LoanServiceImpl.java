package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.exceptions.TutorialException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author Lucía Sánchez
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> find(LoanSearchDto dto) {
        Pageable pageable = PageRequest.of(dto.getPageable().getPageNumber(), dto.getPageable().getPageSize());
        Long idGame = dto.getGameId();
        Long idClient = dto.getClientId();
        LocalDate date = dto.getSearchDate();

        Specification<Loan> specification = Specification.where(null);

        if (idGame != null) {
            LoanSpecification gameSpecification = new LoanSpecification(new SearchCriteria("game.id", ":", idGame));
            specification = Specification.where(specification).and(gameSpecification);
        }
        if (idClient != null) {
            LoanSpecification clientSpecification = new LoanSpecification(new SearchCriteria("client.id", ":", idClient));
            specification = Specification.where(specification).and(clientSpecification);
        }
        if (date != null) {
            LoanSpecification initDateSpecification = new LoanSpecification(new SearchCriteria("init_date", "<=", date));
            specification = Specification.where(specification).and(initDateSpecification);

            LoanSpecification endDateSpecification = new LoanSpecification(new SearchCriteria("end_date", ">=", date));
            specification = Specification.where(specification).and(endDateSpecification);
        }

        return this.loanRepository.findAll(specification, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(LoanDto data) throws LoanException {
        Loan loan = new Loan();

        BeanUtils.copyProperties(data, loan, "id", "game", "client");

        try {
            loan.setGame(gameService.get(data.getGame().getId()));
            loan.setClient(clientService.get(data.getClient().getId()));
        } catch (Exception e) {
            throw new TutorialException("Error al mapear los datos de LoanDto a Loan: " + e.getMessage());
        }

        loan.setInitDate(data.getInitDate());
        loan.setEndDate(data.getEndDate());

        if (loan.getEndDate().isBefore(loan.getInitDate())) {
            throw new LoanException("La fecha de fin del préstamo no pueder ser anterior a la de inicio");
        }
        if (loan.getInitDate().plusDays(14).isBefore(loan.getEndDate())) {
            throw new LoanException("El número de días del préstamo no puede ser superior a 14 días");
        }

        if (!loanRepository.findByGameIdAndDateRange(loan.getGame().getId(), loan.getInitDate(), loan.getEndDate()).isEmpty()) {
            throw new LoanException("Juego no disponible en estas fechas. Cambie de fechas o de juego");
        }
        if (!loanRepository.findByClientIdAndDateRange(loan.getClient().getId(), loan.getInitDate(), loan.getEndDate()).isEmpty()) {
            throw new LoanException("Cliente con préstamo en esas fechas. Cambie las fechas.");
        }
        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.loanRepository.findById(id).orElse(null) == null) {
            throw new Exception("The Loan with id: " + id + "doesn't exist");
        }
        this.loanRepository.deleteById(id);
    }
}
