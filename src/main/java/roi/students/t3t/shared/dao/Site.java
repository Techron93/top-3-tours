package roi.students.t3t.shared.dao;

public enum Site {
	teztour {
		public String url(){return "http://www.tez-tour.com/";}
		public String siteName(){return "TEZ tour";}
	},
	itour{
		public String url(){return "http://itour.ru/";}
		public String sitename() {return "itour";}
	}
}
