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

		if(list.size() <= AgreggationService.resultSiteNum)
			return list;
		else{
			List<HotelInfo> result = new ArrayList<HotelInfo>();
			for(int i =0; i < AgreggationService.resultSiteNum; i++){
				HotelInfo min = list.get(0);
				int minInd = 0;
				for(int j = 1; j < list.size(); j++){
					if(list.get(j).getPrice() < min.getPrice()){
						min = list.get(j);
						minInd = j;
					}
				}
				result.add(min);
				list.remove(minInd);
			}
			return result;
		}
	}
}
