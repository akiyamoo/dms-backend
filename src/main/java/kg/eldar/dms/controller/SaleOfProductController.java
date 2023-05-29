package kg.eldar.dms.controller;

import kg.eldar.dms.dao.SaleOfProductDao;
import kg.eldar.dms.model.SaleOfProduct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/sale-of-product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaleOfProductController {
    SaleOfProductDao productDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(productDao.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SaleOfProduct product) throws SQLException {
        try {
            return ResponseEntity.ok(productDao.add(product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody SaleOfProduct product) throws SQLException {
        try {
            return ResponseEntity.ok(productDao.update(product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        try {

            return ResponseEntity.ok(productDao.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
