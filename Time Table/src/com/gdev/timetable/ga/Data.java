/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.ga;

import com.gdev.timetable.model.ConfigDetail;
import com.gdev.timetable.model.SubjectAllocation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 *
 * @author Admin
 */
public class Data {

    private static Data _mInstance;
    private HashMap<Long, ArrayList<SubjectAllocation>> allocations;
    private Vector<ConfigDetail> _config;
    private HashMap<String, Vector<Integer>> teachers;

    public static Data getInstance() {
        if (_mInstance == null) {
            _mInstance = new Data();
        }
        return _mInstance;
    }

    public void init() {
        Vector v = new Vector();
        IntStream.range(0, 10).forEach(x -> {
            SubjectAllocation all = new SubjectAllocation();
            all.setConfig_id(1);
            all.setTeacher_id(x);
            all.setSubject_id(x);
//            if(x==6){
//                all.setLoad(8);
//                 all.setLength(2);
//                 all.setRepeat(2);
//            }else{
            all.setLoad(x == 3 || x == 1 ? 8 : 8);

            all.setLength(x == 3 || x == 1 ? 2 : 1);
            all.setRepeat(x == 3 || x == 1 ? 2 : 1);
//            }

            all.setGroup(x == 3 || x == 1 ? 2 : 1);

            all.setId(x);
            v.add(all);
        });
        setAllocation(v);

    }

    public void setConfig(Vector<ConfigDetail> config){
        _config = config;
    }
    
    public void setAllocation(Vector<SubjectAllocation> alloc) {
        allocations = new HashMap<>();
        alloc.forEach(x -> {
            ArrayList<SubjectAllocation> y = allocations.get(getConfig(x.getDep_id(), x.getBranch_id(), x.getSem()));
            if (y == null) {
                y = new ArrayList<>();
            }
            y.add(x);
            allocations.put(getConfig(x.getDep_id(), x.getBranch_id(), x.getSem()), y);
        });

    }

    public long getConfig(long dep, long branch, long sem) {
        return _config.stream().filter(c -> {
            return c.getDep_id() == dep && c.getBranch_id() == branch && c.getSem() == sem;
        }).flatMapToLong(c -> {
            return LongStream.of(c.getId());
        }).findFirst().orElse(0);
    }

    public ArrayList<SubjectAllocation> getAllocation(long config_id) {
        return this.allocations.get(config_id);
    }

    public SubjectAllocation getSpecialGen(long config_id) {
        if (this.allocations.get(config_id) == null) {
            throw new NullPointerException("Allocation not found");
        }
        ArrayList<SubjectAllocation> alloc = new ArrayList<>();
        allocations.get(config_id).stream().filter(x -> x.getLength() > 1).forEach(x -> {

            alloc.add(x);
        });

        return alloc.get((int) (alloc.size() * Math.random()));

    }

    public boolean isTeacherAval(long teacher_id, int day, int lecture) {
        return checkTeacherAval(teacher_id, day, lecture);
    }

    private boolean checkTeacherAval(long teacher_id, int day, int lecture) {
        if (teachers == null) {
            teachers = new HashMap<>();
        }

        Vector<Integer> lec = teachers.get(generateKey(teacher_id, day));
        if (lec == null) {
            return true;
        }
        return !lec.contains(lecture);
    }

    private String generateKey(long teacher_id, int day) {
        return teacher_id + ";" + day;
    }

    public void addTeacherAval(long teacher_id, int day, int lecture) {
        if (teachers == null) {
            teachers = new HashMap<>();
        }
        Vector<Integer> lec = teachers.get(generateKey(teacher_id, day));
        if (lec == null) {
            lec = new Vector<>();
        }
        lec.add(lecture);
        teachers.put(generateKey(teacher_id, day), lec);
    }

}
