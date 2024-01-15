package imena.uisrael.docsmanagement.services;

import org.apache.poi.xwpf.usermodel.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.regex.Pattern;

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

    // Define positions and alignments for elements
    enum ElementPosition {
        CENTER,
        LEFT,
        RIGHT,
        RIGHT_SIDE
    }

    public String testDoc() {
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
         return "";

    }

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
