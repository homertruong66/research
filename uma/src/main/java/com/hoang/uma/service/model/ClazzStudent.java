package com.hoang.uma.service.model;

import javax.persistence.*;

@Entity
@Table(
    name="clazzes_students",
    uniqueConstraints={
        @UniqueConstraint(columnNames={"clazz_id", "student_id"})
    }
)
public class ClazzStudent {

    @Id
    private String id;

    // Associations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    // Get/Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
