/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
