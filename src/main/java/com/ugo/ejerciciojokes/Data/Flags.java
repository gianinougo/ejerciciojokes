package com.ugo.ejerciciojokes.Data;

public class Flags {
	
	 	public boolean nsfw;
	    public boolean religious;
	    public boolean political;
	    public boolean racist;
	    public boolean sexist;
	    public boolean explicit;
	    
	    
	    
		public Flags(boolean nsfw, boolean religious, boolean political, boolean racist, boolean sexist,
				boolean explicit) {
			super();
			this.nsfw = nsfw;
			this.religious = religious;
			this.political = political;
			this.racist = racist;
			this.sexist = sexist;
			this.explicit = explicit;
		}



		public boolean isNsfw() {
			return nsfw;
		}



		public void setNsfw(boolean nsfw) {
			this.nsfw = nsfw;
		}



		public boolean isReligious() {
			return religious;
		}



		public void setReligious(boolean religious) {
			this.religious = religious;
		}



		public boolean isPolitical() {
			return political;
		}



		public void setPolitical(boolean political) {
			this.political = political;
		}



		public boolean isRacist() {
			return racist;
		}



		public void setRacist(boolean racist) {
			this.racist = racist;
		}



		public boolean isSexist() {
			return sexist;
		}



		public void setSexist(boolean sexist) {
			this.sexist = sexist;
		}



		public boolean isExplicit() {
			return explicit;
		}



		public void setExplicit(boolean explicit) {
			this.explicit = explicit;
		}
		
		
		
	    
	    

}