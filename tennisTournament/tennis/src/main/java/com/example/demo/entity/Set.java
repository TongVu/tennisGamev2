package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer player1Score;

    private Integer player2Score;



    @JoinColumn
    @ManyToOne
    private Match match;
    private Integer setNumber;
    private LocalDate startDate;
    private LocalDate endDate;

}
