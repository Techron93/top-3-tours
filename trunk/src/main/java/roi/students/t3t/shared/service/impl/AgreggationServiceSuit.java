package roi.students.t3t.shared.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import roi.students.t3t.shared.dao.HotelInfo;
import roi.students.t3t.shared.service.AgreggationService;

public class AgreggationServiceSuit {
	//Сортировка по цене
	//TODO: обсудить логику	
	public static List<HotelInfo> getThreeBest(List<HotelInfo> list){
		Collections.sort(list, new Comparator<HotelInfo>() {
			  public int compare(HotelInfo o1, HotelInfo o2) {
			    return Integer.compare(o1.getPrice(), o2.getPrice());
			  }
			});
		List<HotelInfo> result = new ArrayList<HotelInfo>();
		for(int i =0; i < list.size() && i < AgreggationService.resultSiteNum; i++){
			result.add(list.get(i));
		}
		return result;
	}
}
