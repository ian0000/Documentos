package imena.uisrael.docsmanagement.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.hibernate.internal.HEMLogging;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
        CENTER,
        LEFT,
        RIGHT,
        RIGHT_SIDE
    }
public String testDoc(){
    byte[] wordDocumentBytes = createWordDocument("[test]");

        // Convert byte array to Base64
        String base64String = Base64.getEncoder().encodeToString(wordDocumentBytes);

        // Print the Base64-encoded string
        System.out.println(base64String);
    return "test";
}
private static byte[] createWordDocument(String content) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        // Create a new Word document
        XWPFDocument document = new XWPFDocument();

        // Add a paragraph with the specified content
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);

        // Write the document to the output stream
        document.write(outputStream);

        // Close the document and the output stream
        document.close();
        outputStream.close();

        // Get the byte array
        return outputStream.toByteArray();
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
    public String saveDocumentos() {
        Parametros parametros = parametrosRepo.findByNombreParametro("nuevoparametro", "Vw2WJzh46KHLqHiK");
        if (parametros != null) {

            ParametrosJson parametrosJson = GeneralFunctions.converParamToJSON(parametros);
            try (XWPFDocument document = new XWPFDocument()) {
                //XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                XWPFParagraph headerParagraph = document.createParagraph();

                // Set the header text
                XWPFRun run = headerParagraph.createRun();
                run.setText("Header 1\tHeader 2\tHeader 3\tHeader 4");
    
                // Create a table for the data
                int numRows = 10;
                int numColumns = 4;
                XWPFTable table = document.createTable(numRows, numColumns);
    
                // Populate the table with data (example)
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numColumns; col++) {
                        table.getRow(row).getCell(col).setText("Data " + (row + 1) + "," + (col + 1));
                    }
                }
    
                // Save the document to a file
                try (FileOutputStream out = new FileOutputStream("output.docx")) {
                    document.write(out);
                }
                // createHeaderPart(header, "Left Part", ParagraphAlignment.LEFT);
                // createHeaderPart(header, "Center Part", ParagraphAlignment.CENTER);
                // createHeaderPart(header, "Right Part", ParagraphAlignment.RIGHT);
                // XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                // addContent(header, parametrosJson.header.titulo, ElementPosition.TOP_CENTER,
                // false, null, parametrosJson.generics);
                // addContent(header, parametrosJson.header.subtitulo,
                // ElementPosition.TOP_CENTER,
                // false, null, parametrosJson.generics);
                // addContent(header, parametrosJson.header.fecha, ElementPosition.RIGHT_SIDE,
                // false, null, parametrosJson.generics);

                // // Create a new paragraph for the footer
                // XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
                // addContent(footer, parametrosJson.footer.informacioncontacto,
                // ElementPosition.BELOW_TITLE, false, null,
                // parametrosJson.generics);
                // addContent(footer, parametrosJson.footer.firma, ElementPosition.BELOW_TITLE,
                // false, null,
                // parametrosJson.generics);

                // Save the document
                FileOutputStream out = new FileOutputStream(parametrosJson.nombreParametro +
                        "GeneratedDocument.docx");
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
    // void addContent(Object container, String text, ElementPosition position,
    // boolean isPicture,
    // String imagePath, ParamGenerics generics) {
    // XWPFParagraph paragraph;

    // if (container instanceof XWPFHeader) {
    // paragraph = ((XWPFHeader) container).createParagraph();
    // } else if (container instanceof XWPFFooter) {
    // paragraph = ((XWPFFooter) container).createParagraph();
    // } else if (container instanceof XWPFParagraph) {
    // paragraph = (XWPFParagraph) container;
    // } else {
    // throw new IllegalArgumentException("Container must be XWPFHeader, XWPFFooter,
    // or XWPFParagraph");
    // }
    // XWPFRun run = paragraph.createRun();
    // run.setText(text);
    // run.setBold(position == ElementPosition.TOP_CENTER);
    // switch (position) {
    // case TOP_CENTER:
    // paragraph.setAlignment(ParagraphAlignment.CENTER);
    // break;
    // case LEFT_SIDE:
    // paragraph.setAlignment(ParagraphAlignment.LEFT);
    // break;
    // case RIGHT_SIDE:
    // paragraph.setAlignment(ParagraphAlignment.RIGHT);
    // break;
    // default:
    // break;
    // }

    // run.setFontSize(generics.fontSize);
    // run.setFontFamily(generics.font);
    // if (isValidHexColor(generics.fontColor)) {
    // if (generics.fontColor.startsWith("#")) {
    // generics.fontColor = generics.fontColor.substring(1);
    // }

    // } else {
    // generics.fontColor = "0000FF";
    // }
    // run.setColor(generics.fontColor);
    // }

    public static boolean isValidHexColor(String color) {
        String hexPattern = "^#?([0-9a-fA-F]{6})$";
        Pattern pattern = Pattern.compile(hexPattern);
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
