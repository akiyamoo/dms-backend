package kg.eldar.dms.controller;

import kg.eldar.dms.dao.BudgetDao;
import kg.eldar.dms.model.Budget;
import kg.eldar.dms.model.Ingredient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BudgetController {

    BudgetDao budgetDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(budgetDao.getAll());
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Budget budget) throws SQLException {
        return ResponseEntity.ok(budgetDao.update(budget));
    }
}
