package com.ugo.ejerciciojokes.Data;

import java.util.List;

public class Joke{
	
	
    public String category;
    public String type;
    public String joke;
    public Flags flags;
    public int id;
    public boolean safe;
    public String setup;
    public String delivery;
    public String language;
    
    
	public Joke(String category, String type, String joke, Flags flags, int id, boolean safe, String setup,
			String delivery, String language) {
		super();
		this.category = category;
		this.type = type;
		this.joke = joke;
		this.flags = flags;
		this.id = id;
		this.safe = safe;
		this.setup = setup;
		this.delivery = delivery;
		this.language = language;
	}
	
	public Joke(String category, String type, Flags flags, int id, boolean safe, String setup,
			String delivery, String language) {
		super();
		this.category = category;
		this.type = type;
		this.flags = flags;
		this.id = id;
		this.safe = safe;
		this.setup = setup;
		this.delivery = delivery;
		this.language = language;
	}
	
	public Joke(String category, String type, int id, boolean safe, String setup,
			String delivery, String language) {
		super();
		this.category = category;
		this.type = type;
		this.id = id;
		this.safe = safe;
		this.setup = setup;
		this.delivery = delivery;
		this.language = language;
	}
	
	public Joke(String category, String type, String joke,int id, boolean safe, String language) {
		super();
		this.category = category;
		this.type = type;
		this.joke = joke;
		this.id = id;
		this.safe = safe;
		this.language = language;
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


	public String getJoke() {
		return joke;
	}


	public void setJoke(String joke) {
		this.joke = joke;
	}


	public Flags getFlags() {
		return flags;
	}


	public void setFlags(Flags flags) {
		this.flags = flags;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public boolean isSafe() {
		return safe;
	}


	public void setSafe(boolean safe) {
		this.safe = safe;
	}


	public String getSetup() {
		return setup;
	}


	public void setSetup(String setup) {
		this.setup = setup;
	}


	public String getDelivery() {
		return delivery;
	}


	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	
	
	
    
    
    
}

