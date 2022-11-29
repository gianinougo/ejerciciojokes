package com.ugo.ejerciciojokes.Data;

public class Joke{
	
	
    public String category;
    public String type;
    public String joke;
    public String delivery;
    public String setup;
    public Flags flags;
	public String lang;


	public Joke(String category, String type, String texto1, String texto2, Flags flags,  String lang) {
		super();
		
		this.category = category;
		this.type = type;
		this.delivery = texto1;
		this.setup = texto2;
		this.flags = flags;
		this.lang = lang;
	}
	
	public Joke(String category, String type, String texto1, Flags flags,  String lang) {
		super();
		
		this.category = category;
		this.type = type;
		this.joke = texto1;
		this.flags = flags;
		this.lang = lang;
	}
	
	public Joke(String category, String type, String texto1, String lang) {
		super();
		
		this.category = category;
		this.type = type;
		this.joke = texto1;
		this.lang = lang;
	}


	public String getCategory() {
		
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public Flags getFlags() {
		return flags;
	}


	public void setFlags(Flags flags) {
		this.flags = flags;
	}

	
    public String getLang() {
   	
   	
		return lang;
	}

	public void setLang(String lang) {
		

		this.lang = lang;
	}


	public String getTexto1() {
		if(type.equals("single")) {
			return joke;
		}
		return setup;
	}


	public void setTexto1(String texto1) {
		this.setup = texto1;
	}
	




	public String getTexto2() {
		return delivery;
	}


	public void setTexto2(String texto2) {
		this.delivery = texto2;
	}




	@Override
	public String toString() {
		
		if(type.equals("single")) {
			
			return "Joke [category=" + category + ", type=" + type + ", texto1=" + joke + ", "
					+ "flags=" + flags + ", lang=" + lang + "]";
			
		}
		
		return "Joke [category=" + category + ", type=" + type + ", texto1=" + setup + ", texto2=" + delivery
				+ ", flags=" + flags + ", lang=" + lang + "]";
	}




	
	

	
	
    
    
}

