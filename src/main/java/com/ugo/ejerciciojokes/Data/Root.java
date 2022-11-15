package com.ugo.ejerciciojokes.Data;

import java.util.ArrayList;

public class Root{
	
    public Info info;
    public ArrayList<Joke> jokes;
    
    
	public Root(Info info, ArrayList<Joke> jokes) {
		super();
		this.info = info;
		this.jokes = jokes;
	}


	public Info getInfo() {
		return info;
	}


	public void setInfo(Info info) {
		this.info = info;
	}


	public ArrayList<Joke> getJokes() {
		return jokes;
	}


	public void setJokes(ArrayList<Joke> jokes) {
		this.jokes = jokes;
	}
	
	
    
}
