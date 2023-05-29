package kg.eldar.dms.controller;

import kg.eldar.dms.dao.ProductDao;
import kg.eldar.dms.model.Ingredient;
import kg.eldar.dms.model.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductDao productDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(productDao.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Product product) throws SQLException {
        return ResponseEntity.ok(productDao.add(product));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Product product) throws SQLException {
        return ResponseEntity.ok(productDao.update(product));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(productDao.delete(id));
    }
}
