package com.app.chess.chest.model.Tournament;

import com.app.chess.chest.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private TournamentStatus status;
    private Integer minValueOfRankValue;
    @NotNull
    private Integer maxNumberOfUser;
    private Integer NumberOfUser;
    private Long masterUser;

    @JsonIgnoreProperties(value = "tournaments", allowSetters = true)
    @ManyToMany(mappedBy = "tournaments")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }

    public Integer getMinValueOfRankValue() {
        return minValueOfRankValue;
    }

    public void setMinValueOfRankValue(Integer minValueOfRankValue) {
        this.minValueOfRankValue = minValueOfRankValue;
    }

    public Integer getMaxNumberOfUser() {
        return maxNumberOfUser;
    }

    public void setMaxNumberOfUser(Integer maxNumberOfUser) {
        this.maxNumberOfUser = maxNumberOfUser;
    }

    public Integer getNumberOfUser() {
        return NumberOfUser;
    }

    public void setNumberOfUser(Integer numberOfUser) {
        NumberOfUser = numberOfUser;
    }

    public Long getMasterUser() {
        return masterUser;
    }

    public void setMasterUser(Long masterUser) {
        this.masterUser = masterUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
