package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import com.example.demo.domain.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired

    private StudentService service;


    @RequestMapping("/")
    public String Homepage()
    {
        return "login";
    }

    @RequestMapping("/test")
    public String testpage()
    {
        return "test";
    }

    @RequestMapping("/contact")
    public String viewcontact_info()
    {
        return "contact";
    }


    @RequestMapping("/index")
    public String viewHomePage(Model model, @Param("keyword") String keyword,
                               @RequestParam(value = "find_problem", required = false, defaultValue = "false") boolean find_problem)
    {
        List<Student> listStudents = service.listAll(keyword, find_problem);
        model.addAttribute("listStudents", listStudents);
        model.addAttribute("keyword", keyword);

        return "index";
    }

    @RequestMapping("/new")
    public String showNewStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "new_student";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/index2")
    public String UpdateStudentInfo(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "index2";
    }

    @RequestMapping("/gistogram")
    public String showGraph(Model model, @Param("keyword") String keyword) {

        List<Student> listStudents = service.listAll(keyword, false);
        model.addAttribute("listStudents", listStudents);
        model.addAttribute("keyword", keyword);
        return "gistogram";

    }

    @PostMapping("/update")
    public String saveStudents(@RequestBody List<Student> listStudents) {
        System.out.println(listStudents);
        for (Student studentDto : listStudents) {
            Student student = new Student();
            student.setId(studentDto.getId());
            student.setFirst(studentDto.getFirst());
            student.setLast(studentDto.getLast());
            student.setNum(studentDto.getNum());
            student.setNumgroup(studentDto.getNumgroup());
            student.setAv(studentDto.getAv());
            student.setProblemstudent(studentDto.getProblemstudent());
            System.out.println(student);
            service.update(student);
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.save(student);
        return "redirect:/index";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditStudentForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_student");
        Student student = service.get(id);
        mav.addObject("student", student);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/index";
    }

    @PostMapping("/download-excel")
    public ResponseEntity<Resource> downloadExcelFile(@RequestParam(value = "fields[]", required = false) List<String> fields) throws IOException {

        System.out.println(fields);

        List<Student> students = service.listAll(null, false);
        String fileName = "src/main/resources/students.xlsx";
        service.saveDataToExcel(students, fields, fileName);

        Resource resource = new FileSystemResource(fileName);


        if (!resource.exists()) {
            throw new RuntimeException("Excel file not found");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xls");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

}

