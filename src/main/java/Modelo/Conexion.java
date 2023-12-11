//
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import net.sf.jasperreports.engine.JasperPrint;


public class Conexion {
    Connection con;
    public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/inventarios";
        String user="root";
        String pass="";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=DriverManager.getConnection(url,user,pass);
    }
    catch(Exception e){
    
    }
    return con;
    }
    
    
    
    
}
