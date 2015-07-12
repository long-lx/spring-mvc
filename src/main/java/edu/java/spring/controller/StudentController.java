package edu.java.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import edu.java.spring.dao.StudentDAO;
import edu.java.spring.model.JavaClazz;
import edu.java.spring.model.Student;
import edu.java.spring.model.StudentMapper;

@Controller

public class StudentController {
	
	@Autowired
	private StudentDAO studentDAO;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home() {
		
		return "redirect:/student/list";
	}
	
	@RequestMapping(value="/student/form", method = RequestMethod.GET)
	public ModelAndView student() {
		
		return new ModelAndView("StudentForm", "command", new Student());
	}
	
	@RequestMapping(value="/student/add", method = RequestMethod.POST)
	public ModelAndView addStudent(@Valid @ModelAttribute("command") Student student, BindingResult result) {
		
		if(result.hasErrors()) {
			ModelAndView model1 = new ModelAndView("StudentForm", "command", student);
			model1.addObject("error", result);
			return model1;
		}
		if(student.getId() > 0) {
			System.out.println("\n\n edit student" + student.getId() + "\n\n");
			studentDAO.update(student);
		}else {
			System.out.println("\n\n edit student " + student.getId() + " " + student.getName() + "\n\n");
			studentDAO.insert(student);
		}		
		ModelAndView model = new ModelAndView("redirect:/student/list");
		return model;
	}
	
	@RequestMapping(value="/student/list", method = RequestMethod.GET)
	public ModelAndView listStudent(@RequestParam(value="q", required=false) String query) {
		
		ModelAndView model = new ModelAndView("StudentList");
		if (query == null){
			model.addObject("students", studentDAO.listStudents());
		}else {
			model.addObject("students", studentDAO.listStudents(query));
		}
		return model;
	}
	
	@RequestMapping(value="/student/view/{id}")
	public String loadStudent(@PathVariable Integer id, ModelMap model) {
		
		Student student = studentDAO.loadStudent(id);
		model.put("id", student.getId());
		model.put("name", student.getName());
		model.put("age", student.getAge());
		return "StudentView";
	}
	
	@RequestMapping(value="/student/edit/{id}",method = RequestMethod.GET)
	public ModelAndView editStudent(@PathVariable Integer id) {
		
		Student student = studentDAO.loadStudent(id);
		ModelAndView model = new ModelAndView("StudentForm", "command", student);
		model.getModelMap().put("id", student.getId());
		model.getModelMap().put("name", student.getName());
		model.getModelMap().put("age", student.getAge());
		return model;
	}
	
	@RequestMapping(value="/student/delete/{id}")
	public ModelAndView delStudent(@PathVariable Integer id) {
		
		studentDAO.delete(id);
		ModelAndView model = new ModelAndView("redirect:/student/list");
		return model;
	}
	
	@RequestMapping(value="/student/xml",produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody JavaClazz viewInXML() {
		
		List<Student> students = studentDAO.listStudents();
		return new JavaClazz(students);
	}
	
	@RequestMapping(value="/student/json",produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JavaClazz viewInJSON() {
		
		List<Student> students = studentDAO.listStudents();
		return new JavaClazz(students);
	}
	
	@RequestMapping(value="/xsl/view")
	public ModelAndView viewXSLT() throws JAXBException, ParserConfigurationException, SAXException, IOException {
		
		JavaClazz clazz = studentDAO.getJavaClazz();
		ModelAndView model = new ModelAndView("ClazzView");
		model.getModelMap().put("data", StudentMapper.clazzToDomSource(clazz));
		return model;
	}
	
	@RequestMapping(value="/xsl/view2", produces = "application/xslt")
	public ModelAndView viewXslts2() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clazzObj", studentDAO.getJavaClazz());
		return new ModelAndView("ClazzView", map);
	}
	
	@RequestMapping(value="/student/pdf", produces = "application/pdf")
	public ModelAndView viewPdf() {
		
		JavaClazz clazz = studentDAO.getJavaClazz();
		return new ModelAndView("pdfView", "clazzObj", clazz);
	}
	
	@RequestMapping(value="/student/excel", produces = "application/excel")
	public ModelAndView viewExcel() {
		
		JavaClazz clazz = studentDAO.getJavaClazz();
		return new ModelAndView("excelView", "clazzObj", clazz);
	}
	
	@RequestMapping(value="/student/report", produces = "application/pdf")
	public ModelAndView viewReport() {
		
		List<Student> students = studentDAO.listStudents();
		JRDataSource dataSource = new JRBeanCollectionDataSource(students);
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("dataSource", dataSource);
		return new ModelAndView("pdfReport", parameterMap);
	}
	
	@RequestMapping(value="/student/json/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Student viewInJSONById(@PathVariable Integer id) {
		
		return studentDAO.loadStudent(id);
	}
	
	@RequestMapping(value="/student/avatar/save", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("id") int id, 
			@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

		
		if(file.isEmpty()) return "StudentError";
		
		ServletContext servletContext = request.getSession().getServletContext();
		String absoluteDiskPath = servletContext.getRealPath("/");
		
		File folder = new File(absoluteDiskPath + File.separator + "avatar" + File.separator);
		if(!folder.exists()) folder.mkdirs();
		File avatarFile = new File(folder, String.valueOf(id) + ".jpg");
		if(!avatarFile.exists()) avatarFile.createNewFile();
		
		FileOutputStream outputStream = null;
		try{
			outputStream = new FileOutputStream(avatarFile);
			outputStream.write(file.getBytes());
		}finally {
			if(outputStream != null) outputStream.close();
		}
		
		return "redirect:/student/view/" + String.valueOf(id);		
	}
	
	@RequestMapping(value="/student/avatar/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable int id, HttpServletRequest request) throws IOException {
		
		ServletContext servletContext = request.getSession().getServletContext();
		String absoluteDiskPath = servletContext.getRealPath("/");
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		
		File folder = new File(absoluteDiskPath + File.separator + "avatar" + File.separator);
		if(folder.exists()) {
			File file = new File(folder, id + ".jpg");
			if(file.exists()) {
				InputStream input = new FileInputStream(file);
				int read;
				byte[] buffer = new byte[100]; 
				while((read=input.read(buffer)) != -1){
					byteOutput.write(buffer, read, 10000);
				}
				input.close();
			}
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(byteOutput.toByteArray(), headers, HttpStatus.CREATED);
	}
}
