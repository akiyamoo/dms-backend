package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.Budget;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BudgetDao {

    DataSource dataSource;

    public List<Budget> getAll() throws SQLException {
        String selectAll = "exec select_budget";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Budget> budgets = new ArrayList<Budget>();
            while (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getLong("id"));
                budget.setSumOfBudget(rs.getDouble("sum_of_budget"));
                budget.setPercent(rs.getDouble("percent"));
                budget.setBonus(rs.getDouble("bonus"));
                budgets.add(budget);
            }
            return budgets;
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

    public String update(Budget budget) throws SQLException {
        String update = "exec update_budget @id=?, @sumOfBudget=?, @percent=?, @bonus=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, budget.getId());
            ps.setDouble(2, budget.getSumOfBudget());
            ps.setDouble(3, budget.getPercent());
            ps.setDouble(4, budget.getBonus());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                log.info("An existing budget was updated successfully!");
                return Result.UPDATE.getDescription();
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

        return Result.NOT_DELETE.getDescription();
    }
}
