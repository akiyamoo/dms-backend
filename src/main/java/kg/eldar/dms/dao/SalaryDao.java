package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Month;
import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.DateModel;
import kg.eldar.dms.model.Salary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SalaryDao {
    DataSource dataSource;

    public List<Salary> getAll(DateModel dateModel) throws SQLException {
        String selectAll = "EXEC select_salary @year = ?, @month = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            ps.setInt(1, dateModel.getYear());
            ps.setInt(2, Month.getMonth(dateModel.getMonth()).getMonth());
            rs = ps.executeQuery();
            List<Salary> salaries = new ArrayList<>();
            while (rs.next()) {
                Salary salary = new Salary();

                salary.setStaffId(rs.getLong("staff_id"));
                salary.setStaff(rs.getString("staff"));
                salary.setYear(rs.getInt("year"));
                salary.setMonth(dateModel.getMonth());
                salary.setSumSalary(rs.getDouble("sum_salary"));
                salary.setBonus(rs.getDouble("bonus"));
                salary.setSalary(rs.getDouble("salary"));
                salary.setProductionCount(rs.getInt("production_count"));
                salary.setPurchaseCount(rs.getInt("purchase_count"));
                salary.setSaleCount(rs.getInt("sale_count"));
                salary.setSumCount(rs.getInt("sum_count"));
                salary.setIsIssued(rs.getBoolean("is_issued") ? "Выдано" : "Не выдано");

                salaries.add(salary);
            }
            return salaries;
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

    public String issueSalary(DateModel model) throws SQLException {
        String insert = "exec issue_salary @year = ?, @month = ?";
        Connection connection = dataSource.getConnection();
        CallableStatement ps = null;
        try {
            ps = connection.prepareCall(insert);
            ps.setInt(1, model.getYear());
            ps.setInt(2, Month.getMonth(model.getMonth()).getMonth());
            ps.execute();
            while (ps.getMoreResults() || ps.getUpdateCount() != -1) {
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            try {
                if(ps != null)
                    ps.close();
                if(connection != null)
                    connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return Result.INSERT.getDescription();
    }

    public String update(DateModel model) throws SQLException {
        String update = "exec edit_salary @year = ?, @month = ?, @staff_id = ?, @sum_salary = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);

            ps.setInt(1, model.getYear());
            ps.setInt(2, Month.getMonth(model.getMonth()).getMonth());
            ps.setLong(3, model.getStaffId());
            ps.setDouble(4, model.getSumSalary());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing supply was updated successfully!");
            }
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

        return Result.UPDATE.getDescription();
    }

    public String getSum(DateModel dateModel) throws SQLException {
        String selectAll = "EXEC SUM_SALARY @year = ?, @month = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            ps.setInt(1, dateModel.getYear());
            ps.setInt(2, Month.getMonth(dateModel.getMonth()).getMonth());
            rs = ps.executeQuery();

            rs.next();
            return (rs.getDouble("sum") + "").equals("") ? "0" : rs.getDouble("sum") + "";
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
}
