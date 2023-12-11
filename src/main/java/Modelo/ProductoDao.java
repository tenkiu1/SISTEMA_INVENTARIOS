/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author jorge
 */
public class ProductoDao extends Conexion {
    
    Connection cone = null;
    Statement stm = null;
    PreparedStatement pst = null;
    
    
    public JasperPrint reporteTodo(){
        cone= getConnection();
        File reporte = new File(getClass().getResource("/jasper/ingreso.jasper").getFile());
        if(!reporte.exists()){
            return null;
        }
        try {
            InputStream is= new BufferedInputStream(new FileInputStream(reporte.getAbsoluteFile()));
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                JasperPrint jp = JasperFillManager.fillReport(jr,null,cone);
                return jp;
            } catch (JRException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
