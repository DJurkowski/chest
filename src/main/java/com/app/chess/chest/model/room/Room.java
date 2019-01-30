package com.app.chess.chest.model.room;

import com.app.chess.chest.model.User;
import com.app.chess.chest.model.message.Message;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String user1Id;
    private String user2Id;

    @ManyToMany(mappedBy = "rooms")
    @JsonIgnoreProperties(value = "rooms", allowSetters = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "room", allowSetters = true)
    private Set<Message> messages = new TreeSet<>();

    public Room(){}

    public Room(String user1Id, String user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user1Id='" + user1Id + '\'' +
                ", user2Id='" + user2Id + '\'' +
                '}';
    }
}
