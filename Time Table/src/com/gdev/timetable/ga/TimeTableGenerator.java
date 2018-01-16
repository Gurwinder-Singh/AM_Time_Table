/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.ga;


import com.gdev.timetable.model.ConfigDetail;
import com.gdev.timetable.model.SubjectAllocation;
import java.util.Vector;

/**
 * Written by Gurwinder Singh
 *
 * @author Gurwinder Singh
 *
 */
public class TimeTableGenerator {

    private String _session;
    private Vector<SubjectAllocation> _allocations;
    public static final int POPULATION_SIZE = 10;
    public static final double MUTATION_RATE = 0.1;
    public static final double CROSSOVER_RATE = 0.9;
    public static final int TOURNAMENT_SELECTION_SIZE = 1;
    public static final int NUMB_OF_ELITE_SCHEDULES = 1;
    private int SCNUB = 0;

    public void setSession(String session) {
        _session = session;
    }

    public void setAllocation(Vector<SubjectAllocation> allocation) {
        _allocations = allocation;
    }

    public boolean generate() {
//        if (_session == null || _session.isEmpty()) {
//            throw new NullPointerException("Session not found");
//        }
//        if (_allocations == null || _allocations.isEmpty()) {
//            throw new NullPointerException("Allocation not found");
//        }
        return generateTimeTable();
    }

    private boolean generateTimeTable() {

        Data data = Data.getInstance();
        data.init();
        ConfigDetail config = new ConfigDetail();
        config.setId(1);
        config.setTotal_day(6);
        config.setTotal_lec(8);
        config.setBreak_after(4);
        config.setMax_generations(2000);
        int genNum = 0;

        GeneticAlgorithm ga = new GeneticAlgorithm(data, config);
        Population pop = new Population(POPULATION_SIZE, data, config).sortByFitness();
        pop.getSchedule().forEach(sc -> {
            System.out.println("    " + SCNUB++ + "    | " + sc + " | "
                    + String.format("%.5f", sc.getFitness()) + " | " + sc.getNumberOfConflicts()
            );
        });
//        alog.printAsTable(pop.getSchedule().get(0), genNum);
        while (pop.getSchedule().get(0).getFitness() != 1 && genNum < 2000) {
            ++genNum;
            pop = ga.evolve(pop).sortByFitness();
            SCNUB = 0;
            System.out.println("");
            System.out.println("Generation " + genNum);
            if(pop.getSchedule().get(0).getFitness() ==1 || genNum > 0){
            pop.getSchedule().stream().limit(1).forEach(sc -> {
                sc.sortFitness();
                System.out.println("    " + SCNUB++ + "    | " + sc + " | "
                        + String.format("%.5f", sc.getFitness()) + " | " + sc.getNumberOfConflicts()
                );

            });
            }
//            alog.printAsTable(pop.getSchedule().get(0), genNum);

        }
        return false;
    }

}
