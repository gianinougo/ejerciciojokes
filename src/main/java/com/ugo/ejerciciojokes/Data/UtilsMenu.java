package com.ugo.ejerciciojokes.Data;

import java.util.Scanner;

public class UtilsMenu {
	
	public static String mostrarMenu(Scanner sc) {
		
		System.out.println("1. Reset DataBase");
        System.out.println("2. Add Joke Statement");
        System.out.println("3. Add Joke PreparedStatement");
        System.out.println("4. Search with Text");
        System.out.println("5. Jokes without flags");
        System.out.println("6. Descargar un chiste aleatorio desde API");
        System.out.println("7. Descargar todos los chistes de la API(No son todos)");
        System.out.println("8. Guargar los chistes del punto 6 y 7");		        
        System.out.println("9. Exit");

        System.out.print("Choose an option: ");
        String optionString = sc.nextLine();
		
		return optionString;
		
	}
	
	public static int menuCategoria(Scanner sc) {
		
		
		boolean salir = false;
		String categoriaString = null;
		int categoria = 0;
		
		while (!salir) {
			
			System.out.println("---Nuevo Chiste--- ");
			System.out.println("Elige una categoria: ");
			System.out.println("1. Any");
	        System.out.println("2. Misc");
	        System.out.println("3. Programming");
	        System.out.println("4. Dark");
	        System.out.println("5. Pum");
	        System.out.println("6. Spooky");
	        System.out.println("7. Christmas");
	        
	       
			categoriaString = sc.nextLine();
			
			if (categoriaString.matches("[a-zA-Z].*")) {
				System.out.println("No puede haber letras");
				System.out.println("");
				
				salir = false;
			}
			else {
				
				categoria = Integer.parseInt(categoriaString);
				
				if ((categoria >= 8) || (categoria == 0)){
					
					System.out.println("Elige un numero entre 1 y 7");
					salir = false;
					
				} else {
					salir = true;
				}
				
			}
				
			}
		return categoria;
		
	}
	
	public static int menuTipo(Scanner sc) {
		
        
        boolean salir2 = false;
		int type = 0;
		
		while (!salir2) {
			
	        
			System.out.println("Elige una tipo: ");
			System.out.println("1. single");
	        System.out.println("2. twopart");

	        String typeString = sc.nextLine();
	        
			if (typeString.matches("[a-zA-Z].*")) {
				System.out.println("No puede haber letras");
				System.out.println("");
				
				salir2 = false;
			} else {
				
				type = Integer.parseInt(typeString);
				
				if((type == 0) || (type >= 3)){
					System.out.println("Elige entre las 2 opciones");
					salir2 = false;
				}
				else {
					salir2 = true;
				}
			}
			
		}
        
        return type;
	}
	
	public static String menuJoke(Scanner sc) {
		
		System.out.println("---Single--- ");
		System.out.println("Escribe el chiste: ");
		
		String jokeString = sc.nextLine();
		
		 return jokeString;
	}
	
	public static int menuIdioma(Scanner sc) {
		
		int idioma = 0;
		
		boolean salir3 = false;
		while (!salir3) {
			System.out.println("Elige idioma: ");
			System.out.println("1. CS");
	        System.out.println("2. DE");
	        System.out.println("3. ES");
	        System.out.println("4. PT");
	        System.out.println("5. FR");
	        System.out.println("6. EN");
	        
			String idiomaString = sc.nextLine();
			
			if(idiomaString.matches("[a-zA-Z].*")) {
				System.out.println("No puede haber letras");
				System.out.println("");
				
				salir3 = false;
			}
			

			idioma = Integer.parseInt(idiomaString);
			
			if((idioma == 0) || (idioma >= 7)){
				System.out.println("Elige entre las 6 opciones");
				salir3 = false;
			}
			else {
				salir3 = true;
			}
			
		}
		return idioma;
		

	}
	
	public static String menuDosTextosA(Scanner sc) {
		
		System.out.println("---TwoParts--- ");
		System.out.println("Escribe primera parte del chiste: ");
		String setupString = sc.nextLine();
		
		return setupString;
	}
	
	public static String menuDosTextosB(Scanner sc) {
		
		System.out.println("---TwoParts--- ");
		System.out.println("Escribe segunda parte del chiste: ");
		String deliveryString = sc.nextLine();
		
		return deliveryString;
	}
	
	

}
