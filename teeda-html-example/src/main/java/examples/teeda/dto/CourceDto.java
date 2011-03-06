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
import java.util.List;

/**
 * コース表示用DTO
 * 
 * @version $Id: CourceDto.java,v 1.3 2007/07/05 07:23:00 taiki Exp $
 */
public class CourceDto implements Serializable {

	private static final long serialVersionUID = 7162151354976928680L;

	private int courceID = 0;

	private String courceName = null;

	private String courceDescription = null;

	private int trainingID = 0;

	private Integer viewSeqNo = null;

	private List/* <LessonDto> */lessonItems = null;

	public CourceDto() {

	}

	public CourceDto(int aTrainingID, int aCourceID, String aCourceName,
			String aCourceDescription) {
		trainingID = aTrainingID;
		courceID = aCourceID;
		courceName = aCourceName;
		courceDescription = aCourceDescription;
	}

	public String getCourceDescription() {
		return courceDescription;
	}

	public void setCourceDescription(String aCourceDescription) {
		courceDescription = aCourceDescription;
	}

	public int getCourceID() {
		return courceID;
	}

	public void setCourceID(int aCourceID) {
		courceID = aCourceID;
	}

	public String getCourceName() {
		return courceName;
	}

	public void setCourceName(String aCourceName) {
		courceName = aCourceName;
	}

	public int getTrainingID() {
		return trainingID;
	}

	public void setTrainingID(int aTrainingID) {
		trainingID = aTrainingID;
	}

	public Integer getViewSeqNo() {
		return viewSeqNo;
	}

	public void setViewSeqNo(Integer aViewSeqNo) {
		viewSeqNo = aViewSeqNo;
	}

	public List/* <LessonDto> */getLessonItems() {
		return lessonItems;
	}

	public void setLessonItems(List/* <LessonDto> */aLessonItems) {
		lessonItems = aLessonItems;
	}

}
