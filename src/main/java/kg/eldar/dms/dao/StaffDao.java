package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.Staff;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StaffDao {

    DataSource dataSource;

    public List<Staff> getAll() throws SQLException {
        String selectAll = "exec select_staff";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Staff> staffs = new ArrayList<Staff>();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getLong("id"));
                staff.setFio(rs.getString("fio"));
                staff.setPositionId(rs.getLong("position_id"));
                staff.setPosition(rs.getString("position"));
                staff.setSalary(rs.getDouble("salary"));
                staff.setAddress(rs.getString("address"));
                staff.setPhoneNumber(rs.getString("phone_number"));
                staffs.add(staff);
            }
            return staffs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(connection != null)
                    connection.close();
                if (ps != null)
                    ps.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String add(Staff staff) throws SQLException {
        String insert = "exec insert_staff @fio=?, @position_id=?, @salary=?, @address=?, @phone_number=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insert);
            ps.setString(1, staff.getFio());
            ps.setLong(2, staff.getPositionId());
            ps.setDouble(3, staff.getSalary());
            ps.setString(4, staff.getAddress());
            ps.setString(5, staff.getPhoneNumber());
            ps.executeUpdate();
            return Result.INSERT.getDescription();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                if(connection != null)
                    connection.close();
                if(ps != null)
                    ps.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public String update(Staff staff) throws SQLException {
        String update = "exec update_staff @id=?, @fio=?, @position_id=?, @salary=?, @address=?, @phone_number=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, staff.getId());
            ps.setString(2, staff.getFio());
            ps.setLong(3, staff.getPositionId());
            ps.setDouble(4, staff.getSalary());
            ps.setString(5, staff.getAddress());
            ps.setString(6, staff.getPhoneNumber());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing staff was updated successfully!");
            }
            return Result.UPDATE.getDescription();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                if(connection != null)
                    connection.close();
                if(ps != null)
                    ps.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public String delete(Long id) throws SQLException {
        String delete = "exec delete_staff @id=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(delete);
            ps.setLong(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A staff was deleted successfully!");
            }
            return Result.DELETE.getDescription();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                if(connection != null)
                    connection.close();
                if(ps != null)
                    ps.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
