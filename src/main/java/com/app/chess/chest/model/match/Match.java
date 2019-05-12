package com.app.chess.chest.model.match;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String userOneUsername;
    private String userTwoUsername;
    private Boolean userOneReady;
    private Boolean userTwoReady;
    private Integer userOneMoves;
    private Integer userTwoMoves;
    private Long userOneRoundsTime;
    private Long userTwoRoundsTime;
    private Boolean showMatch;
    private Long whoWon;
    private Long startGameUser;
    private Integer matchTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tournament_id")
    @JsonIgnoreProperties(value = "matches", allowSetters = true)
    private Tournament tournament;

    @JsonIgnoreProperties(value = "matches", allowSetters = true)
    @ManyToMany(mappedBy = "matches")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users = new ArrayList<>();

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

    public Integer getUserOneMoves() { return userOneMoves; }

    public void setUserOneMoves(Integer userOneMoves) { this.userOneMoves = userOneMoves; }

    public Integer getUserTwoMoves() { return userTwoMoves; }

    public void setUserTwoMoves(Integer userTwoMoves) { this.userTwoMoves = userTwoMoves; }

    public Long getUserOneRoundsTime() { return userOneRoundsTime; }

    public void setUserOneRoundsTime(Long userOneRoundsTime) { this.userOneRoundsTime = userOneRoundsTime; }

    public Long getUserTwoRoundsTime() { return userTwoRoundsTime; }

    public void setUserTwoRoundsTime(Long userTwoRoundsTime) { this.userTwoRoundsTime = userTwoRoundsTime; }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUserOneUsername() {
        return userOneUsername;
    }

    public void setUserOneUsername(String userOneUsername) {
        this.userOneUsername = userOneUsername;
    }

    public String getUserTwoUsername() {
        return userTwoUsername;
    }

    public void setUserTwoUsername(String userTwoUsername) {
        this.userTwoUsername = userTwoUsername;
    }
}
