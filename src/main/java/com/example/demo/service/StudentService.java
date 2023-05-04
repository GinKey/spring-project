package com.example.demo.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.demo.domain.Student;
import com.example.demo.repo.StudentRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public List<Student> listAll(String keyword, Boolean find_problem){
        if (keyword != null) {
            return repo.search(keyword);
        }
        else if (find_problem) {
            return repo.findByProblemstudentIsNotNull();
        }
        else {
            return repo.findAll();
        }
    }

    public void save(Student student) {
        repo.save(student);
    }

    public void update(Student student) {
        repo.saveAndFlush(student);

    }

    public Student get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) { repo.deleteById(id); }

    public void saveDataToExcel(List<Student> students, List<String> fields, String fileName) throws IOException {
        // создаем новую рабочую книгу Excel
        Workbook workbook = new XSSFWorkbook();
        // создаем новый лист в книге Excel
        Sheet sheet = workbook.createSheet("Students");

        // создаем заголовки для столбцов на основе переданных полей
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < fields.size(); i++) {
            headerRow.createCell(i).setCellValue(fields.get(i));
        }

        // заполняем данные для каждого студента на основе переданных полей
        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < fields.size(); i++) {
                String field = fields.get(i);
                switch (field) {
                    case "id":
                        row.createCell(i).setCellValue(student.getId());
                        break;
                    case "Фамилия":
                        row.createCell(i).setCellValue(student.getLast());
                        break;
                    case "Имя":
                        row.createCell(i).setCellValue(student.getFirst());
                        break;
                    case "Номер":
                        row.createCell(i).setCellValue(student.getNum());
                        break;
                    case "Группа":
                        row.createCell(i).setCellValue(student.getNumgroup());
                        break;
                    case "Средний балл":
                        row.createCell(i).setCellValue(student.getAv());
                        break;
                    case "Проблемность":
                        row.createCell(i).setCellValue(student.getProblemstudent());
                        break;
                }
            }
        }

        int numColumns = sheet.getRow(0).getLastCellNum(); // получение количества колонок

        for (int i = 0; i < numColumns; i++) {
            sheet.autoSizeColumn(i); // установка автоматической ширины для каждой колонки
        }

        // сохраняем данные в файл Excel
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

