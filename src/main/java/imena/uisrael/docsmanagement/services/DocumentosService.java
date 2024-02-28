package imena.uisrael.docsmanagement.services;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imena.uisrael.docsmanagement.DTO.ObjetoDocumentos;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.ParametrosJson;
import imena.uisrael.docsmanagement.DTO.ObjetoParametros.Placeholder;
import imena.uisrael.docsmanagement.model.Parametros;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasDocumentos;
import imena.uisrael.docsmanagement.model.Parciales.RespuestasParametros;
import imena.uisrael.docsmanagement.repo.ParametrosRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public String testDoc(byte[] document) {

        // Convert byte array to Base64
        String base64String = Base64.getEncoder().encodeToString(document);

        return base64String;
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
        Parametros parametros = parametrosRepo.findByNombreParametro("test", "Vw2WJzh46KHLqHiK");
        if (parametros != null) {
            ParametrosJson parametrosJson = GeneralFunctions.converParamToJSON(parametros);

            byte[] decodedBytes = Base64.getDecoder().decode(parametrosJson.documents.document);
            try (InputStream is = new ByteArrayInputStream(decodedBytes)) {
                XWPFDocument document = new XWPFDocument(is);
                // Process headers

                List<XWPFHeader> headers = document.getHeaderList();
                for (XWPFHeader header : headers) {
                    processDocumentPart(header, parametrosJson.placeholders);
                }

                // Process footers
                List<XWPFFooter> footers = document.getFooterList();
                for (XWPFFooter footer : footers) {
                    processDocumentPart(footer, parametrosJson.placeholders);
                }
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    processParagraph(paragraph, parametrosJson.placeholders);
                }
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                    document.write(outputStream);

                    String fileName1 = "your_document.docx";
                    try (FileOutputStream fos = new FileOutputStream(fileName1)) {
                        document.write(fos);
                    }

                    document.close();
                    outputStream.close();
                    // Get the byte array
                    return outputStream.toByteArray().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return RespuestasParametros.PARAMETRONOEXISTE;

    }

    public static boolean isValidHexColor(String color) {
        String hexPattern = "^#?([0-9a-fA-F]{6})$";
        Pattern pattern = Pattern.compile(hexPattern);
        return pattern.matcher(color).matches();
    }

    private void processDocumentPart(IBody part, List<Placeholder> parametrosJson) {
        for (IBodyElement element : part.getBodyElements()) {
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                processParagraph(paragraph, parametrosJson);
            }
        }
    }

    private void processParagraph(XWPFParagraph paragraph, List<Placeholder> parametrosJson) {
        StringBuilder paragraphText = new StringBuilder();

        // Concatenate the text of all runs into a single string
        for (XWPFRun run : paragraph.getRuns()) {
            paragraphText.append(run.getText(0));
        }

        String originalText = paragraphText.toString();
        String modifiedText = originalText;

        // Find all occurrences of placeholders in the text
        Matcher matcher = Pattern.compile("\\[(.*?)\\]").matcher(originalText);

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            Optional<Placeholder> optionalPlaceholder = parametrosJson.stream()
                    .filter(x -> placeholder.equals(x.placeholderName))
                    .findFirst();

            if (optionalPlaceholder.isPresent()) {
                // Replace all occurrences of the placeholder with the value from the JSON
                // structure
                modifiedText = modifiedText.replace("[" + placeholder + "]",
                        optionalPlaceholder.get().placeholderValue);
            }
        }

        // Clear existing runs in the paragraph
        for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }

        // Create a new run and set the modified text
        XWPFRun newRun = paragraph.createRun();
        newRun.setText(modifiedText, 0);
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
