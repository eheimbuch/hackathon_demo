package de.quickstart;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ExcelController {

    private final ExcelImporter excelImporter;

    @PostMapping("/api/excel")
    public void importExcel() throws Exception {
        excelImporter.importExcel();
    }

    @PostMapping("/api/delete-all")
    public void deleteAll() {
        excelImporter.deleteAll();
    }
}
