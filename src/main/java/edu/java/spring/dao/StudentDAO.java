package edu.java.spring.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import edu.java.spring.model.JavaClazz;
import edu.java.spring.model.Student;
import edu.java.spring.model.StudentMapper;

public class StudentDAO {

	private static Log log = LogFactory.getLog(StudentDAO.class);
	private DataSource dataSource;
	private JdbcTemplate templateObject;
	private String insertSQL;

	public String getInsertSQL() {
		return insertSQL;
	}

	public void setInsertSQL(String insertSQL) {
		this.insertSQL = insertSQL;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.templateObject = new JdbcTemplate(dataSource);
	}
	
	private void createTableIfNotExist(String tableName, String createTableSQL) throws SQLException {
		
		DatabaseMetaData dbmd = dataSource.getConnection().getMetaData();
		java.sql.ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);
		
		if(rs.next()) {
			log.info("Table " + rs.getString("TABLE_NAME") + " already exists !");
			return;
		}
		templateObject.execute(createTableSQL);		
	}
	
	public void shutdown() {
		try{
			DriverManager.getConnection(
					"jdbc:derby:/tmp/studentdb;shutdown=true");
			dataSource.getConnection().close();
		}catch (SQLException e) {
			log.error(e);
		}
	}
	
	public void insert(final Student student) {
		PreparedStatementCreator creator = new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					
				PreparedStatement statement = con.prepareStatement(insertSQL);
				statement.setString(1, student.getName());
				statement.setInt(2, student.getAge());		
				return (java.sql.PreparedStatement) statement;
			}
		};
		templateObject.update(creator);
		log.info("Created Record Name =" + student.getName());
	}
	
	@Autowired @Qualifier("studentMapper") StudentMapper mapper;
	
	public List<Student> listStudents() {
		String sql = "select * from Student";
		List<Student> students = templateObject.query(sql, new StudentMapper());
		return students;
	}
	
	public Student loadStudent(Integer id) {
		String SQL = "select * from Student where id = ?";
		return templateObject.queryForObject(SQL, new Object[]{id}, mapper);
	}
	
	public List<Student> listStudents(String name) {
		String SQL = "select * from Student where name like '%" +name+ "%'";
		List<Student> students = templateObject.query(SQL, new StudentMapper());
		return students;
	}
	
	public void update(Student student) {
		String sql = "update Student set name = ? , age = ? where id = ?";
		templateObject.update(sql, student.getName(), student.getAge(), student.getId());
		log.info("Update Record with ID =" + student.getId());
		return;
	}
	
	public void delete(Integer id) {
		String sql = "delete from Student where id = ?";
		templateObject.update(sql, id);
	}
	
	public JavaClazz getJavaClazz() {
		return new JavaClazz(listStudents());
	}
}
