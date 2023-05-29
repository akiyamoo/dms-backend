package kg.eldar.dms.controller;

import kg.eldar.dms.dao.MeasurementDao;
import kg.eldar.dms.model.Measurement;
import kg.eldar.dms.model.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/measurement")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MeasurementController {

    MeasurementDao measurementDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(measurementDao.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Measurement measurement) throws SQLException {
        return ResponseEntity.ok(measurementDao.add(measurement));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Measurement measurement) throws SQLException {
        return ResponseEntity.ok(measurementDao.update(measurement));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(measurementDao.delete(id));
    }
}
