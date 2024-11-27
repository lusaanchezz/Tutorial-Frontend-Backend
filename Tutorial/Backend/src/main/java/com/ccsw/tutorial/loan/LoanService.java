package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

public interface LoanService {

    /**
     * Recupera un {@link Loan} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Loan}
     */
    Loan get(Long id);

    /**
     * Método para recuperar un listado paginado que puede estar filtrado o no de {@link Loan}
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> find(LoanSearchDto dto);

    /**
     * Método para crear un {@link Loan}
     * @param dto datos de la entidad
     * @throws LoanException si no funciona correctamente
     */
    void save(LoanDto dto) throws LoanException;

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entidad
     * @throws LoanException si el id no existe, lanza la excepción
     */
    void delete(Long id) throws Exception;
}
