package com.perfume.backend.pdf;

import com.perfume.backend.dto.request.PdfRequestDto;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generate(PdfRequestDto dto) {

        if (dto == null || dto.getOils() == null || dto.getOils().isEmpty()) {
            throw new IllegalArgumentException("No data provided for PDF generation");
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            String html = PdfTemplateBuilder.build(dto);

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(html, "http://localhost:8080/");

            builder.toStream(out);
            builder.run();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
