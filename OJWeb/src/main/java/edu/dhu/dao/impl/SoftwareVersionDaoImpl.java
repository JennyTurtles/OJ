package edu.dhu.dao.impl;

import edu.dhu.dao.SoftwareVersionDaoI;
import edu.dhu.model.SoftwareVersion;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("softwareVersionDao")
public class SoftwareVersionDaoImpl extends BaseDaoImpl<SoftwareVersion> implements SoftwareVersionDaoI {

	@Override
	public List<SoftwareVersion> getAllSoftwareVersion() {
		String hql = "from SoftwareVersion";
		List<SoftwareVersion> softwareVersion = this.find(hql);
		return softwareVersion;
	}

	@Override
	public boolean editSoftwareVersion(int id, String versionName, String versionDescription) {
		String hql = "update SoftwareVersion set versionName=:versionName,description=:description,"
				+ "lastModifiedTime=:lastModifiedTime where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("versionName", versionName);
		q.setParameter("description", versionDescription);
		q.setParameter("lastModifiedTime", new Date());
		q.setParameter("id", id);
		int num = q.executeUpdate();
		if (num != 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteSoftwareVersion(int id) {
		String hql = "delete from SoftwareVersion where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("id", id);
		int result = q.executeUpdate();
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public void addSoftwareVersion(SoftwareVersion sw) {
		SoftwareVersion tmp = new SoftwareVersion();
		tmp.setVersionName(sw.getVersionName());
		tmp.setDescription(sw.getDescription());
		tmp.setLastModifiedTime(new Date());
		tmp.setLocation(sw.getLocation());
		this.save(tmp);
	}

	@Override
	public SoftwareVersion getSoftwareByID(int versionId) {
		String hql = "from SoftwareVersion where id="+versionId;
		List<SoftwareVersion> softwareVersion = this.find(hql);
		if(softwareVersion.size() == 1)
			return softwareVersion.get(0);
		else
			return new SoftwareVersion();
	}
	
	@Override
	public SoftwareVersion getSoftwareByVersionName(String versionName) {
		String hql = "from SoftwareVersion where versionName="+versionName;
		List<SoftwareVersion> softwareVersion = this.find(hql);
		if(softwareVersion.size() == 1)
			return softwareVersion.get(0);
		else
			return new SoftwareVersion();
	}

	@Override
	public boolean editSoftwareVersion(int id, String versionName,
			String versionDescription, String directionName) {
		String hql = "update SoftwareVersion set versionName=:versionName,description=:description,"
				+ "lastModifiedTime=:lastModifiedTime， location:=location where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("versionName", versionName);
		q.setParameter("description", versionDescription);
		q.setParameter("lastModifiedTime", new Date());
		q.setParameter("location", directionName);
		q.setParameter("id", id);
		int num = q.executeUpdate();
		if (num != 0)
			return true;
		else
			return false;
	}

	
	
}
