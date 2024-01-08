package imena.uisrael.docsmanagement.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.hibernate.internal.HEMLogging;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoDocumentos;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParamGenerics;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosJson;
import imena.uisrael.docsmanagement.model.Parametros;
import imena.uisrael.docsmanagement.repo.ParametrosRepo;

@Service
public class DocumentosService {
    @Autowired
    ParametrosRepo parametrosRepo;

    // Define positions and alignments for elements
    enum ElementPosition {
        TOP_CENTER,
        BELOW_TITLE,
        BELOW_SUBTITLE,
        LEFT_SIDE,
        RIGHT_SIDE
    }

    public String saveDocumentos() {
        Parametros parametros = parametrosRepo.findByNombreParametro("nuevoparametro", "Vw2WJzh46KHLqHiK");
        if (parametros != null) {

            ParametrosJson parametrosJson = GeneralFunctions.converParamToJSON(parametros);
            try (XWPFDocument document = new XWPFDocument()) {
                XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                addContent(header, parametrosJson.header.titulo, ElementPosition.TOP_CENTER,
                        false, null, parametrosJson.generics);
                addContent(header, parametrosJson.header.subtitulo, ElementPosition.TOP_CENTER,
                        false, null, parametrosJson.generics);
                addContent(header, parametrosJson.header.fecha, ElementPosition.RIGHT_SIDE,
                        false, null, parametrosJson.generics);

                // XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                // XWPFParagraph headerParagraph = header.createParagraph();
                // XWPFRun headerRun = headerParagraph.createRun();
                // headerRun.setText(parametrosJson.header.titulo);
                // XWPFRun orgNameRun = headerParagraph.createRun();
                // orgNameRun.setText(parametrosJson.header.nombreorganizacion);
                // XWPFRun dateRun = headerParagraph.createRun();
                // dateRun.setText("Date: " + parametrosJson.header.fecha);

                // Create a new paragraph for the footer
                XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
                XWPFParagraph footerParagraph = footer.createParagraph();
                XWPFRun footerRun = footerParagraph.createRun();
                footerRun.setText(parametrosJson.footer.firma);

                // Create document content
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setText(parametrosJson.nombreParametro);
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setColor("0000FF");

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

    // Create a method to add content at a specific position
    void addContent(XWPFHeader header, String text, ElementPosition position, boolean isPicture,
            String imagePath, ParamGenerics generics) {
        XWPFParagraph headerParagraph = header.createParagraph();
        XWPFRun headerRun = headerParagraph.createRun();
        headerRun.setText(text);
        headerRun.setBold(position == ElementPosition.TOP_CENTER);
        switch (position) {
            case TOP_CENTER:
                headerParagraph.setAlignment(ParagraphAlignment.CENTER);
                break;
            case LEFT_SIDE:
                headerParagraph.setAlignment(ParagraphAlignment.LEFT);
                break;
            case RIGHT_SIDE:
                headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
                break;
            default:
                break;
        }

        // Adjust formatting as needed
        headerRun.setFontSize(generics.fontSize);
        headerRun.setFontFamily(generics.font);
        if (isValidHexColor(generics.fontColor)) {
            if (generics.fontColor.startsWith("#")) {
                generics.fontColor = generics.fontColor.substring(1);
            }

        } else {
            generics.fontColor = "0000FF";
        }
        headerRun.setColor(generics.fontColor);
        // Make title bold
    }

    public static boolean isValidHexColor(String color) {
        // Regular expression for a 6-digit hexadecimal number
        String hexPattern = "^#?([0-9a-fA-F]{6})$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(hexPattern);

        // Check if the color matches the pattern
        return pattern.matcher(color).matches();
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
