package com.example.suko.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "puzzle_constraints")
public class PuzzleConstraint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id", nullable = false, unique = true)
    private Puzzle puzzle;

    @Column(name = "circle_sum_1", nullable = false)
    private Integer circleSum1;

    @Column(name = "circle_sum_2", nullable = false)
    private Integer circleSum2;

    @Column(name = "circle_sum_3", nullable = false)
    private Integer circleSum3;

    @Column(name = "circle_sum_4", nullable = false)
    private Integer circleSum4;

    @Column(name = "color_zone_map", nullable = false, columnDefinition = "CLOB")
    private String colorZoneMap;

    @Column(name = "color_zone_sum_map", nullable = false, columnDefinition = "CLOB")
    private String colorZoneSumMap;

    @Column(name = "circle_cell_map", nullable = false, columnDefinition = "CLOB")
    private String circleCellMap;

    public Long getId() {
        return id;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public Integer getCircleSum1() {
        return circleSum1;
    }

    public Integer getCircleSum2() {
        return circleSum2;
    }

    public Integer getCircleSum3() {
        return circleSum3;
    }

    public Integer getCircleSum4() {
        return circleSum4;
    }

    public String getColorZoneMap() {
        return colorZoneMap;
    }

    public String getColorZoneSumMap() {
        return colorZoneSumMap;
    }

    public String getCircleCellMap() {
        return circleCellMap;
    }
}
