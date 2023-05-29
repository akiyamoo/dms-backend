package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.Ingredient;
import kg.eldar.dms.model.Product;
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
public class ProductDao {
    DataSource dataSource;

    public List<Product> getAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        String selectAll = "exec select_product";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setMeasurementId(rs.getLong("measurement_id"));
                product.setMeasurement(rs.getString("measurement"));
                product.setAmount(rs.getDouble("amount"));
                product.setSum(rs.getDouble("sum"));
                products.add(product);
            }
            return products;
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

    public String add(Product product) throws SQLException {
        String insert = "exec insert_product @name=?, @measurement_id=?, @amount=?, @sum=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insert);
            ps.setString(1, product.getName());
            ps.setLong(2, product.getMeasurementId());
            ps.setDouble(3, product.getAmount());
            ps.setDouble(4, product.getSum());
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

    public String update(Product product) throws SQLException {
        String update = "exec update_product @id=?, @name=?, @measurement_id=?, @amount=?, @sum=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, product.getId());
            ps.setString(2, product.getName());
            ps.setLong(3, product.getMeasurementId());
            ps.setDouble(4, product.getAmount());
            ps.setDouble(5, product.getSum());
            int rowsUpdated = ps.executeUpdate();

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
        String delete = "exec delete_product @id=?";
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
