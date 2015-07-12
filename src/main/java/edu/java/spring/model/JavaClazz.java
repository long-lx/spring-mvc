package edu.java.spring.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="clazz")
public class JavaClazz {
	
	private List<Student> students;
	private Student student;
	
	public JavaClazz() {
		
	}
	
	public JavaClazz(List<Student> students) {
		this.students = students;
	}
	
	public JavaClazz(Student student) {
		this.student = student;
	}
	
	@XmlElements(@XmlElement(name="student", type=Student.class))
	public List<Student> getStudents() { return students; }

}
