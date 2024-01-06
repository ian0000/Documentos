package imena.uisrael.docsmanagement.services;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoDocumentos;

@Service
public class DocumentosService {

    public String saveDocumentos() {
        String documentTitle = "Sample Document";
        String headerContent = "Header Text";
        String footerContent = "Footer Text";

        try (XWPFDocument document = new XWPFDocument()) {
            // Create a new paragraph for the header
            XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
            XWPFParagraph headerParagraph = header.createParagraph();
            XWPFRun headerRun = headerParagraph.createRun();
            headerRun.setText(headerContent);

            // Create a new paragraph for the footer
            XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
            XWPFParagraph footerParagraph = footer.createParagraph();
            XWPFRun footerRun = footerParagraph.createRun();
            footerRun.setText(footerContent);

            // Create document content
            XWPFParagraph titleParagraph = document.createParagraph();
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText(documentTitle);
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            // Other content can be added similarly

            // Save the document
            FileOutputStream out = new FileOutputStream("GeneratedDocument.docx");
            document.write(out);
            out.close();

            System.out.println("Word document generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String updateDocumentos(ObjetoDocumentos objeto) {
        return "";
    }

    public String stateDocumentos(ObjetoDocumentos objeto) {
        return "";
    }

    public String listDocumentos(ObjetoDocumentos objeto) {
        return "";
    }
}
