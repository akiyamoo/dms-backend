package kg.eldar.dms.controller;

import kg.eldar.dms.dao.SalaryDao;
import kg.eldar.dms.model.DateModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalaryController {
    SalaryDao salaryDao;

    @PostMapping("/all/{year}/{month}")
    public ResponseEntity<?> getAll(@PathVariable String year, @PathVariable String month) throws SQLException {
        DateModel dateModel = new DateModel();
        dateModel.setMonth(month);
        dateModel.setYear(Integer.valueOf(year));

        return ResponseEntity.ok(salaryDao.getAll(dateModel));
    }

    @PostMapping("/sum/{year}/{month}")
    public ResponseEntity<?> getSum(@PathVariable String year, @PathVariable String month) throws SQLException {
        DateModel dateModel = new DateModel();
        dateModel.setMonth(month);
        dateModel.setYear(Integer.valueOf(year));

        return ResponseEntity.ok(salaryDao.getSum(dateModel));
    }

    @PostMapping("/issue/{year}/{month}")
    public ResponseEntity<?> add(@PathVariable String year, @PathVariable String month) throws SQLException {
        try {
            DateModel dateModel = new DateModel();
            dateModel.setMonth(month);
            dateModel.setYear(Integer.valueOf(year));
            return ResponseEntity.ok(salaryDao.issueSalary(dateModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update/{year}/{month}/{staffId}/{sumSalary}")
    public ResponseEntity<?> update(@PathVariable String year, @PathVariable String month,
                                    @PathVariable String staffId, @PathVariable String sumSalary) throws SQLException {
        try {
            DateModel dateModel = new DateModel();
            dateModel.setMonth(month);
            dateModel.setYear(Integer.valueOf(year));
            dateModel.setSumSalary(Double.valueOf(sumSalary));
            dateModel.setStaffId(Long.valueOf(staffId));
            return ResponseEntity.ok(salaryDao.update(dateModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
