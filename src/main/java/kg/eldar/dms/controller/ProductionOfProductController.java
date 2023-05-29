package kg.eldar.dms.controller;

import kg.eldar.dms.dao.ProductionOfProductDao;
import kg.eldar.dms.model.ProductionOfProduct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/production-of-product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductionOfProductController {
    ProductionOfProductDao productDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(productDao.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductionOfProduct product) throws SQLException {
        try {
            return ResponseEntity.ok(productDao.add(product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProductionOfProduct product) throws SQLException {
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
