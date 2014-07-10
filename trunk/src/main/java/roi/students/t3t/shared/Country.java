package roi.students.t3t.shared;

public enum Country {
	Turkey("tur","tr", 1, 119),
	Egypt("egy","eg", 2, 40),
	Cyprus("cyp","cy", 6, 58),
	Maldives("mv","mv", 19, 72),
	Bulgaria("blg","bg", 66, 19);
	
	public final String nevatravel;
	public final String itour;
	public final int idITour;
	public final int idTourFind;
	
	Country(String nevatravel, String itour, int id,int idTF){
		this.nevatravel = nevatravel;
		this.itour = itour;
		this.idITour = id;
		this.idTourFind=idTF;
	}
}

