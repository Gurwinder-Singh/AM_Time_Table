/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.ga;

import com.gdev.timetable.model.ConfigDetail;
import com.gdev.timetable.model.Lecture;
import com.gdev.timetable.model.SubjectAllocation;
import com.gdev.timetable.model.TimeTableDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;
import org.openide.util.Exceptions;

/**
 *
 * @author Gurwinder Singh
 */
public class Schedule {

    private ArrayList<ArrayList<TimeTableDetail>> timeTable;
    private int days = 0;
    private boolean isFitnessChanged = true;
    private double fitness = -1;
    private int numberOfConflicts = 0;
    private int conflicts = 0;
    private Data data;
    private ConfigDetail config;
    int skipped = -1;
    int dailyLoad = 0;
    HashMap<Integer, Double> dailyFitness = new HashMap<>();

    public Schedule(Data data, ConfigDetail config) {
        this.data = data;
        this.config = config;
        if (config == null) {
            System.out.println("");
        }
        this.timeTable = new ArrayList<>((int) config.getTotal_day());
    }

    public Schedule init() {
//        new ArrayList<SubjectAllocation>(data.getAllocation(config.getId())).forEach(x -> {
        IntStream.range(0, (int) config.getTotal_day()).forEach(y -> {
            ArrayList<TimeTableDetail> det = new ArrayList<>();
            skipped = -1;
            IntStream.range(0, (int) config.getTotal_lec()).forEach(z -> {
                if (skipped >= z) {
                    try {
                        TimeTableDetail td = (TimeTableDetail) det.get(z - 1).clone();
                        td.setLec(z);
                        det.add(td);
                    } catch (CloneNotSupportedException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else {
                    skipped = -1;
                    TimeTableDetail detail = new TimeTableDetail();
                    detail.setId(z);
                    detail.setDay(y);
                    detail.setLec(z);
                    Lecture lec = new Lecture();
                    SubjectAllocation alloc = data.getAllocation(config.getId()).get((int) (data.getAllocation(config.getId()).size() * Math.random()));
                    if (alloc.getLength() > 1) {
                        skipped = z + (int) (alloc.getLength() - 1);
                    }
                    if (alloc.getGroup() == 2) {
                        SubjectAllocation alloc2 = data.getAllocation(config.getId()).get((int) (data.getAllocation(config.getId()).size() * Math.random()));
                        lec.setGroup2(alloc2);
                    }
                    lec.setGroup1(alloc);
                    detail.setLecture(lec);
                    det.add(detail);
                }
            });
            timeTable.add(det);
        });
//        });
        return this;
    }

    public int getNumberOfConflicts() {
        return numberOfConflicts;
    }

    public ArrayList<ArrayList<TimeTableDetail>> getTimeTable() {
        isFitnessChanged = true;
        return timeTable;
    }

    public ArrayList<ArrayList<TimeTableDetail>> reArrangeOrder() {
//        ArrayList<ArrayList<TimeTableDetail>> arrangeTimeTable = new ArrayList<>();
//        timeTable.forEach(d -> {
//            d.forEach(l -> {
//                l.setLec(days);
//            });
//        });

        IntStream.range(0, (int) config.getTotal_day()).forEach(d -> {
            IntStream.range(0, (int) config.getTotal_lec()).forEach(l -> {
                TimeTableDetail detail = timeTable.get(d).get(l);
                detail.setDay(d+1);
                detail.setLec(l+1);
                detail.setSub_alloc_id(detail.getLecture().getGroup1().getId());
                if (detail.getLecture().getGroup2() != null) {
                    detail.setSub_alloc_id2(detail.getLecture().getGroup2().getId());
                }
                detail.setConfig_id(config.getId());
//                detail.setSub_alloc_id(detau);
//                arrangeTimeTable.add(timeTable.get(d));
            });
        });
        return timeTable;
    }

    public void sortFitness() {
        fitness = calculateFitness();
        sortOverDailyFitness();
        isFitnessChanged = false;
    }

    public double getFitness() {
        if (isFitnessChanged) {
            fitness = calculateFitness();
            sortOverDailyFitness();
            isFitnessChanged = false;
        }
        return fitness;
    }

    //Used for Calculating Schedule Fitness
    private double calculateFitness() {
        numberOfConflicts = 0;
        conflicts = 0;
        skipped = -1;
        HashMap<Long, Long> load = new HashMap<>();
        clearAllErrorMarking();
        timeTable.forEach(x -> {
            conflicts = 0;
            skipped = -1;
//            System.out.print("--------");
//            System.out.print(timeTable.indexOf(x) +" ---- ");
            x.forEach(y -> {
//                y.setError(0);
                //check if teacher is avalable or not
                if (!data.isTeacherAval(y.getLecture().getGroup1().getTeacher_id(), timeTable.indexOf(x), x.indexOf(y))) {
                    conflicts++;
                    y.setError(1);
                }

                //add daily load
                load.put(y.getLecture().getGroup1().getId(), (load.get(y.getLecture().getGroup1().getId()) == null
                        ? 0 : load.get(y.getLecture().getGroup1().getId())) + y.getLecture().getGroup1().getLength());
                y.setLoad(load.get(y.getLecture().getGroup1().getId()));
//                System.out.print( y.getLecture().getGroup1().getId() + " LD "+load.get(y.getLecture().getGroup1().getId()) + " ");
                //if daily load
                if (load.get(y.getLecture().getGroup1().getId()) > y.getLecture().getGroup1().getLoad()) {
                    if (y.getLecture().getGroup1().getId() != 3 && y.getLecture().getGroup1().getId() != 1 && y.getLecture().getGroup1().getId() != 6) {
                        System.out.print("");
                    }
                    conflicts++;
                    y.setError(1);
                    y.setErrorReason("OverLoaded");
                }

                if (y.getLecture().getGroup1().getGroup() > 1 && y.getLecture().getGroup2() == null) {
                    conflicts++;
                    y.setError(3);
                    y.setErrorReason("no 2nd group");
                }
                if (y.getLecture().getGroup2() != null) {

                    if (!data.isTeacherAval(y.getLecture().getGroup2().getTeacher_id(), timeTable.indexOf(x), x.indexOf(y))) {
                        conflicts++;
                        y.setError(1);
                    }
                    // check 2nd group allow grouping
                    if (y.getLecture().getGroup2().getGroup() != 2) {
                        conflicts++;
                        y.setError(1);
                        y.setErrorReason("not Grouped");
                    }
                    load.put(y.getLecture().getGroup2().getId(), (load.get(y.getLecture().getGroup2().getId()) == null
                            ? 0 : load.get(y.getLecture().getGroup2().getId())) + y.getLecture().getGroup2().getLength());
                    if (load.get(y.getLecture().getGroup2().getId()) > y.getLecture().getGroup2().getLoad()) {
                        conflicts++;
                        y.setError(1);
                        y.setErrorReason("OverLoaded2");
                    }

                    //Group 1 and 2 can't be same
                    if (y.getLecture().getGroup2().getSubject_id() == y.getLecture().getGroup1().getSubject_id()) {
                        conflicts++;
                        if (y.getLecture().getGroup1().getId() == 6) {
                            System.out.print("");
                        }
                        y.setError(1);
                        y.setErrorReason("2 nd 1 same");
                    }
                }

                dailyLoad = 1;
//                skipped = -1;

                //if it a long entry (What is skipeed? -- if lecture length >1 then we mark it to be skipped)
                if (skipped >= x.indexOf(y)) {

                } else {
                    skipped = -1;
                    if (y.getLecture().getGroup1().getLength() > 1) {
                        //long lecture can't be last lecture of day
                        if (x.indexOf(y) == x.size() - 1) {
                            conflicts++;
                            y.setError(1);
                            y.setErrorReason("last entry");
                        }
                        //long lecture can't be in break;
                        if (x.indexOf(y) < config.getBreak_after() && (x.indexOf(y) + y.getLecture().getGroup1().getLength()) > config.getBreak_after()) {
                            conflicts++;
                            y.setError(1);
                            y.setErrorReason("break entry");
                        }

                        skipped = x.indexOf(y) + (int) (y.getLecture().getGroup1().getLength() - 1);
                    }
                }

                // check next lectures.
                x.stream().filter(z -> x.indexOf(z) > x.indexOf(y)).forEach(z -> {

                    // check for long entry
                    if (skipped >= x.indexOf(z)) {
                        //inc daily load
                        dailyLoad++;
                        //check long lecture is same (ex:- long lecture length 2 then lec1 and lec2 will be same)
                        if (y.getLecture().getGroup1().getId() != z.getLecture().getGroup1().getId()
                                || (y.getLecture().getGroup2() != null && y.getLecture().getGroup2().getId() != z.getLecture().getGroup2().getId())) {
                            //if it same but order is not same (Ex-> lec1(0,1) and lec2(1,0))  swipe it (result = lec2(0,1))
                            if (y.getLecture().getGroup2() != null && z.getLecture().getGroup2() != null
                                    && y.getLecture().getGroup1().getId() == z.getLecture().getGroup2().getId()
                                    && y.getLecture().getGroup2().getId() == z.getLecture().getGroup1().getId()) {
                                SubjectAllocation temp = z.getLecture().getGroup1();
                                z.getLecture().setGroup1(z.getLecture().getGroup2());
                                z.getLecture().setGroup2(temp);
                                return;
                            }
                            conflicts++;
                            if (y.getError() == 0) {
                                z.setError(2);
                                z.setErrorReason("Z not in length");
                            }
                        }
                        return;
                    }

                    //if it is not long lecture and next lecture is same as prev lecture
                    if (y.getLecture().getGroup1().getSubject_id() == z.getLecture().getGroup1().getSubject_id()) {
                        dailyLoad++;
                        //check how  many time it repeat in a day
                        if (dailyLoad > y.getLecture().getGroup1().getRepeat()) {
                            conflicts++;
                            z.setError(1);
                            //if and else just for log
                            if (z.getLecture().getGroup1().getLength() > 1) {
                                z.setErrorReason("daily OL");
                            } else {
                                z.setErrorReason("daily OL2");
                            }
                        }
                    }
//                        if (y.getLecture().getGroup1().getTeacher_id() == z.getLecture().getGroup1().getTeacher_id()
//                                ) {
//                            numberOfConflicts++;
//                        }

                    if (y.getLecture().getGroup2() != null) {

//                            if (y.getLecture().getGroup2().getTeacher_id() == z.getLecture().getGroup1().getTeacher_id()) {
//                                numberOfConflicts++;
//                            }
                        //Grouped subject has No repeat (If you want to implement repeats use same dailyLoad Concept here) 
                        if (z.getLecture().getGroup2() != null) {
                            if (y.getLecture().getGroup2().getSubject_id() == z.getLecture().getGroup2().getSubject_id()) {
                                conflicts++;
                                if (y.getLecture().getGroup1().getId() == 6) {
                                    System.out.print("");
                                }
                                z.setError(1);
                                z.setErrorReason("2 nd 2 same");
                            }
//                                if (y.getLecture().getGroup2().getTeacher_id() == z.getLecture().getGroup2().getTeacher_id()) {
//                                    numberOfConflicts++;
//                                }
                        }
                    }

//                    }
                });

            });
            dailyFitness.put(timeTable.indexOf(x), 1 / (double) (conflicts + 1));
            numberOfConflicts += conflicts;
        });
        return 1 / (double) (numberOfConflicts + 1);
    }

    @Override
    public String toString() {
        return timeTable.toString();

    }

    public double getTimeTableFitness(int index) {
        return getDialyFitness(index);
    }

    private double getDialyFitness(int index) {
        if (dailyFitness == null) {
            dailyFitness = new HashMap<>();
        }
        if (dailyFitness.get(index) == null) {
            return 1;
        }
        return dailyFitness.get(index);
    }

    private void sortOverDailyFitness() {
//        timeTable.sort((x, y) -> {
//            int returnValue = 0;
//            if (getDialyFitness(timeTable.indexOf(x)) > getDialyFitness(timeTable.indexOf(y))) {
//                returnValue = -1;
//            } else if (getDialyFitness(timeTable.indexOf(x)) < getDialyFitness(timeTable.indexOf(y))) {
//                returnValue = 1;
//            }
//            return returnValue;
//        });
    }

    private void clearAllErrorMarking() {
        timeTable.forEach(x -> {
            x.forEach(y -> {
                y.setError(0);
                y.setErrorReason(null);
            });
        });
    }

}
