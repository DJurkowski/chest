package com.app.chess.chest.repository;

import com.app.chess.chest.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUserAndRemove() {

        User dominik = new User("dominik", "domo@gmail.com", "123456");
        entityManager.persist(dominik);
        entityManager.flush();

        Optional<User> found = userRepository.findByUsername(dominik.getUsername());

        assertThat(found.get().getUsername())
                .isEqualTo(dominik.getUsername());

        entityManager.remove(dominik);
        flushAndClear();

        dominik = entityManager.find(User.class, dominik.getId());
        Assert.assertThat(dominik, nullValue());

    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        User fromDb = userRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

}