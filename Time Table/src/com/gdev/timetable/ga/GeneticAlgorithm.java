/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.ga;


import com.gdev.timetable.model.ConfigDetail;
import com.gdev.timetable.model.Lecture;
import com.gdev.timetable.model.TimeTableDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 *
 * @author Gurwinder Singh
 */
public class GeneticAlgorithm {

    private Data data;
    private ConfigDetail config;

    public GeneticAlgorithm(Data data, ConfigDetail config) {
        this.data = data;
        this.config = config;
    }

    public Population evolve(Population pop) {
        return mutatePopulation(crossOverPopulation(pop));
    }

    private String getKey(Schedule sc, int index) {
        return sc.hashCode() + ";" + index;
    }

    Population crossOverPopulation(Population pop) {
        Population co = new Population(pop.getSchedule().size(), data, config);
        IntStream.range(0, TimeTableGenerator.NUMB_OF_ELITE_SCHEDULES).forEach(x -> {
            Schedule sc = pop.getSchedule().get(x);
            sc.sortFitness();
            sc.getTimeTable().stream().filter(x1 -> sc.getTimeTableFitness(sc.getTimeTable().indexOf(x1)) != 1).forEach(y -> {
                y.stream().filter(a -> a.getError() > 0).forEach(a -> {
                    if (a.getError() == 2) {
                        Lecture lec = a.getLecture();
                        lec.setGroup1(data.getSpecialGen(config.getId()));
                        if (lec.getGroup1().getGroup() > 1) {
                            lec.setGroup2(data.getSpecialGen(config.getId()));
                        }
                        a.setLecture(lec);
                        a.setError(0);
                        y.set(y.indexOf(a), a);
                    } else {
                        ArrayList<TimeTableDetail> td1 = selectTournamentTimeTable(pop);
                        ArrayList<TimeTableDetail> td2 = selectTournamentTimeTable(pop);
                        y.set(y.indexOf(a), crossoverTimeTable(td1, td2).get(y.indexOf(a)));
                    }
                });
                sc.getTimeTable().set(sc.getTimeTable().indexOf(y), y);
//               
            });
            co.getSchedule().set(x, sc);
//            co.getSchedule().set(x, pop.getSchedule().get(x));
        });
        IntStream.range(TimeTableGenerator.NUMB_OF_ELITE_SCHEDULES, pop.getSchedule().size()).forEach(x -> {
            if (TimeTableGenerator.CROSSOVER_RATE > Math.random()) {
                Schedule sc1 = selectTournamentPopulation(pop).sortByFitness().getSchedule().get(0);
                Schedule sc2 = selectTournamentPopulation(pop).sortByFitness().getSchedule().get(0);
                co.getSchedule().set(x, crossoverSchedule(sc1, sc2));
            } else {
                co.getSchedule().set(x, pop.getSchedule().get(x));
            }
        });
        return co;
    }

    ArrayList<TimeTableDetail> crossoverTimeTable(ArrayList<TimeTableDetail> td1, ArrayList<TimeTableDetail> td2) {
        Schedule co = new Schedule(data, config).init();
        ArrayList<TimeTableDetail> ttd = co.getTimeTable().get((int) (co.getTimeTable().size() * Math.random()));
        IntStream.range(0, ttd.size()).forEach(x -> {
            if (Math.random() > 0.5) {
                TimeTableDetail d = td1.get(x);
                d.setError(0);
                ttd.set(x, d);
            } else {
                TimeTableDetail d = td2.get(x);
                d.setError(0);
                ttd.set(x, d);
            }
        });
        return ttd;
    }

    Schedule crossoverSchedule(Schedule sc1, Schedule sc2) {
        Schedule co = new Schedule(data, config).init();
        IntStream.range(0, co.getTimeTable().size()).forEach(x -> {
            if (Math.random() > 0.5) {
                co.getTimeTable().set(x, sc1.getTimeTable().get(x));

            } else {
                co.getTimeTable().set(x, sc2.getTimeTable().get(x));
            }

        });
        return co;
    }

    Population mutatePopulation(Population pop) {
        Population mp = new Population(pop.getSchedule().size(), data, config);
        ArrayList<Schedule> schedules = mp.getSchedule();
        IntStream.range(0, TimeTableGenerator.NUMB_OF_ELITE_SCHEDULES).forEach(x -> {
            schedules.set(x, pop.getSchedule().get(x));
        });
        IntStream.range(TimeTableGenerator.NUMB_OF_ELITE_SCHEDULES, pop.getSchedule().size()).forEach(x -> {
            schedules.set(x, mutateSchedule(pop.getSchedule().get(x)));
        });
        return mp;
    }

    Schedule mutateSchedule(Schedule schedule) {
        Schedule sc = new Schedule(data, config).init();
        IntStream.range(0, schedule.getTimeTable().size()).forEach(x -> {
            if (TimeTableGenerator.MUTATION_RATE > Math.random()) {
                schedule.getTimeTable().set(x, sc.getTimeTable().get(x));
            }
        });

        return schedule;
    }

    ArrayList<TimeTableDetail> selectTournamentTimeTable(Population pop) {
        Population tp = new Population(TimeTableGenerator.TOURNAMENT_SELECTION_SIZE, data, config);
        Schedule sc = selectTournamentPopulation(tp).sortByFitness().getSchedule().get(0);
        return sc.getTimeTable().get(0);
    }

    Population selectTournamentPopulation(Population pop) {
        Population tp = new Population(TimeTableGenerator.TOURNAMENT_SELECTION_SIZE, data, config);
        IntStream.range(0, TimeTableGenerator.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
            tp.getSchedule().set(x, pop.getSchedule().get((int) (Math.random() * pop.getSchedule().size())));

        });
        return tp;
    }
}
