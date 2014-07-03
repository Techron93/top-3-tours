package roi.students.t3t.shared.dao.impl;

import java.util.Set;
import java.util.TreeSet;

import roi.students.t3t.shared.dao.ClientSettings;
import roi.students.t3t.shared.dao.Site;

public class ClientSettingsImpl implements ClientSettings {
	
	private Set<Site> siteList;
	
	public ClientSettingsImpl(){
		siteList = new TreeSet<Site>();
	}
	public Set<Site> getSiteList() {
		return siteList;
	}

	public void setSiteList(Set<Site> siteList) {
		this.siteList = siteList;
	}
	
	public void addSite(Site site){
		siteList.add(site);
	}
}
