package org.cuatrovientos.cilveti.PrimerProyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class App {

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException {
		  int option ;
	        String name = "";
	        String idCard = "";
	        Boolean repeating = false;
	        Scanner reader = new Scanner(System.in);
	        Class.forName("org.sqlite.JDBC");
	        
	        // creamos db
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement statement = connection.createStatement();
			String sql = "create table clients (id integer, name varchar(30))";
			//statement.executeUpdate(sql); 
			// creamos un prepared statement para introducir clientes más fácilmente
			PreparedStatement crearCliente =					
					connection.prepareStatement("insert into clients values (?,?)");
			//creamos un prepared statement para eliminar clientes más fácilmente via nombre
			PreparedStatement eliminarPorNombre =
					connection.prepareStatement("delete from clients where name =?");
			//creamos un prepared statement para eliminar clientes más fácilmente via id
			PreparedStatement eliminarPorId =
					connection.prepareStatement("delete from clients where id =?");
			
			do {
	        	showMenu();
	        	option = Integer.parseInt(reader.nextLine());
	        	
	        	switch (option) {
	        	case 1:
	        		String select = "select * from clients order by name desc";
	        		ResultSet resultSet = statement.executeQuery(select);
	        		
	        		while (resultSet.next()) {
	        			//System.out.print("ID: " + resultSet.getString(1));
	        			//System.out.println(" Name: " + resultSet.getString(2));
	        			System.out.print("ID: " + resultSet.getInt("id"));
	        			System.out.println(" Name: " + resultSet.getString("name"));
	        		}
	        		break;
	        	case 2:
	        		boolean siguiente = false;
	        		do {
	        			System.out.print("Introduzca nombre: ");
	        			name = reader.nextLine();
	        			System.out.print("Introduzca id: ");
	        			
	        			idCard = reader.nextLine();
	        			crearCliente.setInt(1, Integer.parseInt(idCard));
	        			crearCliente.setString(2, name);
	        			crearCliente.addBatch();
	        			System.out.print("añadir más alumnos? (s/n): ");
	        			if (reader.nextLine()== "s"){
	        				siguiente = true;
	        			}
	        			
	        		}while(siguiente);
	        		crearCliente.executeBatch();
	        		
	        		break;
	        	case 3:
	        		System.out.print("Introduzca el nombre del cliente a eliminar(enviaremos a un hombre sin cara a eliminarlo): ");
	        		name = reader.nextLine();
	        		eliminarPorNombre.setString(1, name);
	        		int val = eliminarPorNombre.executeUpdate();
	        		if (val!= 0){
	        			if (val == 1){
	        				System.out.println("El cliente con nombre "+name+" fue eliminado satisfactoriamente");
	        				}
	        				else{
	        				System.out.println(val+" clientes con nombre "+name+" fueron eliminados satisfactoriamente");
	        				}
	        			}
	        		else{
	        			System.out.println("No existe ningun cliente con ese nombre");
	        		}
	        		break;
	        	case 4:
	        		System.out.print("Introduzca el id del cliente a eliminar(desaparecerá por combustión espontánea): ");
	        		idCard = reader.nextLine();
	        		eliminarPorId.setInt(1, Integer.parseInt(idCard));
	        		int val1 = eliminarPorId.executeUpdate();
	        		if (val1!= 0){
	        			if (val1 == 1){
	        				System.out.println("El cliente con id "+idCard+" fue eliminado satisfactoriamente");
	        				}
	        				else{
	        				System.out.println(val1+" clientes con id "+idCard+" fueron eliminados satisfactoriamente");
	        				}
	        			}
	        		else{
	        			System.out.println("No existe ningun cliente con esa id");
	        		}
	        		break;
	        	
	        	case 5:
	        		String removeAll = "delete from clients";
	        		
	        		break;
	        	case 6:
	        		System.out.println("Thank you!");
	        		break;
	        	}
	        	
	        } while (option != 5);
			
			connection.close();
	    }
	
private static void showMenu() {
	System.out.println("Please select option:");
	System.out.println("1. Show all clients");
	System.out.println("2. Add new client");
	System.out.println("3. Remove client via name");
	System.out.println("4. Remove client via id");
	System.out.println("5. Remove all clients");
	System.out.println("6. Exit");
	System.out.println("Please select option: ");
}


}