package roi.students.t3t.shared.dao.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import roi.students.t3t.shared.Site;
import roi.students.t3t.shared.dao.ClientSettings;

public class ClientSettingsImpl implements ClientSettings, Serializable {
	
	private static final long serialVersionUID = 7746689159327489964L;
	private HashSet<Site> siteList;
	
	public ClientSettingsImpl(){
		siteList = new HashSet<Site>();
	}
	public Set<Site> getSiteList() {
		return siteList;
	}

	public void setSiteList(HashSet<Site> siteList) {
		this.siteList = siteList;
	}
	
	public void addSite(Site site){
		siteList.add(site);
	}
}
