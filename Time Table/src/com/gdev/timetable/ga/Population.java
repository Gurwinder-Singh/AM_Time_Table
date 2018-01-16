/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.ga;


import com.gdev.timetable.model.ConfigDetail;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author Admin
 */
public class Population {

    private ArrayList<Schedule> schedules;

    public Population(int size, Data data, ConfigDetail config) {
        schedules = new ArrayList<Schedule>(size);
        IntStream.range(0, size).forEach(x -> schedules.add(new Schedule(data, config).init()));
    }

    public ArrayList<Schedule> getSchedule() {
        return this.schedules;
    }

    public Population sortByFitness() {
        schedules.sort((schedule1, schedule2) -> {
            int returnValue = 0;
            if (schedule1.getFitness() > schedule2.getFitness()) {
                returnValue = -1;
            } else if (schedule1.getFitness() < schedule2.getFitness()) {
                returnValue = 1;
            }
            return returnValue;
        });
        return this;
    }
}
