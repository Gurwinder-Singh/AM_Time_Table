/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.ga;

import com.gdev.timetable.db.DbManager;
import com.gdev.timetable.dialogs.TimeTableGenerateLoading;
import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.utility.Utility;
import com.gdev.timetable.model.ConfigDetail;
import com.gdev.timetable.model.Result;
import com.gdev.timetable.model.SubjectAllocation;
import com.gdev.timetable.model.TimeTableDetail;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 *
 * @author Gurwinder Singh
 *
 */
public class TimeTableGenerator {

    private Vector<SubjectAllocation> _allocations;
    private Vector<ConfigDetail> _config;
    private Vector timetables;
    public static final int POPULATION_SIZE = 10;
    public static final double MUTATION_RATE = 0.1;
    public static final double CROSSOVER_RATE = 0.9;
    public static final int TOURNAMENT_SELECTION_SIZE = 1;
    public static final int NUMB_OF_ELITE_SCHEDULES = 1;
    private int SCNUB = 0;

    public void setAllocation(Vector<SubjectAllocation> allocation) {
        _allocations = allocation;
    }

    public void setConfig(Vector<ConfigDetail> config) {
        _config = config;
    }

    public boolean generate() {
        if (_allocations == null || _allocations.isEmpty()) {
            //Show Message if Allocation not found
            MessageDisplay.showErrorDialog(null, "Allocation(s) not Found");
            return false;
        }
        if (_config == null || _config.isEmpty()) {
            //Show Message if Configuration not found
            MessageDisplay.showErrorDialog(null, "Configuration(s) not Found");
            return false;
        }
        timetables = new Vector();
        return generateTimeTable();
    }

    private boolean generateTimeTable() {

        Data data = new Data();
        data.setConfig(_config);
        data.setAllocation(_allocations);
        data.loadTeacherAval(DbManager.getDefault().getTeacherAllocatedDetail());
       
//        data.init(); //for testing

        // for testing
//        ConfigDetail config = new ConfigDetail();
//        config.setId(1);
//        config.setTotal_day(6);
//        config.setTotal_lec(8);
//        config.setBreak_after(4);
//        config.setMax_generations(2000);
        //
        //filter for allocation avalable 
        _config.stream().filter(x -> data.getAllocation(x.getId()) != null
                && TimeTableGenerateLoading.getDefault().isRunning()).forEach(config -> {
                    int genNum = 0;
                    GeneticAlgorithm ga = new GeneticAlgorithm(data, config);
                    Population pop = new Population(POPULATION_SIZE, data, config).sortByFitness();
                    pop.getSchedule().forEach(sc -> {
                        System.out.println("    " + SCNUB++ + "    | " + sc + " | "
                                + String.format("%.5f", sc.getFitness()) + " | " + sc.getNumberOfConflicts()
                        );
                    });
//        printAsTable(pop.getSchedule().get(0), genNum);
                    while (pop.getSchedule().get(0).getFitness() != 1 && genNum < config.getMax_generations()
                    && TimeTableGenerateLoading.getDefault().isRunning()) {
                        ++genNum;
                        pop = ga.evolve(pop).sortByFitness();
                        SCNUB = 0;
                        System.out.println("");
                        System.out.println("Generation " + genNum);
                        if (pop.getSchedule().get(0).getFitness() == 1 || genNum > 0) {
                            pop.getSchedule().stream().limit(1).forEach(sc -> {
                                sc.sortFitness();
                                System.out.println("    " + SCNUB++ + "    | " + sc + " | "
                                        + String.format("%.5f", sc.getFitness()) + " | " + sc.getNumberOfConflicts()
                                );

                            });
                        }
//          printAsTable(pop.getSchedule().get(0), genNum);

                    }
                    if (pop.getSchedule().get(0).getFitness() == 1) {
                        ArrayList<ArrayList<TimeTableDetail>> tt = pop.getSchedule().get(0).reArrangeOrder();
                        tt.forEach(x -> {
                            x.forEach(y -> {
                                y.setConfig_id(config.getId());
                                data.addTeacherAval(y.getLecture().getGroup1().getTeacher_id(), tt.indexOf(x), x.indexOf(y));
                                if (y.getLecture().getGroup2() != null) {
                                    data.addTeacherAval(y.getLecture().getGroup2().getTeacher_id(), tt.indexOf(x), x.indexOf(y));
                                }
                                timetables.add(y);
                            });
                        });
                    }
                });

        return saveTimeTable();
    }

    private boolean saveTimeTable() {
        if (!timetables.isEmpty() && TimeTableGenerateLoading.getDefault().isRunning()) {
            Result r = DbManager.getDefault().setTimeTable(timetables);
            if (r.isSuccess()) {
                MessageDisplay.showSuccessDialog(null, "Time Table Generated Succesfully", "Success");
            }
            return r.isSuccess();
        }
        return false;
    }
}
