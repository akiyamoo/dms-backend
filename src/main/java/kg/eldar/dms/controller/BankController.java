package kg.eldar.dms.controller;

import kg.eldar.dms.dao.BankDao;
import kg.eldar.dms.model.Credit;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankController {

    BankDao bankDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(bankDao.getAll());
    }

    @GetMapping("/plan/{creditId}")
    public ResponseEntity<?> getPlanById(@PathVariable Integer creditId) throws SQLException {
        return ResponseEntity.ok(bankDao.getPlanPaymentByCredit(creditId));
    }

    @GetMapping("/current/{creditId}")
    public ResponseEntity<?> getCurrentById(@PathVariable Integer creditId) throws SQLException {
        return ResponseEntity.ok(bankDao.getCurrentPaymentByCredit(creditId));
    }

    @PostMapping("/add-credit")
    public ResponseEntity<?> add(@RequestBody Credit credit) throws SQLException {
        try {
            return ResponseEntity.ok(bankDao.addCredit(credit));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/pay-credit/{paymentId}/{paymentDate}")
    public ResponseEntity<?> payPaymentSchedule(@PathVariable Integer paymentId, @PathVariable String paymentDate) {
        try {
            return ResponseEntity.ok(bankDao.payCredit(paymentId, paymentDate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
