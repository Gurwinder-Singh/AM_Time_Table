/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.db;

import com.gdev.timetable.helper.MessageDisplay;
import com.gdev.timetable.model.ConfigDetail;
import com.gdev.timetable.model.SubjectDetail;
import com.gdev.timetable.model.SubjectAllocation;
import com.gdev.timetable.model.IdName;
import com.gdev.timetable.model.Result;
import com.gdev.timetable.model.TeacherDetail;
import com.gdev.timetable.model.TimeTableDetail;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

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

            query = "update SUBJECTS set name= ?, SUB_ID =?, DEP_ID =?, BRANCH_ID=?, TYPE =? where id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, detail.getName());
            pst.setString(2, detail.getSub_id());
            pst.setLong(3, detail.getDep_id());
            pst.setLong(4, detail.getBranch_id());
            pst.setString(5, detail.getType());
            pst.setLong(6, detail.getId());
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

    public Vector getSubjects(long dep_id, long branch_id) {
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
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                SubjectDetail detail = new SubjectDetail();
                detail.setId(rs.getLong("id"));
                detail.setName(rs.getString("name"));
                detail.setSub_id(rs.getString("SUB_ID"));
                detail.setDep_id(rs.getLong("DEP_ID"));
                detail.setBranch_id(rs.getLong("BRANCH_ID"));
                detail.setType(rs.getString("type"));
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

    public Result allocSubject(SubjectAllocation detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from SUB_ALLOC where SUB_ID = ? and SESSION = ? ";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getSubject_id());
            pst.setString(2, detail.getSession());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Subject is  Already Allocated");
                return r;
            }

            query = "insert into SUB_ALLOC(SUB_ID, TEACH_ID, DEP_ID, BRANCH_ID,TYPE,LOAD,LENGTH,SESSION,PRIORITY, sem)"
                    + " values (?,?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getSubject_id());
            pst.setLong(2, detail.getTeacher_id());
            pst.setLong(3, detail.getDep_id());
            pst.setLong(4, detail.getBranch_id());
            pst.setString(5, detail.getType());
            pst.setLong(6, detail.getLoad());
            pst.setLong(7, detail.getLength());
            pst.setString(8, detail.getSession());
            pst.setInt(9, detail.getPriority());
            pst.setLong(10, detail.getSem());
            pst.execute();
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
            query = "select * from SUB_ALLOC where SUB_ID = ? and SESSION = ?  and id!=?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getSubject_id());
            pst.setString(2, detail.getSession());
            pst.setLong(3, detail.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Subject is  Already Allocated");
                return r;
            }
            query = "update SUB_ALLOC set SUB_ID =?, TEACH_ID =?, DEP_ID =? ,BRANCH_ID=?, TYPE = ? , LOAD =?, LENGTH =?, PRIORITY = ?, sem = ?"
                    + " where id =? ";
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
            pst.setLong(10, detail.getId());
            pst.execute();
            r.setSuccess(true);
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }

    public Vector getAllocSubject(String session) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Vector v = new Vector();
        String query;
        try {
            con = getConnection();
            query = "select sa.*, b.name as teach_name, s.name as sub_name, br.name as branch, d.name as department "
                    + " from SUB_ALLOC sa "
                    + " inner join TEACHER b on (b.id = sa.TEACH_ID)"
                    + " inner join SUBJECTS s on (s.id = sa.SUB_ID)  "
                    + " inner join DEPARTMENTS d on (d.id = sa.dep_id) "
                    + " inner join BARANCHS br on (br.id = sa.branch_id) "
                    + " where sa.session = ?";

            pst = con.prepareStatement(query);
            pst.setString(1, session);
            rs = pst.executeQuery();
            while (rs.next()) {
                SubjectAllocation detail = new SubjectAllocation();
                detail.setId(rs.getLong("id"));
                detail.setSubject_id(rs.getLong("sub_id"));
                detail.setTeacher_id(rs.getLong("teach_id"));
                detail.setDep_id(rs.getLong("dep_id"));
                detail.setBranch_id(rs.getLong("branch_id"));
                detail.setSubject_name(rs.getString("sub_name"));
                detail.setBranch_name(rs.getString("department") + " / " + rs.getString("branch"));
                detail.setTech_name(rs.getString("teach_name"));
                detail.setType(rs.getString("type"));
                detail.setLoad(rs.getLong("load"));
                detail.setLength(rs.getLong("length"));
                detail.setSession(rs.getString("session"));
                detail.setPriority(rs.getInt("PRIORITY"));
                detail.setSem(rs.getLong("sem"));
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

    public Result addConfig(ConfigDetail detail) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        Result r = new Result();
        try {
            con = getConnection();
            query = "select * from CONFIG where DEP_ID =?, BRANCH_ID= ?, SEM= ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getDep_id());
            pst.setLong(2, detail.getBranch_id());
            pst.setLong(3, detail.getSem());
            rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                r.setMessage("Configuration Duplicated");
                return r;
            }

            query = "insert into CONFIG(DEP_ID, BRANCH_ID, sem, TOTAL_LEC, total_day) values (?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getDep_id());
            pst.setLong(2, detail.getBranch_id());
            pst.setLong(3, detail.getSem());
            pst.setLong(4, detail.getTotal_lec());
            pst.setLong(5, detail.getTotal_day());
            pst.execute();
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
            query = "select * from CONFIG where DEP_ID =?, BRANCH_ID= ?, SEM= ? id!=? ";
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

            query = "update CONFIG set DEP_ID =?, BRANCH_ID=?, sem =?, TOTAL_LEC=?, total_day =? where id = ?";
            pst = con.prepareStatement(query);
            pst.setLong(1, detail.getDep_id());
            pst.setLong(2, detail.getBranch_id());
            pst.setLong(3, detail.getSem());
            pst.setLong(4, detail.getTotal_lec());
            pst.setLong(5, detail.getTotal_day());
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

    public Vector getConfig(long dep_id, long branch_id) {
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
                query = query + " where dep_id = " + dep_id;
            }
            if (branch_id > 0) {
                query = query + " and branch_id = " + branch_id;
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
                v.add(detail);
            }
            con.close();
        } catch (Exception e) {
            handelException(e, con);
        }
        return v;
    }

    public Result checkTimeTable(String session) {
        Result r = new Result();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String query;
        try {
            con = getConnection();
            query = "select * from TIME_TABLE where SESSION = ? ";
            pst = con.prepareStatement(query);
            pst.setString(1, session);
            rs = pst.executeQuery();
            if (rs.next()) {
                r.setMessage(session + " Time table is already Exists.");
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

    public Result setTimeTable(Vector<TimeTableDetail> timeTableDetail, String session) {
        Result r = new Result();
        Connection con = null;
        PreparedStatement pst = null;
        String query;
        try {
            con = getConnection();
            // Clear previous Data
            query = "delete from TIME_TABLE  where session = " + session;
            pst = con.prepareStatement(query);
            pst.execute();

            query = "insert into time_table(SESSION, DAY, SUB_ALLOC_ID, lec) values(?,?,?,?)";
            pst = con.prepareStatement(query);
            for (TimeTableDetail detail : timeTableDetail) {
                pst.setString(1, session);
                pst.setLong(2, detail.getDay());
                pst.setLong(3, detail.getSub_alloc_id());
                pst.setLong(4, detail.getLec());
                pst.execute();
            }
            con.close();
            r.setSuccess(true);
        } catch (Exception e) {
            handelException(e, con);
        }
        return r;
    }
    
    

}
