package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.Measurement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MeasurementDao {

    DataSource dataSource;

    public List<Measurement> getAll() throws SQLException {
        String selectAll = "exec select_measurement";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Measurement> measurements = new ArrayList<Measurement>();
            while (rs.next()) {
                Measurement measurement = new Measurement();
                measurement.setId(rs.getLong("id"));
                measurement.setName(rs.getString("name"));
                measurements.add(measurement);
            }
            return measurements;
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

    public String add(Measurement measurement) throws SQLException {
        String insert = "exec insert_measurement @name=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insert);
            ps.setString(1, measurement.getName());
            ps.executeUpdate();

            return Result.INSERT.getDescription();
        } catch (SQLException e) {
            e.printStackTrace();
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

        return Result.NOT_INSERT.getDescription();
    }

    public String update(Measurement measurement) throws SQLException {
        String update = "exec update_measurement @id=?, @name=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, measurement.getId());
            ps.setString(2, measurement.getName());
            int rowsUpdated = ps.executeUpdate();

            return Result.UPDATE.getDescription();
        } catch (SQLException e) {
            e.printStackTrace();
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
        return Result.NOT_UPDATE.getDescription();
    }

    public String delete(Long id) throws SQLException {
        String delete = "exec delete_measurement @id=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(delete);
            ps.setLong(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                return Result.DELETE.getDescription();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
