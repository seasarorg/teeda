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
package examples.teeda.web.foreach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import examples.teeda.dto.CourceDto;
import examples.teeda.dto.LessonDto;

/**
 * 研修申し込みページ
 * 
 * @version $Id: EntryTrainingPage.java,v 1.4 2007/07/05 07:23:00 taiki Exp $
 */
public class EntryTrainingPage {

	// コース一覧
	private List/* <CourceDto> */courceItems = null;

	// コース情報
	private Integer courceID = null;

	private String courceName = null;

	private String courceDescription = null;

	private String status = null;

	// 講義一覧表示
	private boolean viewLesson = false;

	// 講義一覧
	private List/* <LessonDto> */lessonItems = null;

	// 講義情報
	private Integer lessonID = null;

	private String lessonName = null;

	public static final String lessonDate_dateTimeConverter = "pattern='yyyy/M/dd'";

	private Date lessonDate = null;

	private boolean enabledEntry = false;

	public void initialize() {

		viewLesson = false;
		courceItems = new ArrayList/* <CourceDto> */(3);
		courceItems.add(new CourceDto(1, 1, "コース１", "説明１"));
		courceItems.add(new CourceDto(1, 2, "コース２", "説明２"));
		courceItems.add(new CourceDto(1, 3, "コース３", "説明３"));

		if (courceID != null) {
			viewLesson = true;
			lessonItems = new ArrayList/* <LessonDto> */(4);
			lessonItems.add(new LessonDto(101, "講義１", new Date()));
			lessonItems.add(new LessonDto(102, "講義２", new Date()));
			lessonItems.add(new LessonDto(103, "講義３", new Date()));
			lessonItems.add(new LessonDto(104, "講義４", new Date()));
		}

	}

	/**
	 * courceDescription
	 * 
	 * @return courceDescription
	 */
	public String getCourceDescription() {
		return courceDescription;
	}

	/**
	 * courceDescription
	 * 
	 * @param aCourceDescription
	 *            courceDescription
	 */
	public void setCourceDescription(String aCourceDescription) {
		courceDescription = aCourceDescription;
	}

	/**
	 * courceName
	 * 
	 * @return courceName
	 */
	public String getCourceName() {
		return courceName;
	}

	/**
	 * courceName
	 * 
	 * @param aCourceName
	 *            courceName
	 */
	public void setCourceName(String aCourceName) {
		courceName = aCourceName;
	}

	/**
	 * lessonDate
	 * 
	 * @return lessonDate
	 */
	public Date getLessonDate() {
		return lessonDate;
	}

	/**
	 * lessonDate
	 * 
	 * @param aLessonDate
	 *            lessonDate
	 */
	public void setLessonDate(Date aLessonDate) {
		lessonDate = aLessonDate;
	}

	/**
	 * lessonName
	 * 
	 * @return lessonName
	 */
	public String getLessonName() {
		return lessonName;
	}

	/**
	 * lessonName
	 * 
	 * @param aLessonName
	 *            lessonName
	 */
	public void setLessonName(String aLessonName) {
		lessonName = aLessonName;
	}

	/**
	 * courceItems
	 * 
	 * @return courceItems
	 */
	public List/* <CourceDto> */getCourceItems() {
		return courceItems;
	}

	/**
	 * courceItems
	 * 
	 * @param aCourceItems
	 *            courceItems
	 */
	public void setCourceItems(List/* <CourceDto> */aCourceItems) {
		courceItems = aCourceItems;
	}

	/**
	 * courceID
	 * 
	 * @return courceID
	 */
	public Integer getCourceID() {
		return courceID;
	}

	/**
	 * courceID
	 * 
	 * @param aCourceID
	 *            courceID
	 */
	public void setCourceID(Integer aCourceID) {
		courceID = aCourceID;
	}

	/**
	 * lessonItems
	 * 
	 * @return lessonItems
	 */
	public List/* <LessonDto> */getLessonItems() {
		return lessonItems;
	}

	/**
	 * lessonItems
	 * 
	 * @param aLessonItems
	 *            lessonItems
	 */
	public void setLessonItems(List/* <LessonDto> */aLessonItems) {
		lessonItems = aLessonItems;
	}

	/**
	 * viewLesson
	 * 
	 * @return viewLesson
	 */
	public boolean isViewLesson() {
		return viewLesson;
	}

	/**
	 * viewLesson
	 * 
	 * @param aViewLesson
	 *            viewLesson
	 */
	public void setViewLesson(boolean aViewLesson) {
		viewLesson = aViewLesson;
	}

	/**
	 * lessonID
	 * 
	 * @return lessonID
	 */
	public Integer getLessonID() {
		return lessonID;
	}

	/**
	 * lessonID
	 * 
	 * @param aLessonID
	 *            lessonID
	 */
	public void setLessonID(Integer aLessonID) {
		lessonID = aLessonID;
	}

	/**
	 * status
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * status
	 * 
	 * @param aStatus
	 *            status
	 */
	public void setStatus(String aStatus) {
		status = aStatus;
	}

	public boolean isEnabledEntry() {
		return enabledEntry;
	}

	public void setEnabledEntry(boolean aEnabledEntry) {
		enabledEntry = aEnabledEntry;
	}

}
