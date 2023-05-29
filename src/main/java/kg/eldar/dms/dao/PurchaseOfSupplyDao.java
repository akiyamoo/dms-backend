package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.PurchaseOfSupply;
import kg.eldar.dms.model.Supply;
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
public class PurchaseOfSupplyDao {

    DataSource dataSource;

    public List<PurchaseOfSupply> getAll() throws SQLException {
        String selectAll = "exec select_purchase_of_supply";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<PurchaseOfSupply> purchaseOfSupplies = new ArrayList<>();
            while (rs.next()) {
                PurchaseOfSupply supply = new PurchaseOfSupply();

                supply.setId(rs.getLong("id"));
                supply.setSupplyId(rs.getLong("supply_id"));
                supply.setSupply(rs.getString("supply"));
                supply.setAmount(rs.getDouble("amount"));
                supply.setSum(rs.getDouble("sum"));
                supply.setCreatedDate(DateUtil.frontNoSeconds.format(rs.getTimestamp("date")));
                supply.setStaff(rs.getString("staff"));
                supply.setStaffId(rs.getLong("staff_id"));

                purchaseOfSupplies.add(supply);
            }
            return purchaseOfSupplies;
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

    public String add(PurchaseOfSupply supply) throws SQLException {
        String insert = "exec insertPurchaseOfSupply @supply_id=?, @amount=?, @sum=?, @staff_id=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insert);
            ps.setLong(1, supply.getSupplyId());
            ps.setDouble(2, supply.getAmount());
            ps.setDouble(3, supply.getSum());
            ps.setLong(4, supply.getStaffId());
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

    public String update(PurchaseOfSupply supply) throws SQLException {
        String update = "exec update_purchase_of_supply @id=?, @supply_id=?, @amount=?, @sum=?, @staff_id=?, @date=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, supply.getId());
            ps.setLong(2, supply.getSupplyId());
            ps.setDouble(3, supply.getAmount());
            ps.setDouble(4, supply.getSum());
            ps.setLong(5, supply.getStaffId());
            ps.setTimestamp(6, new Timestamp(DateUtil.frontNoSeconds.parse(supply.getCreatedDate()).getTime()));
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
        String delete = "exec delete_purchase_of_supply @id=?";
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
