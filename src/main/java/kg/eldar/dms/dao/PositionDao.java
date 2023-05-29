package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.Position;
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
public class PositionDao {
    DataSource dataSource;
    public List<Position> getAll() throws SQLException {
        String selectAll = "exec select_position";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Position> positions = new ArrayList<Position>();
            while (rs.next()) {
                Position position = new Position();
                position.setId(rs.getLong("id"));
                position.setName(rs.getString("name"));
                positions.add(position);
            }
            return positions;
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

    public String add(Position position) throws SQLException {
        String insert = "exec insert_position @name=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insert);
            ps.setString(1, position.getName());
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

    public String update(Position position) throws SQLException {
        String update = "exec update_position @id=?, @name=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, position.getId());
            ps.setString(2, position.getName());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                log.info("An existing position was updated successfully!");
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
        return Result.NOT_UPDATE.getDescription();
    }

    public String delete(Long id) throws SQLException {
        String delete = "exec delete_position @id=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(delete);
            ps.setLong(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                log.info("A position was deleted successfully!");
                return Result.DELETE.getDescription();
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
