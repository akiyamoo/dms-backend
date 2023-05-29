package kg.eldar.dms.controller;

import kg.eldar.dms.dao.SupplyDao;
import kg.eldar.dms.model.Position;
import kg.eldar.dms.model.Supply;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/supply")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplyController {

    SupplyDao supplyDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(supplyDao.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Supply supply) throws SQLException {
        try {
            return ResponseEntity.ok(supplyDao.add(supply));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Supply supply) throws SQLException {
        try {
            return ResponseEntity.ok(supplyDao.update(supply));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        try {

            return ResponseEntity.ok(supplyDao.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
