package com.app.chess.chest.security.scheduledTasks;

import com.app.chess.chest.model.Tournament.Tournament;
import com.app.chess.chest.model.Tournament.TournamentStatus;
import com.app.chess.chest.model.User;
import com.app.chess.chest.model.match.Match;
import com.app.chess.chest.security.services.Tournament.TournamentService;
import com.app.chess.chest.security.services.match.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final TournamentService tournamentService;
    private final MatchService matchService;

    public ScheduledTasks(TournamentService tournamentService, MatchService matchService) {
        this.tournamentService = tournamentService;
        this.matchService = matchService;
    }

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
        planingTournament(TournamentStatus.FULL);
    }

    public void planingTournament(TournamentStatus status) {

        List<Tournament> tournaments = tournamentService.getTournamentsByStatus(status);

        for (Tournament t : tournaments) {
            List<User> userList = t.getUsers();

            if(userList.size()%2 != 0){
                User oddUser = userList.remove(userList.size()-1);
                for(User user: userList){
                    matchService.createMatch(new Match(), t, oddUser.getId(), user.getId());
                }
            }

            int first = 0;
            int last = userList.size() - 1;

            for (int i = 0; i < userList.size() - 1; i++) {
                for (int j = 0; j < (userList.size() / 2); j++) {
                    matchService.createMatch(new Match(), t, userList.get(first).getId(), userList.get(last).getId());
                    first++;
                    last--;
                }
                first = 0;
                last = userList.size() - 1;
                Collections.rotate(userList, 1);
                Collections.swap(userList, 0, 1);

            }
        }

    }
}
