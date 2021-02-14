package com.tramite_documentario.microservicios.backend.microserviciosolicitudes.view.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tramite_documentario.microservicio.backend.commonpersonas.models.entity.Persona;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.EstadoSolicitud;
import com.tramite_documentario.microservicios.backend.microserviciosolicitudes.models.entity.Solicitud;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Locale;

public class SolicitudPdfView {
    private Solicitud solicitud;

    public SolicitudPdfView(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Paragraph titlePdf = new Paragraph("SOLICITUD DE " + solicitud.getTipoSolicitud().getNombre().toUpperCase(Locale.ROOT));
        titlePdf.setAlignment(Element.ALIGN_CENTER);
        titlePdf.setSpacingAfter(20);
        document.add(titlePdf);

        //Tabla de Solicitante
        PdfPTable tableSolicitante = new PdfPTable(2);
        tableSolicitante.setSpacingAfter(20);
        tableSolicitante.setWidths(new float[] {2f, 3.5f});
        PdfPCell titulo = null;
        PdfPCell cell = null;
        titulo = new PdfPCell(new Phrase("DATOS DEL SOLICITANTE "));
        titulo.setColspan(2);
        titulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        titulo.setBackgroundColor(new Color(184, 218, 255));
        titulo.setPadding(8f);
        tableSolicitante.addCell(titulo);
        //Filas
        tableSolicitante.addCell("DNI/RUC: ");
        tableSolicitante.addCell(solicitud.getPersonaEmisor().getDniRuc());

        tableSolicitante.addCell("APELLIDOS Y NOMBRE: ");
        tableSolicitante.addCell(solicitud.getPersonaEmisor().getApellidos() + ", " + solicitud.getPersonaEmisor().getNombre());

        tableSolicitante.addCell("PUESTO: ");
        tableSolicitante.addCell(solicitud.getPersonaEmisor().getPuesto().getNombre());

        tableSolicitante.addCell("EMAIL: ");
        tableSolicitante.addCell(solicitud.getPersonaEmisor().getCorreo());


        //Tabla de datos de Destinatarios
        PdfPTable tableDestinatarios = new PdfPTable(4);
        tableDestinatarios.setSpacingAfter(20);
        tableDestinatarios.setWidths(new float[] {1, 2, 1, 2f});
        //Titulo para la tabla
        titulo = new PdfPCell(new Phrase("DATOS DE RECEPTORES"));
        titulo.setPadding(8f);
        titulo.setColspan(4);
        titulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        titulo.setBackgroundColor(new Color(195, 230, 203));
        tableDestinatarios.addCell(titulo);

        //Llenando los encabezadons de cada columna
        tableDestinatarios.addCell("DNI/RUC");
        tableDestinatarios.addCell("APELLIDOS Y NOMBRE");
        tableDestinatarios.addCell("PUESTO");
        tableDestinatarios.addCell("EMAIL");

        //Llenando cada fila de personas Receptoras
        for (Persona receptora: solicitud.getPersonasReceptoras()){
            tableDestinatarios.addCell(receptora.getDniRuc());
            tableDestinatarios.addCell(receptora.getApellidos() + ", " + receptora.getNombre());
            tableDestinatarios.addCell(receptora.getPuesto().getNombre());
            tableDestinatarios.addCell(receptora.getCorreo());
        }


        //Tabla de estados
        PdfPTable tableEstados = new PdfPTable(3);
        tableEstados.setSpacingAfter(20);
        tableEstados.setWidths(new float[] {1.5f, 1.5f, 2.5f});
        //Titulo de la tabla
        titulo = new PdfPCell(new Phrase("Estado de la Solicitud"));
        titulo.setPadding(8f);
        titulo.setColspan(3);
        titulo.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        titulo.setBackgroundColor(new Color(225, 100, 103));

        tableEstados.addCell("Estado");
        tableEstados.addCell("Fecha");
        tableEstados.addCell("Descripcion");


        for (EstadoSolicitud estadoSolicitud: solicitud.getEstadoSolicitudes()){
            tableEstados.addCell(estadoSolicitud.getEstado().getNombre());
            tableEstados.addCell(estadoSolicitud.getFecha().toString());
            tableEstados.addCell(estadoSolicitud.getDescripcion());
        }


        document.add(tableSolicitante);
        document.add(tableDestinatarios);
        document.add(tableEstados);
        document.close();
    }
}
