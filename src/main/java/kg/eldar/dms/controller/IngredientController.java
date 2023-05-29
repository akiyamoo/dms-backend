package kg.eldar.dms.controller;

import kg.eldar.dms.dao.IngredientDao;
import kg.eldar.dms.model.Ingredient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IngredientController {

    IngredientDao ingredientDao;

    @GetMapping("/test")
    public ResponseEntity<String> test() throws SQLException {
        ingredientDao.getConnection();
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/all/{productId}")
    public ResponseEntity<?> getAll(@PathVariable Integer productId) throws SQLException {
        return ResponseEntity.ok(ingredientDao.getAll(productId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Ingredient ingredient) throws SQLException {
        return ResponseEntity.ok(ingredientDao.add(ingredient));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Ingredient ingredient) throws SQLException {
        return ResponseEntity.ok(ingredientDao.update(ingredient));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(ingredientDao.delete(id));
    }
}
