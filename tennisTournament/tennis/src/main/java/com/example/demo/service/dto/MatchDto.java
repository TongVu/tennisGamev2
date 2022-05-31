package com.example.demo.service.dto;

import com.example.demo.entity.Player;
import com.example.demo.entity.Round;
import com.example.demo.entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    public class Match {

        private Integer id;
        private LocalDate startDate;
        private Integer duration;
        private Integer winnerId;
        private String player1Name;
        private String player2Name;
        private String roundName;
        private String stadiumName;
    }
}
