package roi.students.t3t.shared.dao;

import java.util.Set;

public interface ClientSettings {
	public Set<Site> getSiteList();
	public void addSite(Site site);
}
