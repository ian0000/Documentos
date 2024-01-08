package imena.uisrael.docsmanagement.services;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoDocumentos;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosJson;
import imena.uisrael.docsmanagement.model.Parametros;
import imena.uisrael.docsmanagement.repo.ParametrosRepo;

@Service
public class DocumentosService {
    @Autowired
    ParametrosRepo parametrosRepo;

    public String saveDocumentos() {
        Parametros parametros = parametrosRepo.findByNombreParametro("nuevoparametro", "Vw2WJzh46KHLqHiK");
        if (parametros != null) {

            ParametrosJson parametrosJson = GeneralFunctions.converParamToJSON(parametros);
            try (XWPFDocument document = new XWPFDocument()) {
                // Create a new paragraph for the header
                XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                XWPFParagraph headerParagraph = header.createParagraph();
                XWPFRun headerRun = headerParagraph.createRun();
                headerRun.setText(parametrosJson.header.titulo);

                // Create a new paragraph for the footer
                XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
                XWPFParagraph footerParagraph = footer.createParagraph();
                XWPFRun footerRun = footerParagraph.createRun();
                footerRun.setText(parametrosJson.footer.firma);

                // Create document content
                XWPFParagraph titleParagraph = document.createParagraph();
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setText(parametrosJson.nombreParametro);
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
        } else {
            return "";
        }
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
