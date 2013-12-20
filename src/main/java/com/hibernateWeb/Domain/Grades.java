package com.hibernateWeb.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="Grades")
public class Grades {
	
	@Id	
	@GenericGenerator(name="generator", strategy="foreign",
	parameters = @Parameter(name="property", value="student"))
	@GeneratedValue(generator="generator")
	@Column(name="student_id")
	private Long studentId;
	
	@Column(name="math")
	private int math;
	
	@Column(name="science")
	private int science;
	
	@Column(name="english")
	private int english;
	
	@OneToOne @PrimaryKeyJoinColumn
	private Student student;
	
	private Grades(){
		
	}

	public Grades(int math, int science, int english) {
		super();
		this.math = math;
		this.science = science;
		this.english = english;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getScience() {
		return science;
	}

	public void setScience(int science) {
		this.science = science;
	}

	public int getEnglish() {
		return english;
	}

	public void setEnglish(int english) {
		this.english = english;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
}
