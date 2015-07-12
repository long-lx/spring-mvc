package edu.java.spring.view;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import edu.java.spring.model.JavaClazz;
import edu.java.spring.model.Student;

public class PdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws DocumentException {
		JavaClazz clazz = (JavaClazz) model.get("clazzObj");
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {2.0f, 3.0f, 1.5f});
		table.setSpacingBefore(10);

		Font font = FontFactory.getFont(FontFactory.COURIER);
		font.setColor(Color.WHITE);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.CYAN);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);

		table.completeRow();

		for (Student student: clazz.getStudents()) {
			table.addCell(String.valueOf(student.getId()));
			table.completeRow();
		}
		doc.add(table);

	}
}
