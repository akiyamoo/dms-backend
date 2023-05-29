package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.SaleOfProduct;
import kg.eldar.dms.util.DateUtil;
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
public class SaleOfProductDao {
    DataSource dataSource;

    public List<SaleOfProduct> getAll() throws SQLException {
        String selectAll = "exec select_sale_of_product";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<SaleOfProduct> products = new ArrayList<>();
            while (rs.next()) {
                SaleOfProduct product = new SaleOfProduct();

                product.setId(rs.getLong("id"));
                product.setProductId(rs.getLong("product_id"));
                product.setProduct(rs.getString("product"));
                product.setStaff(rs.getString("staff"));
                product.setStaffId(rs.getLong("staff_id"));
                product.setCreatedDate(DateUtil.frontNoSeconds.format(rs.getTimestamp("date")));
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

    public String add(SaleOfProduct product) throws SQLException {
        String insert = "exec insert_sale_of_product @product_id=?, @amount=?, @staff_id=?";
        Connection connection = dataSource.getConnection();
        CallableStatement ps = null;
        try {
            ps = connection.prepareCall(insert);
            ps.setLong(1, product.getProductId());
            ps.setDouble(2, product.getAmount());
            ps.setLong(3, product.getStaffId());
            ps.execute();
            while (ps.getMoreResults() || ps.getUpdateCount() != -1) {
            }
        } catch (Exception e) {
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
                return exception.getMessage();
            }
        }

        return Result.INSERT.getDescription();
    }

    public String update(SaleOfProduct product) throws SQLException {
        String update = "exec update_sale_of_product @id=?, @product_id=?, @amount=?, @sum=?, @staff_id=?, @date=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);

            ps.setLong(1, product.getId());
            ps.setLong(2, product.getProductId());
            ps.setDouble(3, product.getAmount());
            ps.setDouble(4, product.getSum());
            ps.setLong(5, product.getStaffId());
            ps.setTimestamp(6, new Timestamp(DateUtil.frontNoSeconds.parse(product.getCreatedDate()).getTime()));

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing supply was updated successfully!");
            }
        } catch (SQLException | ParseException e) {
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
        String delete = "exec delete_sale_of_product @id=?";
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
