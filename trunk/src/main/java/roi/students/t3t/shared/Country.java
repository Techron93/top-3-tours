package roi.students.t3t.shared;

public enum Country {
	Turkey("tur","tr"),
	Egypt("egy","eg"),
	Cyprus("cyp","cy"),
	Maldives("mv","mv"),
	Bulgaria("blg","bg");
	
	
	public final String nevatravel;
	public final String itour;
	
	Country(String nevatravel, String itour){
		this.nevatravel = nevatravel;
		this.itour = itour;
	}

}
