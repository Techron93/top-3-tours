package roi.students.t3t.shared;

public enum Site {
	teztour("http://www.tez-tour.com/","TEZ tour"), 
	itour("http://itour.ru/","itour"),
	nevatravel("http://www.nevatravel.ru","nevatravel"),
	tourfind("http://http://www.tour-find/","tourfind");
	
	public final String url;
	public final String name;
	
	Site(String url, String name){
		this.url = url;
		this.name = name;
	}

}
