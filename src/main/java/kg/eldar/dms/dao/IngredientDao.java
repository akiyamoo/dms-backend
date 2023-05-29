package kg.eldar.dms.dao;

import kg.eldar.dms.model.Ingredient;
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
public class IngredientDao {
    DataSource dataSource;

    public void getConnection() throws SQLException {
        dataSource.getConnection();

        log.info("OK");
    }

    public List<Ingredient> getAll() throws SQLException {
        String selectAll = "exec select_ingredient";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Ingredient> ingredients = new ArrayList<Ingredient>();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getLong("id"));
                ingredient.setProductId(rs.getLong("product_id"));
                ingredient.setProduct(rs.getString("product"));
                ingredient.setSupplyId(rs.getLong("supply_id"));
                ingredient.setSupply(rs.getString("supply"));
                ingredient.setAmount(rs.getDouble("amount"));
                ingredients.add(ingredient);
            }
            return ingredients;
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

    public List<Ingredient> getAll(Integer productId) throws SQLException {
        Connection connection = dataSource.getConnection();
        String selectAll = "exec get_ingredient_by_product_id @product_id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            ps.setLong(1, productId);
            rs = ps.executeQuery();
            List<Ingredient> ingredients = new ArrayList<Ingredient>();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getLong("id"));
                ingredient.setProductId(rs.getLong("product_id"));
                ingredient.setSupply(rs.getString("product"));
                ingredient.setSupplyId(rs.getLong("supply_id"));
                ingredient.setSupply(rs.getString("supply"));
                ingredient.setAmount(rs.getDouble("amount"));
                ingredients.add(ingredient);
            }
            return ingredients;
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

    public String add(Ingredient ingredient) throws SQLException {
        String insert = "exec insert_ingredient @product_id=?, @supply_id=?, @amount=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(insert);
            ps.setLong(1, ingredient.getProductId());
            ps.setLong(2, ingredient.getSupplyId());
            ps.setDouble(3, ingredient.getAmount());
            rs = ps.executeQuery();
            String response = "";
            while (rs.next()) {
                response = rs.getString("response");
            }
            return response;
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
        return "Непредвиденная ошибка на сервере";
    }

    public String update(Ingredient ingredient) throws SQLException {
        String update = "exec update_ingredient @id=?, @product_id=?, @supply_id=?, @amount=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(update);
            ps.setLong(1, ingredient.getId());
            ps.setLong(2, ingredient.getProductId());
            ps.setLong(3, ingredient.getSupplyId());
            ps.setDouble(4, ingredient.getAmount());
            rs = ps.executeQuery();
            String response = "";
            while (rs.next()) {
                response = rs.getString("response");
            }
            return response;
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
        return null;
    }

    public String delete(Long id) throws SQLException {
        String delete = "exec delete_ingredient @id=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(delete);
            ps.setLong(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                return "Запись успешна удалена!";
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

        return "Произошла ошибка на сервере!";
    }
}
