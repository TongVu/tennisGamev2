package com.example.demo.service.dto;

import com.example.demo.entity.Match;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class SetTennisDto {
    private Integer id;
    private Integer player1Score;
    private Integer player2Score;
    private Integer setWinnderId;
    private Integer setNumber;
    private LocalDate startTime;
    private LocalDate endTime;
    private Match match;

    private String playerOneFirstName;

    private String playerTwoFirstName;

    private String stadiumName;
    private String roundName;
}
