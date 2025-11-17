
package Modelo;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    private String bd; //nombre de la base de datos
    private String url;
    private String user;
    private String password;
    private String driver;
    private Connection conn;

    public Conexion(String bd, String url, String user, String password, String driver) {
        this.bd = bd;
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }
    
    public Connection conectar(){
        if(conn == null){
            try {
                Class.forName(driver);
                conn=(Connection) DriverManager.getConnection(url + bd,user,password);
                System.out.println("Conexion establecida con la base de datos");    
            } catch (ClassNotFoundException ex) {
                System.out.println("Error no se encontro el Driver: " + ex.getMessage() );
            } catch (SQLException ex) {
                System.out.println("Error en la conexion " +ex.getMessage());;
            }
            
        }
        return conn ;
    }
    
}
