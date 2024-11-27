package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author Lucía Sánchez
 *
 */
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "init_date", nullable = false)
    private LocalDate init_date;

    @Column(name = "end_date", nullable = false)
    private LocalDate end_date;

    /**
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id new value of {@link #getId}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return gameName
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * @param game new value of {@link #getGame}
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return clientName
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * @param client new value of {@link #getClient}
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return initDate
     */
    public LocalDate getInitDate() {
        return this.init_date;
    }

    /**
     * @param init_date new value of {@link #getInitDate}
     */
    public void setInitDate(LocalDate init_date) {
        this.init_date = init_date;
    }

    /**
     * @return endDate
     */
    public LocalDate getEndDate() {
        return this.end_date;
    }

    /**
     * @param end_date new value of {@link #getEndDate}
     */
    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }
}
