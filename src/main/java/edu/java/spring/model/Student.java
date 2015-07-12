package edu.java.spring.model;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@XmlRootElement(name="item")
public class Student {
	private int id;
	
	@NotBlank
	@Size(min=2, max=100)
	private String name;
	
	@Range(min=1, max=150)
	private int age;
	
	public Student(){
		
	};
	
	public void setId(int id) {
		this.id = id;
	}
	@XmlElement
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public String getName() {
		return name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	@XmlElement
	public int getAge() {
		return age;
	}

}
