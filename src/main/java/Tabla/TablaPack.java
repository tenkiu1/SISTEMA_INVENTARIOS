
package Tabla;

import Modelo.Packing;
import Modelo.PackingDAO;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class TablaPack {
     PackingDAO dao = null;

    public void visualizar_PdfVO(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new imgTabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dt.addColumn("codigopdf");
        dt.addColumn("almacenpdf");
        dt.addColumn("proveedorpdf");
        dt.addColumn("archivopdf");
        dt.addColumn("fechapdf");

        ImageIcon icono = null;
        if (get_Image("/Imagen/32pdf.png") != null) {
            icono = new ImageIcon(get_Image("/Imagen/32pdf.png"));
        }

        dao = new PackingDAO();
        Packing vo = new Packing();
        List<Packing> list = dao.listar();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[5];
                vo = list.get(i);
                fila[0] = vo.getCodigo();
                fila[1] = vo.getAlmacen();
                fila[2] = vo.getProveedor();
                fila[4] = vo.getFecha();
                if (vo.getArchivo() != null) {
                    fila[3] = new JButton(icono);
                } else {
                    fila[3] = new JButton("Vacio");
                }

                dt.addRow(fila);
            }
            tabla.setModel(dt);
            tabla.setRowHeight(32);
        }
    }

    public Image get_Image(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta));
            Image mainIcon = imageIcon.getImage();
            return mainIcon;
        } catch (Exception e) {
        }
        return null;
    }
}
