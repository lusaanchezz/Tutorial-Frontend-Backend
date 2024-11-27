package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * @author Lucía Sánchez
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar un listado paginado y filtrado de {@link Loan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link LoanDto}
     */
    @Operation(summary = "Find Page", description = "Method that returns a filtered or not filtered page of Loans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDto> find(@RequestBody LoanSearchDto dto) {
        Page<Loan> page = this.loanService.find(dto);

        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }

    /**
     * Método para crear un {@link Loan}
     *
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save", description = "Method that saves a Loan")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void save(@RequestBody LoanDto dto) throws LoanException {
        this.loanService.save(dto);
    }

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entindad
     * @throws Exception si el id no existe lanza esta excepción
     */
    @Operation(summary = "Delete", description = "Method that deletes Loans")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.loanService.delete(id);
    }

    @ExceptionHandler(LoanException.class)
    public ResponseEntity<String> handleLoanException(LoanException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
