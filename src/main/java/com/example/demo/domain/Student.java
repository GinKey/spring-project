package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Student {
    private Long id; // ID
    private String first; // Имя
    private String last; // Фамилия
    private String num; // номер студенческого
    private String numgroup;
    private float av; // средний балл
    private String problemstudent; // проблемность

    public Student() {
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public float getAv() {return av;}

    public void setAv(float av) {this.av = av;}
    public String getProblemstudent() {return problemstudent;}
    public void setProblemstudent(String problemstudent) {this.problemstudent = problemstudent;}

    public String getNumgroup() {return numgroup;}
    public void setNumgroup(String numgroup) {this.numgroup = numgroup;}

    @Override
    public String toString() {
        return "student [id=" + id + ", first=" + first +", last=" + last + ", num=" + num + ", numgroup=" + numgroup + ", av=" + av + ", problemstudent=" + problemstudent +"]";
    }


}
