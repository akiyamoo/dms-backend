package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.Supply;
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
public class SupplyDao {

    DataSource dataSource;

    public List<Supply> getAll() throws SQLException {
        String selectAll = "exec select_supply";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Supply> supplies = new ArrayList<Supply>();
            while (rs.next()) {
                Supply supply = new Supply();
                supply.setId(rs.getLong("id"));
                supply.setName(rs.getString("name"));
                supply.setMeasurementId(rs.getLong("measurement_id"));
                supply.setMeasurement(rs.getString("measurement"));
                supply.setAmount(rs.getDouble("amount"));
                supply.setSum(rs.getDouble("sum"));
                supplies.add(supply);
            }
            return supplies;
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

    public String add(Supply supply) throws SQLException {
        String insert = "exec insert_supply @name=?, @measurement_id=?, @amount=?, @sum=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insert);
            ps.setString(1, supply.getName());
            ps.setLong(2, supply.getMeasurementId());
            ps.setDouble(3, supply.getAmount());
            ps.setDouble(4, supply.getSum());
            ps.executeUpdate();
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

        return Result.INSERT.getDescription();
    }

    public String update(Supply supply) throws SQLException {
        String update = "exec update_supply @id=?, @name=?, @measurement_id=?, @amount=?, @sum=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, supply.getId());
            ps.setString(2, supply.getName());
            ps.setLong(3, supply.getMeasurementId());
            ps.setDouble(4, supply.getAmount());
            ps.setDouble(5, supply.getSum());
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

    public String delete(Long id) throws SQLException {
        String delete = "exec delete_supply @id=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(delete);
            ps.setLong(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                log.info("A supply was deleted successfully!");
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

        return Result.DELETE.getDescription();
    }
}
