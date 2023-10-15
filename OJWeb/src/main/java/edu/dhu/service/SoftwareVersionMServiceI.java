package edu.dhu.service;

import edu.dhu.model.SoftwareVersion;

import java.util.List;


public interface SoftwareVersionMServiceI {
	
	//获取所有的版本信息
	public List<SoftwareVersion> getAllSoftwareVersion();
	//修改版本信息
	public boolean editSoftwareVersion(int id, String versionName, String Description);
	//根据id删除版本信息
	public boolean deleteSoftwareVersion(int id);
	//添加版本信息
	public void addSoftwareVersion(SoftwareVersion sw);
	//根据id获取相应的SoftwareVersion信息
	public SoftwareVersion getSoftwareByID(int versionId);
	//根据id修改版本信息
	public boolean editSoftwareVersion(int id, String versionName,
			String versionDescription, String directionName);
	
	public SoftwareVersion getSoftwareByVersionName(String versionName);
}
