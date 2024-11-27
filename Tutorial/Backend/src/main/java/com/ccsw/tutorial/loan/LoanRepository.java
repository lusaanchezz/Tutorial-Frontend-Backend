package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Lucía Sánchez
 */
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    @Query("SELECT l FROM Loan l WHERE l.game.id = :gameId AND (" + "l.init_date BETWEEN :initDate AND :endDate OR " + "l.end_date BETWEEN :initDate AND :endDate OR " + "(l.init_date <= :initDate AND l.end_date >= :endDate))")
    List<Loan> findByGameIdAndDateRange(@Param("gameId") Long gameId, @Param("initDate") LocalDate initDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT l FROM Loan l WHERE l.client.id = :clientId AND (" + "l.init_date BETWEEN :initDate AND :endDate OR " + "l.end_date BETWEEN :initDate AND :endDate OR " + "(l.init_date <= :initDate AND l.end_date >= :endDate))")
    List<Loan> findByClientIdAndDateRange(@Param("clientId") Long clientId, @Param("initDate") LocalDate initDate, @Param("endDate") LocalDate endDate);
}
