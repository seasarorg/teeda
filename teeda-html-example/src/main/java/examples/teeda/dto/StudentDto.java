package examples.teeda.dto;

import java.io.Serializable;

/**
 * 受講者用Dto
 *
 * @version $Id: StudentDto.java,v 1.3 2007/07/03 06:58:06 taiki Exp $
 */
public class StudentDto implements Serializable {

	private static final long serialVersionUID = -9143562236770718879L;
	
	//氏名
	private String userName = null;
	//所属名称
	private String sectionName = null;
	//状態名称
	private String statusName = null;
	//結果名称
	private String resultName = null;

	/**
	 * sectionName
	 * @return sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * sectionName
	 * @param aSectionName sectionName
	 */
	public void setSectionName(String aSectionName) {
		sectionName = aSectionName;
	}

	/**
	 * userName
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * userName
	 * @param aUserName userName
	 */
	public void setUserName(String aUserName) {
		userName = aUserName;
	}

	/**
	 * statusName
	 * @return statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * statusName
	 * @param aStatusName statusName
	 */
	public void setStatusName(String aStatusName) {
		statusName = aStatusName;
	}

	/**
	 * resultName
	 * @return resultName
	 */
	public String getResultName() {
		return resultName;
	}

	/**
	 * resultName
	 * @param aResultName resultName
	 */
	public void setResultName(String aResultName) {
		resultName = aResultName;
	}

}
