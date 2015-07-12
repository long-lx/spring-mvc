package edu.java.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import edu.java.spring.model.JavaClazz;
import edu.java.spring.model.Student;

public class ExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook wb, 
			HttpServletRequest request, HttpServletResponse response) {
		
		Sheet sheet = wb.createSheet("Java Clazz");
		
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Age");
		
		JavaClazz clazz = (JavaClazz) model.get("clazzObj");
		for (int i = 0; i < clazz.getStudents().size(); i++) {
			Student student = clazz.getStudents().get(i);
			HSSFRow row = (HSSFRow) sheet.createRow(i+1);
			row.createCell(0).setCellValue(student.getId());
			row.createCell(1).setCellValue(student.getName());
			row.createCell(2).setCellValue(student.getAge());
		}
	}
}
