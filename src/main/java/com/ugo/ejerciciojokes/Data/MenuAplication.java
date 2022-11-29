//Autor Ugo Gianio De Simón

package com.ugo.ejerciciojokes.Data;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.commons.dbutils.DbUtils;


public class MenuAplication {
	
	List<Joke> jokes = new ArrayList<>();

	public void ejecutar() throws SQLException {
		
		Connection conn = null;
		try {
			
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/jokes";
			String usuario = "postgres";
			String password = "0000";
			conn = DriverManager.getConnection(url, usuario, password);
			Scanner sc = new Scanner(System.in);
			
			String optionString;
			do {
				
				optionString = UtilsMenu.mostrarMenu(sc);
				
		        
		        if (optionString.matches("[a-zA-Z].*")) {
					System.out.println("No puede haber letras");
					System.out.println("");
					
				} else {
		        	
			        switch(optionString)
			        {
			        	case "1": resetDataBase(conn); break;
			        	case "2": addJokesStatement(conn); break;
			        	case "3": addJokesPreparedStatement(conn); break;
			        	case "4": searchJokes(conn); break;
			        	case "5": jokesWithOutFlags(conn); break;
			        	case "6": jokes.addAll(callAPI()); break;
			        	case "7": jokes.addAll(AllJokes()) ; break;
			        	case "8": CargarBDD(conn); break;
			        	case "9": System.out.println("Bye"); break;
			        	default: System.out.println("Wrong option"); break;
			        }
			        System.out.println();
		        }
				
			} while(!optionString.equals("9"));
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(conn);
			}
		
		 		System.out.println("Terminado!"); 
} 
			

	private void addJokesStatement(Connection conn) {
		
		Statement statement = null;
		Scanner sc = new Scanner(System.in);
		
		int categoria = UtilsMenu.menuCategoria(sc);
		
		int type = UtilsMenu.menuTipo(sc);
		int typeAux = type;
		
		int idioma = 0;
		
		String jokeString;
		String setupString;
		String deliveryString;
		
		if(typeAux == (1)) {
			
			jokeString = UtilsMenu.menuJoke(sc);
			
			idioma = UtilsMenu.menuIdioma(sc);
			
			
			try {
					
				String sql2 = "INSERT INTO jokes (id_languages , texto1, id_category, id_type) "
						+ "VALUES ( " + idioma + ", " + "'" + jokeString + "'"  + ", " 
						+ categoria  + ", "  + type  + " " + ") ";
				
				
				System.out.println(sql2);
				statement = conn.createStatement();
				int affectedRows1 = statement.executeUpdate(sql2);
				System.out.print("Affected rows: " + affectedRows1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//DbUtils.closeQuietly(conn);
			}
			
			
			
		} else if(typeAux == 2){
			
			
			setupString = UtilsMenu.menuDosTextosA(sc);
			deliveryString = UtilsMenu.menuDosTextosB(sc);
			idioma = UtilsMenu.menuIdioma(sc);
			
			try {
				
				String sql2 = "INSERT INTO jokes (id_languages , texto1, texto2, id_category, id_type) "
						+ "VALUES ( " + idioma + ", " + "'" + setupString + "'" + ", " + "'" + deliveryString + "'" 
						+ ", " + categoria  + ", "  + type  + " " + ") ";
				
				
				System.out.println(sql2);
				statement = conn.createStatement();
				int affectedRows1 = statement.executeUpdate(sql2);
				System.out.print("Affected rows: " + affectedRows1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//DbUtils.closeQuietly(conn);
			}
			
		} else {
			System.out.println("Opcion incorrecta");
		}
	}
		
		
		


	private void jokesWithOutFlags(Connection conn) {
		
		CallableStatement cs = null;
		ResultSet rsChistes = null;
		
		try
		{
			cs = conn.prepareCall(
			"{call CHISTES_SIN_FLAG()}");
			rsChistes = cs.executeQuery();
			while(rsChistes.next())
			{
				System.out.println(" id " + rsChistes.getInt(1) + " Idioma " +
						rsChistes.getInt(2) + " Texto1 " + rsChistes.getString(3) 
						+ " Texto2 " + rsChistes.getString(4) + " categoría " + rsChistes.getString(5)
						+ " Tipo " + rsChistes.getString(6));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(rsChistes);
			DbUtils.closeQuietly(cs);
		}
		
	}

	private void searchJokes(Connection conn) throws SQLException {
		
		CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Buscar: ");
		String search = sc.nextLine();
		search = search.toUpperCase();
		
		try
		{
			ps = conn.prepareStatement("SELECT * FROM jokes " +
								"WHERE UPPER(texto1) = ?");
			ps.setString(1, search);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				cs = conn.prepareCall(
				"{call SEARCH(?)}");
				cs.setInt(1, rs.getInt("id_jokes"));
				rs = cs.executeQuery();
				while(rs.next())
				{
					System.out.println(rs.getString(1) + " - " + rs.getString(2) +
							" - " + rs.getString(3));
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(rs2);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(cs);
		}
		
	}

	private void CargarBDD(Connection conn) throws SQLException {
		
		Statement statement = null;
		
		
		try {
			for (Joke joke : jokes) {
				
				int lenguaje = extracted(joke);
				int category = extracted2(joke);
				int type = extracted3(joke);
				
				System.out.println(joke);
				
				if(type == 1) {
									
				String sql2 = "INSERT INTO jokes (id_languages , texto1, id_category, id_type) "
						+ "VALUES ( " + lenguaje + ", " + "'" + joke.getTexto1() + "'" 
						+ ", " + category  + ", "  + type  + " " + ") ";
				
					System.out.println(sql2);
					statement = conn.createStatement();
					int affectedRows1 = statement.executeUpdate(sql2);
					System.out.print("Affected rows: " + affectedRows1);
				}
				else {
					
					String sql2 = "INSERT INTO jokes (id_languages , texto1, texto2, id_category, id_type) "
							+ "VALUES ( " + lenguaje + ", " + "'" + joke.getTexto1() + "'"  + ", "+ 
					"'" +  joke.getTexto2()+ "'" + ", " + category  + ", "  + type  + " " + ") ";
					
					System.out.println(sql2);
					statement = conn.createStatement();
					int affectedRows1 = statement.executeUpdate(sql2);
					System.out.print("Affected rows: " + affectedRows1);
					
				}
				

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//DbUtils.closeQuietly(conn);
		}
						
		
	}


	

	private void addJokesPreparedStatement(Connection conn) throws SQLException {
		
		
		PreparedStatement ps = null;
		
		String jokeString;
		String setupString;
		String deliveryString;
		
		Scanner sc = new Scanner(System.in);
		
		int categoria = UtilsMenu.menuCategoria(sc);
		int type = UtilsMenu.menuTipo(sc);
		
		int typeAux = type;
		
		int idioma = 0;
		
		if(typeAux == (1)) {

			
			jokeString = UtilsMenu.menuJoke(sc);
			
			idioma = UtilsMenu.menuIdioma(sc);
			
			
			try {
					ps = conn.prepareStatement("INSERT INTO jokes " 
							+ "(id_languages, texto1, id_category, id_type) VALUES (?,?,?,?)");
					
				
				//ps.setString(1, joke.getLang());
				ps.setInt(1, idioma);
				ps.setString(2, jokeString);
				ps.setInt(3, categoria);
				ps.setInt(4, type);
				
				int affectedRows2 = ps.executeUpdate();
				System.out.println("Affected rows: " + affectedRows2);
			}finally {
				
			}
			
			
			} else if(typeAux == 2){
				
				setupString = UtilsMenu.menuDosTextosA(sc);
				deliveryString = UtilsMenu.menuDosTextosB(sc);
				idioma = UtilsMenu.menuIdioma(sc);
			
			try {
				
					
					ps = conn.prepareStatement("INSERT INTO jokes " 
							+ "(id_languages, texto1, texto2, id_category, id_type) VALUES (?,?,?,?,?)");
					
				
				//ps.setString(1, joke.getLang());
				ps.setInt(1, idioma);
				ps.setString(2, setupString);
				ps.setString(3, deliveryString);
				ps.setInt(4, categoria);
				ps.setInt(5, type);
				
				int affectedRows2 = ps.executeUpdate();
				System.out.println("Affected rows: " + affectedRows2);

				} 
			catch (Exception e) {
				// TODO: handle exception
			}
				
			
		} else {
			System.out.println("Opcion incorrecta");
		}
		
	}

	private void resetDataBase(Connection conn) {
		
		Statement statement = null;
		
		
		String sql = "--DROP TABLE IF EXISTS public.jokes;\r\n"
				+ "--DROP TABLE IF EXISTS public.jokes_flags;\r\n"
				+ "--DROP TABLE IF EXISTS public.flags;\r\n"
				+ "--DROP TABLE IF EXISTS public.categoriess;\r\n"
				+ "--DROP TABLE IF EXISTS public.types;\r\n"
				+ "--DROP TABLE IF EXISTS public.languages;"
				+ "\r\n"
				+ "DROP TABLE public.categories CASCADE;\r\n"
				+ "DROP TABLE public.flags CASCADE;\r\n"
				+ "DROP TABLE public.types CASCADE;\r\n"
				+ "DROP TABLE public.languages CASCADE;\r\n"
				+ "DROP TABLE public.jokes CASCADE;\r\n"
				+ "DROP TABLE public.jokes_flags CASCADE;\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "-- Table: public.categories\r\n"
				+ "\r\n"
				+ "CREATE TABLE IF NOT EXISTS public.categories\r\n"
				+ "(\r\n"
				+ "    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),\r\n"
				+ "    name character varying COLLATE pg_catalog.\"default\",\r\n"
				+ "    CONSTRAINT categories_pkey PRIMARY KEY (id)\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "TABLESPACE pg_default;\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.categories\r\n"
				+ "    OWNER to postgres;\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "-- Table: public.flags\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "CREATE TABLE IF NOT EXISTS public.flags\r\n"
				+ "(\r\n"
				+ "    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),\r\n"
				+ "    name character varying COLLATE pg_catalog.\"default\",\r\n"
				+ "    CONSTRAINT flags_pkey PRIMARY KEY (id)\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "TABLESPACE pg_default;\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.flags\r\n"
				+ "    OWNER to postgres;\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "-- Table: public.types\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "CREATE TABLE IF NOT EXISTS public.types\r\n"
				+ "(\r\n"
				+ "    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),\r\n"
				+ "    name character varying COLLATE pg_catalog.\"default\",\r\n"
				+ "    CONSTRAINT type_pkey PRIMARY KEY (id)\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "TABLESPACE pg_default;\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.types\r\n"
				+ "    OWNER to postgres;\r\n"
				+ "\r\n"
				+ "-- Table: public.languages\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "CREATE TABLE IF NOT EXISTS public.languages\r\n"
				+ "(\r\n"
				+ "    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),\r\n"
				+ "    name character varying COLLATE pg_catalog.\"default\" NOT NULL,\r\n"
				+ "    CONSTRAINT languages_pkey PRIMARY KEY (id)\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "TABLESPACE pg_default;\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.languages\r\n"
				+ "    OWNER to postgres;\r\n"
				+ "\r\n"
				+ "-- Table: public.jokes\r\n"
				+ "\r\n"
				+ "-- DROP TABLE IF EXISTS public.jokes;\r\n"
				+ "\r\n"
				+ "CREATE TABLE IF NOT EXISTS public.jokes\r\n"
				+ "(\r\n"
				+ "    id_languages integer NOT NULL,\r\n"
				+ "    id_jokes integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),\r\n"
				+ "    texto1 character varying(400) COLLATE pg_catalog.\"default\",\r\n"
				+ "    texto2 character varying(400) COLLATE pg_catalog.\"default\",\r\n"
				+ "    id_category integer NOT NULL,\r\n"
				+ "    id_type integer NOT NULL,\r\n"
				+ "    CONSTRAINT jokes_pkey PRIMARY KEY (id_languages, id_jokes),\r\n"
				+ "    CONSTRAINT \"FK_id_idioma\" FOREIGN KEY (id_languages)\r\n"
				+ "        REFERENCES public.languages (id) MATCH SIMPLE\r\n"
				+ "        ON UPDATE NO ACTION\r\n"
				+ "        ON DELETE NO ACTION,\r\n"
				+ "    CONSTRAINT jokes_id_category_fkey FOREIGN KEY (id_category)\r\n"
				+ "        REFERENCES public.categories (id) MATCH SIMPLE\r\n"
				+ "        ON UPDATE NO ACTION\r\n"
				+ "        ON DELETE NO ACTION,\r\n"
				+ "    CONSTRAINT jokes_id_type_fkey FOREIGN KEY (id_type)\r\n"
				+ "        REFERENCES public.types (id) MATCH SIMPLE\r\n"
				+ "        ON UPDATE NO ACTION\r\n"
				+ "        ON DELETE NO ACTION\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "TABLESPACE pg_default;\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.jokes\r\n"
				+ "    OWNER to postgres;\r\n"
				+ "\r\n"
				+ "-- Table: public.jokes_flags\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "CREATE TABLE IF NOT EXISTS public.jokes_flags\r\n"
				+ "(\r\n"
				+ "    id_jokes integer NOT NULL,\r\n"
				+ "    id_languages integer NOT NULL,\r\n"
				+ "    id_flag integer NOT NULL,\r\n"
				+ "    CONSTRAINT jokes_flags_pkey PRIMARY KEY (id_languages, id_flag, id_jokes),\r\n"
				+ "    CONSTRAINT \"FK_flags\" FOREIGN KEY (id_flag)\r\n"
				+ "        REFERENCES public.flags (id) MATCH SIMPLE\r\n"
				+ "        ON UPDATE NO ACTION\r\n"
				+ "        ON DELETE NO ACTION,\r\n"
				+ "    CONSTRAINT \"FK_jokes\" FOREIGN KEY (id_jokes, id_languages)\r\n"
				+ "        REFERENCES public.jokes (id_jokes, id_languages) MATCH SIMPLE\r\n"
				+ "        ON UPDATE NO ACTION\r\n"
				+ "        ON DELETE NO ACTION\r\n"
				+ ")\r\n"
				+ "\r\n"
				+ "TABLESPACE pg_default;\r\n"
				+ "\r\n"
				+ "ALTER TABLE IF EXISTS public.jokes_flags\r\n"
				+ "    OWNER to postgres;\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"categories\" (\"name\") VALUES\r\n"
				+ "('Any');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"categories\" (\"name\") VALUES\r\n"
				+ "('Misc');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"categories\" (\"name\") VALUES\r\n"
				+ "('Programming');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"categories\" (\"name\") VALUES\r\n"
				+ "('Dark');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"categories\" (\"name\") VALUES\r\n"
				+ "('Pun');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"categories\" (\"name\") VALUES\r\n"
				+ "('Spooky');\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"categories\" (\"name\") VALUES\r\n"
				+ "('Christmas');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"flags\" (\"name\") VALUES\r\n"
				+ "('nsfw');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"flags\" (\"name\") VALUES\r\n"
				+ "('religious');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"flags\" (\"name\") VALUES\r\n"
				+ "('political');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"flags\" (\"name\") VALUES\r\n"
				+ "('racist');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"flags\" (\"name\") VALUES\r\n"
				+ "('sexist');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"flags\" (\"name\") VALUES\r\n"
				+ "('racist');\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"languages\" (\"name\") VALUES\r\n"
				+ "('cs');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"languages\" (\"name\") VALUES\r\n"
				+ "('de');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"languages\" (\"name\") VALUES\r\n"
				+ "('es');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"languages\" (\"name\") VALUES\r\n"
				+ "('pt');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"languages\" (\"name\") VALUES\r\n"
				+ "('fr');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"languages\" (\"name\") VALUES\r\n"
				+ "('en');\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"types\" (\"name\") VALUES\r\n"
				+ "('single');\r\n"
				+ "\r\n"
				+ "INSERT INTO \"public\".\"types\" (\"name\") VALUES\r\n"
				+ "('twopart');\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "CREATE OR REPLACE FUNCTION CHISTES_SIN_FLAG()\r\n"
				+ "RETURNS SETOF jokes\r\n"
				+ "AS\r\n"
				+ "	'SELECT id_jokes, id_languages, texto1, texto2, id_category, id_type\r\n"
				+ "	FROM public.jokes;'\r\n"
				+ "LANGUAGE sql;"
				+ "--DROP FUNCTION get_search_chiste(character varying);\r\n"
				+ "\r\n"
				+ "CREATE OR REPLACE FUNCTION SEARCH(BUSCAR varchar)\r\n"
				+ "RETURNS VARCHAR\r\n"
				+ "AS\r\n"
				+ "$$\r\n"
				+ "BEGIN\r\n"
				+ "	select * from jokes\r\n"
				+ "    WHERE texto1 = BUSCAR; \r\n"
				+ "END\r\n"
				+ "$$\r\n"
				+ "LANGUAGE PLPGSQL;";
		
		try {
			statement = conn.createStatement();
			int affectedRows = statement.executeUpdate(sql);
			System.out.print("Affected rows: " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			 //DbUtils.closeQuietly(rs);
			 //DbUtils.closeQuietly(ps);
			 //DbUtils.closeQuietly(conn);
		}
		
	}
	
	

		
	private List<Joke> callAPI() throws IOException {
		
		List<Joke> jokes = new ArrayList<>();
		
		String url = "https://v2.jokeapi.dev/joke/Any";
		//String url = "https://v2.jokeapi.dev/joke/Any?lang=es&blacklistFlags=religious&idRange=0";
		
		Joke j = JsonUtils.devolverObjetoGsonGenerico(url, Joke.class);
		
		jokes.add(j);
		jokes.forEach(System.out::println);
		
		
		return jokes;
		
		
	}
	
	private List<Joke> AllJokes() {
		
		List<Joke> jokes = new ArrayList<>();
		
		for(int i = 0; i <= 6; i++) {
			
			String url = "https://v2.jokeapi.dev/joke/Any?lang=es&idRange=" + i;
			
			Joke j = JsonUtils.devolverObjetoGsonGenerico(url, Joke.class);
			
			jokes.add(j);
			
			System.out.println(j);
			
			//String s = JsonUtils.crearJsonPretty(j);
			
			//System.out.println(s);
		}

//		for(int i = 0; i <= 182; i++) {
//			
//			String url = "https://v2.jokeapi.dev/joke/Any?lang=en&idRange=" + i;
//			
//			Joke j = JsonUtils.devolverObjetoGsonGenerico(url, Joke.class);
//			
//			jokes.add(j);
//			
//			System.out.println(j);
//		}
//		
//		for(int i = 0; i <= 999; i++) {
//			
//			String url = "https://v2.jokeapi.dev/joke/Any?lang=fr&idRange=" + i;
//			
//			Joke j = JsonUtils.devolverObjetoGsonGenerico(url, Joke.class);
//			
//			jokes.add(j);
//			
//			System.out.println(j);
//		}
//		
//		for(int i = 0; i <= 1; i++) {
//			
//			String url = "https://v2.jokeapi.dev/joke/Any?lang=pt&idRange=" + i;
//			
//			Joke j = JsonUtils.devolverObjetoGsonGenerico(url, Joke.class);
//			
//			jokes.add(j);
//			
//			System.out.println(j);
//		}
//		
		for(int i = 0; i <= 30; i++) {
			
			String url = "https://v2.jokeapi.dev/joke/Any?lang=de&idRange=" + i;
			
			Joke j = JsonUtils.devolverObjetoGsonGenerico(url, Joke.class);
			
			jokes.add(j);
			
			System.out.println(j);
		}
//		
//		for(int i = 0; i <= 2; i++) {
//			
//			String url = "https://v2.jokeapi.dev/joke/Any?lang=cs&idRange=" + i;
//			
//			Joke j = JsonUtils.devolverObjetoGsonGenerico(url, Joke.class);
//			
//			jokes.add(j);
//			
//			System.out.println(j);
//		}
		
		return jokes;
				
	}
	
	private int extracted(Joke joke) {
		int lenguaje;
		
		if (joke.getLang().equals("cs")) {
			lenguaje = 1;
		} else if (joke.getLang().equals("de")){
			lenguaje = 2;
		} else if (joke.getLang().equals("en")){
			lenguaje = 3;
		} else if (joke.getLang().equals("es")){
			lenguaje = 4;
		} else if (joke.getLang().equals("fr")){
			lenguaje = 5;
		} else if (joke.getLang().equals("pt")){
			lenguaje = 6;	
		} else {
			lenguaje = 90;
		}
		return lenguaje;
	}
	private int extracted2(Joke joke) {
		int category;
		
		if (joke.getCategory().equals("Any")) {
			category = 1;
		} else if (joke.getCategory().equals("Misc")){
			category = 2;
		} else if (joke.getCategory().equals("Programming")){
			category = 3;
		} else if (joke.getCategory().equals("Dark")){
			category = 4;
		} else if (joke.getCategory().equals("Pun")){
			category = 5;
		} else if (joke.getCategory().equals("Spooky")){
			category = 6;
		} else if (joke.getCategory().equals("Christmas")){
			category = 7;	
		} else {
			category = 90;
		}
		return category;
	}
	
	private int extracted3(Joke joke) {
		int type;
		
		if (joke.getType().equals("single")) {
			type = 1;
		} else if (joke.getType().equals("twopart")){
			type = 2;
		} else {
			type = 90;
		}
		return type;
	}
	
	

}

