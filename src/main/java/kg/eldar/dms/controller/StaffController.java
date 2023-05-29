package kg.eldar.dms.controller;

import kg.eldar.dms.dao.StaffDao;
import kg.eldar.dms.model.Staff;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {

    StaffDao staffDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws SQLException {
        return ResponseEntity.ok(staffDao.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Staff staff) throws SQLException {
        return ResponseEntity.ok(staffDao.add(staff));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Staff staff) throws SQLException {
        return ResponseEntity.ok(staffDao.update(staff));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(staffDao.delete(id));
    }
}
