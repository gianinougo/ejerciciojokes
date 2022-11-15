package com.ugo.ejerciciojokes.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MenuAplication {

	public void ejecutar() throws SQLException {
		
		Connection conn = null;
		
		
		try {
			
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/jokes";
			String usuario = "postgres";
			String password = "0000";
			conn = DriverManager.getConnection(url, usuario, password);
			Scanner sc = new Scanner(System.in);
			
			int option;
			
			do {
				System.out.println("1. Reset DataBase");
		        System.out.println("2. Add Joke Statement");
		        System.out.println("3. Add Joke PreparedStatement");
		        System.out.println("4. Search with Text");
		        System.out.println("5. Jokes without flags");
		        System.out.println("6. Exit");
		        System.out.println("7. Serialize");
		        System.out.print("Choose an option: ");
		        option = sc.nextInt();
		        sc.nextLine();
				
		        switch(option)
		        {
		        	case 1: resetDataBase(conn); break;
		        	case 2: addJokesStatement(conn); break;
		        	case 3: addJokesPreparedStatement(conn); break;
		        	case 4: searchJokes(conn); break;
		        	case 5: jokesWithOutFlags(conn); break;
		        	case 7: callAPI(); break;
		        	case 6: System.out.println("Bye"); break;
		        	default: System.out.println("Wrong option"); break;
		        }
		        System.out.println();
				
			} while(option != 6);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		 		System.out.println("Terminado!"); 
}
			

	private void jokesWithOutFlags(Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void searchJokes(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	private void addJokesStatement(Connection conn) {
		
		Statement statement = null;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the name of the joke: ");
		String jokesName = sc.nextLine();
		
		String sql = "INSERT INTO JOKES (NOMBRE) VALUES ('" +
					jokesName + "')";
		
		try {
			statement = conn.createStatement();
			int affectedRows = statement.executeUpdate(sql);
			System.out.print("Affected rows: " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
						
		
	}

	private void addJokesPreparedStatement(Connection conn) {
		
		PreparedStatement ps = null;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the name of the joke: ");
		String jokesName = sc.nextLine();
		
	
		try {
			
			ps = conn.prepareStatement("INSERT INTO JOKES " + 
							"(NOMBRE) VALUES (?)");
			
			ps.setString(1, jokesName);
			int affectedRows2 = ps.executeUpdate();
			System.out.println("Affected rows: " + affectedRows2);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void resetDataBase(Connection conn) {
		
		Statement statement = null;
		
		
		String sql = "DROP TABLE IF EXISTS jokes;"
				+ "CREATE TABLE jokes ("
				+ "nombre VARCHAR(50)"
				+ ");";
		
		try {
			statement = conn.createStatement();
			int affectedRows = statement.executeUpdate(sql);
			System.out.print("Affected rows: " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Parsear json, iterar los jokes, insertar los elementos que correspondan
	
		
	private void callAPI() throws IOException {
		
		String url = "https://v2.jokeapi.dev/joke/Any";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		           new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());
		
		
		JSONObject jsonObject  = new JSONObject(response.toString());
		
		String category = jsonObject.getString("category");
		String type = jsonObject.getString("type");
		JSONObject flags = jsonObject.getJSONObject("flags");
		Boolean nsfw = flags.getBoolean("nsfw");
		
		if(!type.equals("single")) {

			int id = jsonObject.getInt("id");
			Boolean safe = jsonObject.equals("safe");
			String setup = jsonObject.getString("setup");
			String delivery = jsonObject.getString("delivery");
			String language = jsonObject.getString("lang");
			
			Joke j = new Joke(category, type, flags ,id, safe, setup ,delivery, language);

			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(j);

			System.out.println(json);
			
		} else {
			String joke = jsonObject.getString("joke");
			int id = jsonObject.getInt("id");
			Boolean safe = jsonObject.equals("safe");
			String language = jsonObject.getString("lang");
			Joke j = new Joke(category, type, joke ,id, safe, language);
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(j);

			System.out.println(json);
		}


		
		
		
		


		
		
	}

}

