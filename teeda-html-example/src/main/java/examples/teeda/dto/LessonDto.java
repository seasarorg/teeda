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
import java.util.Date;

/**
 * 講義表示用DTO
 *
 * @version $Id: LessonDto.java,v 1.7 2007/07/04 11:13:07 taiki Exp $
 */
public class LessonDto implements Serializable {

	private static final long serialVersionUID = -2232158810639661292L;
	
	//lessonID
	private int lessonID = 0;
	//lessonName
	private String lessonName = null;
	//lessonDescription
	private String lessonDescription = null;
	//courceID
	private int courceID = 0;
	//viewSeqNo
	private Integer viewSeqNo = null;
	//trainingRoomID
	private Integer trainingRoomID = null;
	//lessonDate
	private Date lessonDate = null;

	private boolean enabledEntry = false;
	
	public LessonDto() {
		
	}
	
	public LessonDto(int aLessonID, String aLessonName, Date aLessonDate) {
		lessonID = aLessonID;
		lessonName = aLessonName;
		lessonDate = aLessonDate;
	}
	
	public int getCourceID() {
		return courceID;
	}
	public void setCourceID(int aCourceID) {
		courceID = aCourceID;
	}
	public Date getLessonDate() {
		return lessonDate;
	}
	public void setLessonDate(Date aLessonDate) {
		lessonDate = aLessonDate;
	}
	public String getLessonDescription() {
		return lessonDescription;
	}
	public void setLessonDescription(String aLessonDescription) {
		lessonDescription = aLessonDescription;
	}
	public int getLessonID() {
		return lessonID;
	}
	public void setLessonID(int aLessonID) {
		lessonID = aLessonID;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String aLessonName) {
		lessonName = aLessonName;
	}
	public Integer getTrainingRoomID() {
		return trainingRoomID;
	}
	public void setTrainingRoomID(Integer aTrainingRoomID) {
		trainingRoomID = aTrainingRoomID;
	}
	public Integer getViewSeqNo() {
		return viewSeqNo;
	}
	public void setViewSeqNo(Integer aViewSeqNo) {
		viewSeqNo = aViewSeqNo;
	}

	public boolean isEnabledEntry() {
		return enabledEntry;
	}

	public void setEnabledEntry(boolean aEnabledEntry) {
		enabledEntry = aEnabledEntry;
	}
	
}
