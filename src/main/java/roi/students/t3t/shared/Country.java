package roi.students.t3t.shared;

public enum Country {
	Turkey("tur","tr", 1),
	Egypt("egy","eg", 2),
	Cyprus("cyp","cy", 6),
	Maldives("mv","mv", 19),
	Bulgaria("blg","bg", 66);
	
	
	public final String nevatravel;
	public final String itour;
	public final int idITour;
	
	Country(String nevatravel, String itour, int id){
		this.nevatravel = nevatravel;
		this.itour = itour;
		this.idITour = id;
	}

}
