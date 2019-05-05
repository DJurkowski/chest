package com.app.chess.chest.model.match;

import com.app.chess.chest.model.Tournament.Tournament;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private MatchStatus status;
    private Long userOneId;
    private Long userTwoId;
    private Boolean userOneReady;
    private Boolean userTwoReady;
    private Boolean showMatch;
    private Long whoWon;
    private Long startGameUser;
    private Integer matchTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tournament_id")
    @JsonIgnoreProperties(value = "matches", allowSetters = true)
    private Tournament tournament;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Long getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(Long userOneId) {
        this.userOneId = userOneId;
    }

    public Long getuserTwoId() {
        return userTwoId;
    }

    public void setuserTwoId(Long userTwoId) {
        this.userTwoId = userTwoId;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWhoWon() {
        return whoWon;
    }

    public void setWhoWon(Long whoWon) {
        this.whoWon = whoWon;
    }

    public Boolean getShowMatch() {
        return showMatch;
    }

    public void setShowMatch(Boolean showMatch) {
        this.showMatch = showMatch;
    }

    public Boolean getUserOneReady() {
        return userOneReady;
    }

    public void setUserOneReady(Boolean userOneReady) {
        this.userOneReady = userOneReady;
    }

    public Boolean getUserTwoReady() {
        return userTwoReady;
    }

    public void setUserTwoReady(Boolean userTwoReady) {
        this.userTwoReady = userTwoReady;
    }

    public Long getStartGameUser() {
        return startGameUser;
    }

    public void setStartGameUser(Long startGameUser) {
        this.startGameUser = startGameUser;
    }

    public Integer getMatchTime() { return matchTime; }

    public void setMatchTime(Integer matchTime) { this.matchTime = matchTime; }
}
