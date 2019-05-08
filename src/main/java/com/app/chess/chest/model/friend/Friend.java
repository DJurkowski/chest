package com.app.chess.chest.model.friend;

import com.app.chess.chest.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userOneId;
    private Long userTwoId;
    private Boolean userOneAccept;
    private Boolean userTwoAccept;
    private String userOneName;
    private String userTwoName;

    @ManyToMany(mappedBy = "friends")
    @JsonIgnoreProperties(value = "friends", allowSetters = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;

    public Friend(){}

    public Friend(Long userOneId, Long userTwoId, String userOneName, String userTwoName){
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
        this.userOneName = userOneName;
        this.userTwoName = userTwoName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(Long userOneId) {
        this.userOneId = userOneId;
    }

    public Long getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(Long userTwoId) {
        this.userTwoId = userTwoId;
    }

    public String getUserOneName() {
        return userOneName;
    }

    public void setUserOneName(String userOneName) {
        this.userOneName = userOneName;
    }

    public String getUserTwoName() {
        return userTwoName;
    }

    public void setUserTwoName(String userTwoName) {
        this.userTwoName = userTwoName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Boolean getUserOneAccept() {
        return userOneAccept;
    }

    public void setUserOneAccept(Boolean userOneAccept) {
        this.userOneAccept = userOneAccept;
    }

    public Boolean getUserTwoAccept() {
        return userTwoAccept;
    }

    public void setUserTwoAccept(Boolean userTwoAccept) {
        this.userTwoAccept = userTwoAccept;
    }
}
