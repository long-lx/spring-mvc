package edu.java.spring.view;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;

import org.springframework.web.servlet.view.xslt.XsltView;
import org.xml.sax.SAXException;

import edu.java.spring.model.JavaClazz;
import edu.java.spring.model.StudentMapper;

public class ClazzXsltView extends XsltView {
	
	protected Source locateSource(Map model) throws JAXBException, ParserConfigurationException, SAXException, IOException {
		
		JavaClazz clazz = (JavaClazz) model.get("clazzObj");
		return StudentMapper.clazzToDomSource(clazz);
	}
}
