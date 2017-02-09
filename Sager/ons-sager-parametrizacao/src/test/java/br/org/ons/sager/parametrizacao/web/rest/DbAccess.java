package br.org.ons.sager.parametrizacao.web.rest;
import java.sql.*;

public class DbAccess
{
    public static void main(String[] args)
    {
        try
        {
        	Connection conn=DriverManager.getConnection(
        	        "jdbc:ucanaccess://C:/Temp/BD.accdb");
        	Statement s = conn.createStatement();
        	ResultSet rs = s.executeQuery("SELECT [id] FROM [Usinas]");
        	ResultSet rs2 = s.executeQuery("CT_UC02401-Consulta Agendamento de TODAS as Instalações por Tipo");
        	
        	while (rs.next()) {
        	    System.out.println(rs.getString(1));
        	}
        	while (rs2.next()) {
        	    System.out.println(rs.getString(1));
        	}   
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}