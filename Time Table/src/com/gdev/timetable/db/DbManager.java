/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.db;

import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.model.ConfigDetail;
import com.gdev.timetable.model.IdName;
import com.gdev.timetable.model.Result;
import com.gdev.timetable.model.SubjectAllocation;
import com.gdev.timetable.model.SubjectDetail;
import com.gdev.timetable.model.TeacherDetail;
import com.gdev.timetable.model.TimeTableDetail;
import com.gdev.timetable.utility.CryptoUtils;
import com.gdev.timetable.utility.FileUtility;
import com.gdev.timetable.utility.Utility;
import com.gdev.timetable.utility.ZipUtils;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 *
 * @author Admin
 */
public class DbManager {

    private final String dbUsername = "timetablesystem";
    private final String dbPassword = "5Hihd$bj8d";
    private final String path = System.getProperty("netbeans.user");
    private static DbManager minstance = null;

    public static DbManager getDefault() {
        if (minstance == null) {
            minstance = new DbManager();
        }
        return minstance;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        return DriverManager.getConnection("jdbc:derby:" + path + "/data/;create=true", dbUsername, dbPassword);

    }

    private void sendExceptionLog(Exception e, String message) {

        MessageDisplay.showErrorDialog(null, "Invalid State Occured \n  Connect with Vendor");
    }

    private void handelException(Exception e, Connection con) {
        sendExceptionLog(e, "");
        e.printStackTrace();
        try {
            con.rollback();
            con.close();
            if (con != null && e instanceof SQLException) {
                con.rollback();
                con.close();
            }
        } catch (Exception ex) {
            e.printStackTrace();
            sendExceptionLog(e, "Error on Closing Connection");
        }
    }

    public void createDbIfNotExists() {
        Connection con = null;
        try {
            con = getConnection();
            DatabaseMetaData data = con.getMetaData();
            ResultSet rs = data.getTables(null, null, "USERS", null);
            if (!rs.next()) {
                ScriptRunner r = new ScriptRunner(con, false, false);
                Reader rd = new InputStreamReader(this.getClass().getResourceAsStream("query.sql"));
                r.runScript(rd);
                con.commit();
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
    }

    public boolean checkLogin(String username, String password) {
        Connection con = null;
        PreparedStatement pst = null;
        String query;
        ResultSet rs = null;
        try {
            con = getConnection();
            query = "select * from users where username = ? and password = ? and active='Y'";
            pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                return true;
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return false;
    }

    public boolean updatePassword(String oldPass, String password) {
        Connection con = null;
        PreparedStatement pst = null;
        String query;
        ResultSet rs = null;
        try {
            con = getConnection();
            query = "update users set password = ? where password = ? and active='Y'";
            pst = con.prepareStatement(query);
            pst.setString(1, password);
            pst.setString(2, oldPass);
            int i = pst.executeUpdate();
            if (i <= 0) {
                con.close();
                return false;
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return true;
    }

    public Result addDepartment(IdName in) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from DEPARTMENTS where name = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Department Duplicated");
                return r;
            }
            query = "insert into departments(name) values (?)";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result modifyDepartment(IdName in) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Result r = new Result();
        String query;
        try {
            con = getConnection();
            query = "select * from DEPARTMENTS where name = ? and id != ?";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            pst.setLong(2, in.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Department Duplicated");
                return r;
            }
            query = "update departments set name = ? where id = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            pst.setLong(2, in.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result deleteDepartment(IdName in) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Result r = new Result();
        String query;
        try {
            con = getConnection();
            query = "select 'Subject' as val from SUBJECTS where  DEP_ID = ?"
                    + " union all select 'Subject Allocation' as val from SUB_ALLOC where  DEP_ID = " + in.getId()
                    + " union all select 'Configurations' as val from CONFIG where DEP_ID = " + in.getId();
            pst = con.prepareStatement(query);
            pst.setLong(1, in.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Department Used in " + rs.getString("val"));
                return r;
            }
            query = "delete from departments  where id = ? ";
            pst = con.prepareStatement(query);
            pst.setLong(1, in.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result addBranch(IdName in) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from BARANCHS where name = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Baranch Name Duplicated");
                return r;
            }
            query = "insert into baranchs(name) values (?)";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result modifyBranch(IdName in) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Result r = new Result();
        String query;
        try {
            con = getConnection();
            query = "select * from BARANCHS where name = ? and id != ?";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            pst.setLong(2, in.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Baranch Name Duplicated");
                return r;
            }
            query = "update baranchs set name = ? where id = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, in.getName());
            pst.setLong(2, in.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result deleteBranch(IdName in) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Result r = new Result();
        String query;
        try {
            con = getConnection();
            query = "select 'Subject' as val from SUBJECTS where  BRANCH_ID = ?"
                    + " union all select 'Subject Allocation' as val from SUB_ALLOC where  BRANCH_ID = " + in.getId()
                    + " union all select 'Configurations' as val from CONFIG where BRANCH_ID = " + in.getId();
            pst = con.prepareStatement(query);
            pst.setLong(1, in.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Department Used in " + rs.getString("val"));
                return r;
            }
            query = "delete from baranchs  where id = ? ";
            pst = con.prepareStatement(query);
            pst.setLong(1, in.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Vector getDepartments() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select * from departments";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                IdName in = new IdName();
                in.setId(rs.getLong("id"));
                in.setName(rs.getString("name"));
                v.add(in);

            }

            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    public Vector getBranchs() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select * from baranchs";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                IdName in = new IdName();
                in.setId(rs.getLong("id"));
                in.setName(rs.getString("name"));
                v.add(in);

            }

            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    public Result addTeacher(TeacherDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from TEACHER where lower(TEACH_NO) = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getTeach_no().toLowerCase());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Teacher No Duplicated");
                return r;
            }

            query = "insert into TEACHER(name, teach_no) values (?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getName());
            pst.setString(2, detail.getTeach_no());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result modifyTeacher(TeacherDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from TEACHER where lower(teach_no) = ? and id !=  ?";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getTeach_no().toLowerCase());
            pst.setLong(2, detail.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Teacher No Duplicated");
                return r;
            }

            query = "update TEACHER set name =? , teach_no = ? where id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getName());
            pst.setString(2, detail.getTeach_no());
            pst.setLong(3, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result deleteTeacher(TeacherDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from SUB_ALLOC where TEACH_ID = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Teacher can't be deleted. \n Due to used in Subject Allocation");
                return r;
            }
            query = " delete from TEACHER where id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Vector getTeacher() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select * from TEACHER";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                TeacherDetail detail = new TeacherDetail();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name"));
                detail.setTeach_no(rs.getString("teach_no"));
                v.add(detail);
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    //Not Used
    @Deprecated
    public Result addSubject(SubjectDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from SUBJECTS where lower(SUB_ID) =? ";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getSub_id().toLowerCase());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Subject Duplicated");
                return r;
            }

            query = "insert into SUBJECTS(name, SUB_ID, DEP_ID, BRANCH_ID, TYPE) values (?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getName());
            pst.setString(2, detail.getSub_id());
            pst.setLong(3, detail.getDep_id());
            pst.setLong(4, detail.getBranch_id());
            pst.setString(5, detail.getType());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result addSubject(Vector data) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            con.setAutoCommit(false);
            for (int i = 0; i < data.size(); i++) {
                SubjectDetail detail = (SubjectDetail) data.elementAt(i);
                query = "select * from SUBJECTS where lower(SUB_ID) =? ";
                pst = con.prepareStatement(query);
                pst.setString(1, detail.getSub_id().toLowerCase());
                rs = pst.executeQuery();
                if (rs.next()) {
                    con.close();
                    r.setMessage(rs.getString("name") + " Subject Duplicated");
                    return r;
                }
                query = "insert into SUBJECTS(name, ALIAS, SUB_ID, DEP_ID, BRANCH_ID, TYPE,SEM) values (?,?,?,?,?,?,?)";
                pst = con.prepareStatement(query);
                pst.setString(1, detail.getName());
                pst.setString(2, detail.getAlias());
                pst.setString(3, detail.getSub_id());
                pst.setLong(4, detail.getDep_id());
                pst.setLong(5, detail.getBranch_id());
                pst.setString(6, detail.getType());
                pst.setInt(7, detail.getSem());
                pst.execute();
            }
            r.setSuccess(true);
            con.commit();
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result modifySubject(SubjectDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from SUBJECTS where lower(sub_id) = ? and id!=? ";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getSub_id().toLowerCase());
            pst.setLong(2, detail.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Book No. Duplicated");
                return r;
            }

            query = "update SUBJECTS set name= ?, alias= ?, SUB_ID =?, DEP_ID =?, BRANCH_ID=?, TYPE =?, sem=? where id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getName());
            pst.setString(2, detail.getAlias());
            pst.setString(3, detail.getSub_id());
            pst.setLong(4, detail.getDep_id());
            pst.setLong(5, detail.getBranch_id());
            pst.setString(6, detail.getType());
            pst.setInt(7, detail.getSem());
            pst.setLong(8, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result deleteSubject(SubjectDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from SUB_ALLOC where SUB_ID = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Subject can't be deleted. \n Due to Used in subject Allocation");
                return r;
            }
            query = " delete from SUBJECTS where id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Vector getSubjects(long dep_id, long branch_id, long sem) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select s.*, d.name as dep_name, b.name as b_name from SUBJECTS s "
                    + " inner join DEPARTMENTS d on (d.id = s.DEP_ID)"
                    + " inner join BARANCHS b on (b.id = s.BRANCH_ID)";
            if (dep_id > 0) {
                query = query + " where dep_id = " + dep_id;
            }
            if (branch_id > 0) {
                query = query + " and branch_id = " + branch_id;
            }
            if (sem > 0) {
                query = query + " and sem = " + sem;
            }
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                SubjectDetail detail = new SubjectDetail();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name"));
                detail.setAlias(rs.getString("alias"));
                detail.setSub_id(rs.getString("SUB_ID"));
                detail.setDep_id(rs.getLong("DEP_ID"));
                detail.setBranch_id(rs.getLong("BRANCH_ID"));
                detail.setType(rs.getString("type"));
                detail.setSem(rs.getInt("sem"));
                detail.setDep_name(rs.getString("dep_name"));
                detail.setBranch_name(rs.getString("b_name"));
                v.add(detail);
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    public Result allocSubject(Vector data) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            for (int i = 0; i < data.size(); i++) {
                SubjectAllocation detail = (SubjectAllocation) data.elementAt(i);
                query = "select d.name from SUB_ALLOC s "
                        + " inner join SUBJECTS d on (s.sub_id = d.id) where s.SUB_ID = ? ";
                pst = con.prepareStatement(query);
                pst.setLong(1, detail.getSubject_id());
                rs = pst.executeQuery();
                if (rs.next()) {
                    con.close();
                    r.setMessage(rs.getString("name") + " Subject is  Already Allocated");
                    return r;
                }

                query = "insert into SUB_ALLOC(SUB_ID, TEACH_ID, DEP_ID, BRANCH_ID,TYPE,LOAD,LENGTH,PRIORITY, sem,GROUPS, repeat)"
                        + " values (?,?,?,?,?,?,?,?,?,?,?)";
                pst = con.prepareStatement(query);
                pst.setLong(1, detail.getSubject_id());
                pst.setLong(2, detail.getTeacher_id());
                pst.setLong(3, detail.getDep_id());
                pst.setLong(4, detail.getBranch_id());
                pst.setString(5, detail.getType());
                pst.setLong(6, detail.getLoad());
                pst.setLong(7, detail.getLength());
                pst.setInt(8, detail.getPriority());
                pst.setLong(9, detail.getSem());
                pst.setInt(10, detail.getGroup());
                pst.setInt(11, detail.getRepeat());
                pst.execute();
            }
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result modifyAllocSubject(SubjectAllocation detail) {
        Connection con = null;
        PreparedStatement pst = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from SUB_ALLOC where SUB_ID = ?  and id!=?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getSubject_id());
            pst.setLong(3, detail.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Subject is  Already Allocated");
                return r;
            }
            query = "update SUB_ALLOC set SUB_ID =?, TEACH_ID =?, DEP_ID =? ,BRANCH_ID=?, TYPE = ? , LOAD =?, LENGTH =?, PRIORITY = ?, sem = ?, "
                    + " groups= ?, repeat = ?  where id =? ";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getSubject_id());
            pst.setLong(2, detail.getTeacher_id());
            pst.setLong(3, detail.getDep_id());
            pst.setLong(4, detail.getBranch_id());
            pst.setString(5, detail.getType());
            pst.setLong(6, detail.getLoad());
            pst.setLong(7, detail.getLength());
            pst.setInt(8, detail.getPriority());
            pst.setLong(9, detail.getSem());
            pst.setInt(10, detail.getGroup());
            pst.setInt(11, detail.getRepeat());
            pst.setLong(12, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Vector getAllocSubject(long dep, long branch, long sem) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select sa.*, b.name as teach_name, s.name as sub_name, br.name as branch, d.name as department, s.alias "
                    + " from SUB_ALLOC sa "
                    + " inner join TEACHER b on (b.id = sa.TEACH_ID)"
                    + " inner join SUBJECTS s on (s.id = sa.SUB_ID)  "
                    + " inner join DEPARTMENTS d on (d.id = sa.dep_id) "
                    + " inner join BARANCHS br on (br.id = sa.branch_id)";
            if (dep > 0) {
                query = query + " where sa.dep_id = " + dep;
            }
            if (branch > 0) {
                query = query + ((!query.contains("where") ? " where " : " and ") + " sa.branch_id = " + branch);
            }
            if (sem > 0) {
                query = query + ((!query.contains("where") ? " where " : " and ") + " sa.sem = " + sem);
            }
            query = query + " order by sa.dep_id, sa.branch_id, sa.sem ";

            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                SubjectAllocation detail = new SubjectAllocation();
                detail.setId(rs.getLong("id"));
                detail.setSubject_id(rs.getLong("sub_id"));
                detail.setTeacher_id(rs.getLong("teach_id"));
                detail.setDep_id(rs.getLong("dep_id"));
                detail.setBranch_id(rs.getLong("branch_id"));
                detail.setSubject_name(rs.getString("sub_name"));
                detail.setAlias(rs.getString("alias"));
                detail.setDep_name(rs.getString("department"));
                detail.setBranch_name(rs.getString("branch"));
                detail.setTech_name(rs.getString("teach_name"));
                detail.setType(rs.getString("type"));
                detail.setLoad(rs.getLong("load"));
                detail.setLength(rs.getLong("length"));
                detail.setPriority(rs.getInt("PRIORITY"));
                detail.setSem(rs.getLong("sem"));
                detail.setGroup(rs.getInt("groups"));
                detail.setRepeat(rs.getInt("repeat"));
                v.add(detail);
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    public Result deleteAllocSubject(SubjectAllocation detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from TIME_TABLE where SUB_ALLOC_ID = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Subject Allocation can't be deleted. \n Due to Used in Time Table");
                return r;
            }
            query = " delete from SUB_ALLOC where id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result addConfig(Vector<ConfigDetail> data, boolean update) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            for (int i = 0; i < data.size(); i++) {
                ConfigDetail detail = data.get(i);
                query = "select * from CONFIG where DEP_ID =? and BRANCH_ID= ? and SEM= ?";
                pst = con.prepareStatement(query);
                pst.setLong(1, detail.getDep_id());
                pst.setLong(2, detail.getBranch_id());
                pst.setLong(3, detail.getSem());
                rs = pst.executeQuery();
                if (rs.next()) {
                    if (update) {
                        long id = rs.getLong("id");
                        query = "select * from time_table where config_id = ?";
                        pst = con.prepareStatement(query);
                        pst.setLong(1, id);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            //used configurations never updated
                            continue;
                        }
                        query = "update config set total_lec =?, total_day =?, break_after =?, max_gen =? where id = ?";
                        pst = con.prepareStatement(query);
                        pst.setLong(1, detail.getTotal_lec());
                        pst.setLong(2, detail.getTotal_day());
                        pst.setLong(3, detail.getBreak_after());
                        pst.setLong(4, detail.getMax_generations());
                        pst.setLong(5, id);
                        pst.execute();
                    }
                    continue;
//                con.close();
//                r.setMessage("Configuration Duplicated");
//                return r;
                }

                query = "insert into CONFIG(DEP_ID, BRANCH_ID, sem, TOTAL_LEC, total_day,BREAK_AFTER, max_gen) values (?,?,?,?,?,?,?)";
                pst = con.prepareStatement(query);
                pst.setLong(1, detail.getDep_id());
                pst.setLong(2, detail.getBranch_id());
                pst.setLong(3, detail.getSem());
                pst.setLong(4, detail.getTotal_lec());
                pst.setLong(5, detail.getTotal_day());
                pst.setLong(6, detail.getBreak_after());
                pst.setLong(7, detail.getMax_generations());
                pst.execute();
            }
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result modifyConfig(ConfigDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from CONFIG where DEP_ID =? and BRANCH_ID= ? and SEM= ?  and id!=? ";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getDep_id());
            pst.setLong(2, detail.getBranch_id());
            pst.setLong(3, detail.getSem());
            pst.setLong(4, detail.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("config Duplicated");
                return r;
            }
            query = "select * from time_table where config_id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            rs = pst.executeQuery();
            if (rs.next()) {
                //used configurations never updated
                con.close();
                r.setMessage("Configuration used in Time Table can't be updated. \n Please clear Time table before updating the config.");
                return r;
            }
            query = "update CONFIG set DEP_ID =?, BRANCH_ID=?, sem =?, TOTAL_LEC=?, total_day =?, BREAK_AFTER =?, max_gen=? where id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getDep_id());
            pst.setLong(2, detail.getBranch_id());
            pst.setLong(3, detail.getSem());
            pst.setLong(4, detail.getTotal_lec());
            pst.setLong(5, detail.getTotal_day());
            pst.setLong(6, detail.getBreak_after());
            pst.setLong(7, detail.getMax_generations());
            pst.setLong(8, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Result deleteConfig(ConfigDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from time_table where config_id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                //used configurations never updated
                con.close();
                r.setMessage("Configuration used in Time Table can't be Deleted. \n Please clear Time table before Deleteing the config.");
                return r;
            }
            query = " delete from CONFIG where id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Vector getConfig(long dep_id, long branch_id, long sem, long config) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select s.*, d.name as dep_name, b.name as b_name from CONFIG s "
                    + " inner join DEPARTMENTS d on (d.id = s.DEP_ID)"
                    + " inner join BARANCHS b on (b.id = s.BRANCH_ID)";
            if (dep_id > 0) {
                query = query + " where s.dep_id = " + dep_id;
            }
            if (branch_id > 0) {
                query = query + ((!query.contains("where") ? " where " : " and ") + " s.branch_id = " + branch_id);
            }
            if (sem > 0) {
                query = query + ((!query.contains("where") ? " where " : " and ") + " s.sem = " + sem);
            }
            if (config > 0) {
                query = query + ((!query.contains("where") ? " where " : " and ") + " s.id = " + config);
            }
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                ConfigDetail detail = new ConfigDetail();
                detail.setId(rs.getLong("id"));
                detail.setDep_id(rs.getLong("DEP_ID"));
                detail.setBranch_id(rs.getLong("BRANCH_ID"));
                detail.setDep_name(rs.getString("dep_name"));
                detail.setBranch_name(rs.getString("b_name"));
                detail.setSem(rs.getLong("sem"));
                detail.setTotal_lec(rs.getLong("total_lec"));
                detail.setTotal_day(rs.getLong("total_day"));
                detail.setBreak_after(rs.getLong("break_after"));
                detail.setMax_generations(rs.getInt("max_gen"));
                v.add(detail);
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    //not used
    @Deprecated
    public Result checkTimeTable() {
        Result r = new Result();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        try {
            con = getConnection();
            query = "select * from TIME_TABLE ";
            pst = con.prepareStatement(query);
//            pst.setString(1, session);
            rs = pst.executeQuery();
            if (rs.next()) {
                r.setMessage(" Time table is already Exists.");
                con.close();
                return r;
            }
            con.close();
            r.setSuccess(true);
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;

    }

    public Result setTimeTable(Vector<TimeTableDetail> timeTableDetail) {
        Result r = new Result();
        Connection con = null;
        PreparedStatement pst, pst1 = null;
        String query, deleteQuery;
        try {
            con = getConnection();
           List<Long> l=  timeTableDetail.stream().filter(distinctByKey(d -> d.getConfig_id())).flatMapToLong(x-> LongStream.of(x.getConfig_id()))
                   .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
           
            deleteQuery = "delete from TIME_TABLE where config_id in  " +l;
            deleteQuery = deleteQuery.replace("[", "(");
            deleteQuery = deleteQuery.replace("]", ")");
            pst1 = con.prepareStatement(deleteQuery);
//            pst1.setLong(1, detail.getConfig_id());
            pst1.execute();
            query = "insert into time_table(DAY, SUB_ALLOC_ID, lec, config_id, SUB_ALLOC_ID_SEC) values(?,?,?,?,?)";
            pst = con.prepareStatement(query);
            for (TimeTableDetail detail : timeTableDetail) {
                //delete old record

                //add New
                pst.setLong(1, detail.getDay());
                pst.setLong(2, detail.getSub_alloc_id());
                pst.setLong(3, detail.getLec());
                pst.setLong(4, detail.getConfig_id());
                pst.setLong(5, detail.getSub_alloc_id2());
                pst.execute();
            }
            con.close();
            r.setSuccess(true);
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public  <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    public Vector getTimeTable() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select config_id, d.name as dep_name, b.name as b_name, c.sem from time_table t "
                    + " inner join CONFIG c on(c.id = t.config_id) "
                    + " inner join DEPARTMENTS d on (d.id = c.DEP_ID)"
                    + " inner join BARANCHS b on (b.id = c.BRANCH_ID)"
                    + " group by t.config_id, d.name,b.name,c.sem order by d.name, b.name, c.sem ";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                TimeTableDetail detail = new TimeTableDetail();
//                detail.setId(rs.getLong("id"));
                detail.setConfig_id(rs.getLong("config_id"));
                detail.setDep_name(rs.getString("dep_name"));
                detail.setBranch_name(rs.getString("b_name"));
                detail.setSem(rs.getInt("sem"));
                v.add(detail);
            }

            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    public Vector getTimeTableDetail(long config_id) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select * from time_table where config_id = ? order by day, lec";
            pst = con.prepareStatement(query);
            pst.setLong(1, config_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                TimeTableDetail detail = new TimeTableDetail();
                detail.setId(rs.getLong("id"));
                detail.setConfig_id(rs.getLong("config_id"));
                detail.setDay(rs.getLong("day"));
                detail.setLec(rs.getLong("lec"));
                detail.setSub_alloc_id(rs.getLong("SUB_ALLOC_ID"));
                detail.setSub_alloc_id2(rs.getLong("SUB_ALLOC_ID_SEC"));
                v.add(detail);
            }

            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    public boolean createBackup(String path) {
        Connection con = null;
        try {
            con = getConnection();
            String backupDir = path + File.separator + (new Date()).getTime();
            CallableStatement cs = con.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
            cs.setString(1, backupDir);
            cs.execute();
            cs.close();
            con.close();
            ZipUtils zip = new ZipUtils();
            String zipPath = "backup-" + Utility.getDefault().getTimeStamp() + ".attbk";
            zip.createZip(backupDir + "/data", path + File.separator + "zip" + zipPath);
            CryptoUtils.encrypt(path + File.separator + "zip" + zipPath, path + File.separator + zipPath);
            FileUtility.getDefault().removeDir(backupDir);
            FileUtility.getDefault().removeFile(path + File.separator + "zip" + zipPath);
            return true;
        } catch (Exception e) {
            handelException(e, con);
        }
        return false;
    }

    public boolean restoreBackup(String fpath) {
        try {
            String temp = FileUtility.getDefault().createTempPath();

            CryptoUtils.decrypt(fpath, temp + "/restore.zip");
            ZipUtils zip = new ZipUtils();
            zip.unzipFile(temp + "/restore.zip", temp + File.separator);
//            String restorePath = null;
//            FileUtility.getDefault().removeDir(path + "/data");

            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            try {
                DriverManager.getConnection("jdbc:derby:" + path + "/data/;shutdown=true", dbUsername, dbPassword);
            } catch (Exception e) {
                Connection con = DriverManager.getConnection("jdbc:derby:" + path + "/data/;restoreFrom=" + temp + File.separator + "data", dbUsername, dbPassword);
                con.close();
            }
            FileUtility.getDefault().removeDir(temp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
