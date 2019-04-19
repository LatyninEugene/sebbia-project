package controlers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Canvas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class GeneratePDF {



    public static File getPDF(String path, Canvas c,String name) throws IOException, DocumentException, URISyntaxException {
        File file = new File(path);
        try {
            BaseFont bf = BaseFont.createFont(GeneratePDF.class.getResource("../ariali.ttf").toString(),"windows-1251", BaseFont.EMBEDDED);
            Document document = new Document();
            document.setPageSize(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Map<String, String[]> map = c.getBloks();
            Map<String, int[]> mapPos = c.getBloksPos();
            int x = c.getX();
            int y = c.getY();

            PdfPTable table = new PdfPTable(x);
            Font font = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
            Chunk chunkName = new Chunk(name, new Font(bf,18));
            chunkName.setLineHeight(12);
            document.add(chunkName);
            document.setMargins(64, 64, 8, 64);
            table.setWidthPercentage(100f);
            table.setSpacingBefore(12);

            for (int i = 1; i <= y; i++) {
                for (int j = 1; j <= x; j++) {
                    for (String s : map.keySet()) {
                        int[] pos = mapPos.get(s);
                        if (pos[0] == j && pos[1] == i) {
                            PdfPCell cell = new PdfPCell();
                            cell.setPadding(8);
                            cell.setPaddingTop(2);
                            String[] str = map.get(s);
                            cell.addElement(new Phrase(str[0], new Font(bf,14, Font.BOLD)));
                            cell.addElement(new Phrase(str[2], new Font(bf,12)));
                            cell.setRowspan(pos[2]);
                            cell.setColspan(pos[3]);
                            table.addCell(cell);
                        }
                    }
                }
            }
            document.add(table);
            document.close();
        }catch (Exception e){
            System.out.println("Error");
            System.out.println(e.toString());
        }
        return file;

    }

}
