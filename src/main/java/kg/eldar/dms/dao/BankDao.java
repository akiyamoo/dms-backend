package kg.eldar.dms.dao;

import kg.eldar.dms.enums.Result;
import kg.eldar.dms.model.Credit;
import kg.eldar.dms.model.PaymentSchedule;
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
public class BankDao {

    DataSource dataSource;

    public List<Credit> getAll() throws SQLException {
        String selectAll = "exec getCredits";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            rs = ps.executeQuery();
            List<Credit> credits = new ArrayList<>();
            while (rs.next()) {
                Credit credit = new Credit();
                credit.setId(rs.getLong("id"));
                credit.setTarget(rs.getString("target"));
                credit.setSumLoan(rs.getDouble("sum_loan"));
                credit.setLoanDate(DateUtil.frontNoTime.format(rs.getDate("loan_date")));
                credit.setPercentLoan(rs.getDouble("percent_loan"));
                credit.setPercentPenalty(rs.getDouble("percent_penalty"));
                credit.setSumRemaining(rs.getDouble("sum_remaining"));
                credit.setCountYear(rs.getInt("count_year"));
                credit.setIsPaid(rs.getBoolean("is_paid") ? "Закрыт (оплачен)" : "Открыт");

                credits.add(credit);
            }
            return credits;
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

    public List<PaymentSchedule> getPlanPaymentByCredit(Integer creditId) throws SQLException {
        String selectAll = "exec getPlanPaymentByCredit @credit_id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            ps.setInt(1, creditId);
            rs = ps.executeQuery();
            List<PaymentSchedule> paymentSchedules = new ArrayList<>();
            while (rs.next()) {
                PaymentSchedule paymentSchedule = new PaymentSchedule();
                paymentSchedule.setId(rs.getLong("id"));
                paymentSchedule.setCreditId(rs.getLong("credit_id"));
                paymentSchedule.setPaymentDate(rs.getDate("payment_date") != null ? DateUtil.frontNoTime.format(rs.getDate("payment_date")) : null);
                paymentSchedule.setLoanDate(DateUtil.frontNoTime.format(rs.getDate("loan_date")));
                paymentSchedule.setPaymentAmount(rs.getDouble("payment_amount"));
                paymentSchedule.setPenaltyAmount(rs.getDouble("penalty_amount"));
                paymentSchedule.setIsPaid(rs.getBoolean("is_paid") ? "Оплачено" : "Ожидается оплата");

                paymentSchedules.add(paymentSchedule);
            }
            return paymentSchedules;
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

    public PaymentSchedule getCurrentPaymentByCredit(Integer creditId) throws SQLException {
        String selectAll = "exec getCurrentPaymentSchedule @credit_id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(selectAll);
            ps.setInt(1, creditId);
            rs = ps.executeQuery();
            PaymentSchedule paymentSchedule = new PaymentSchedule();
            if (rs.next()) {
                paymentSchedule.setId(rs.getLong("id"));
                paymentSchedule.setCreditId(rs.getLong("credit_id"));
                paymentSchedule.setPaymentDate(rs.getDate("payment_date") != null ? DateUtil.frontNoTime.format(rs.getDate("payment_date")) : null);
                paymentSchedule.setLoanDate(DateUtil.frontNoTime.format(rs.getDate("loan_date")));
                paymentSchedule.setPaymentAmount(rs.getDouble("payment_amount"));
                paymentSchedule.setPenaltyAmount(rs.getDouble("penalty_amount"));

            }
            return paymentSchedule;
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

    public String addCredit(Credit credit) throws SQLException, ParseException {
        String insert = "EXEC AddCredit @target = ?, @sum_loan = ?, @loan_date = ?, @percent_loan = ?, @percent_penalty = ?, @count_year = ?;";
        Connection connection = dataSource.getConnection();
        CallableStatement ps = null;
        try {
            ps = connection.prepareCall(insert);
            ps.setString(1, credit.getTarget());
            ps.setDouble(2, credit.getSumLoan());
            ps.setDate(3, new Date(DateUtil.frontNoTime.parse(credit.getLoanDate()).getTime()));
            ps.setDouble(4, credit.getPercentLoan());
            ps.setDouble(5, credit.getPercentPenalty());
            ps.setInt(6, credit.getCountYear());

            ps.execute();
            while (ps.getMoreResults() || ps.getUpdateCount() != -1) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(connection != null)
                    connection.close();
                if(ps != null)
                    ps.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
                throw exception;
            }
        }

        return Result.INSERT.getDescription();
    }

    public String payCredit(Integer paymentId, String paymentDate) throws SQLException, ParseException {
        String insert = "EXEC payPaymentSchedule @payment_id = ?, @payment_date = ?";
        Connection connection = dataSource.getConnection();
        CallableStatement ps = null;
        try {
            ps = connection.prepareCall(insert);
            ps.setInt(1, paymentId);
            ps.setDate(2, new Date(DateUtil.frontNoTime.parse(paymentDate).getTime()));

            ps.execute();
            while (ps.getMoreResults() || ps.getUpdateCount() != -1) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if(connection != null)
                    connection.close();
                if(ps != null)
                    ps.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
                throw exception;
            }
        }

        return Result.INSERT.getDescription();
    }
}
