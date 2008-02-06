/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package examples.teeda.web.upload;

import java.io.File;

import org.seasar.teeda.extension.util.UploadedFile;

public class UploadPage {
	public static final String name_TRequiredValidator = null;

	public static final String uploadedFile1_TRequiredValidator = null;

	public static final String uploadedFile2_TRequiredValidator = null;

	public static final String uploadedFile3_TRequiredValidator = null;

	public String name;

	public UploadedFile uploadedFile1;

	public String fileName1;

	public long fileSize1;

	public File fileLocation1;

	public String fileContent1;

	public UploadedFile uploadedFile2;

	public String fileName2;

	public long fileSize2;

	public File fileLocation2;

	public String fileContent2;

	public UploadedFile uploadedFile3;

	public String fileName3;

	public long fileSize3;

	public File fileLocation3;

	public String fileContent3;

	public Class doUpload() throws Exception {
		fileName1 = uploadedFile1.getOriginalName();
		fileSize1 = uploadedFile1.getSize();
		if (!uploadedFile1.isInMemory()) {
			fileLocation1 = uploadedFile1.getStoreLocation();
		}
		if (isTextContent(uploadedFile1)) {
			fileContent1 = uploadedFile1.getString();
		}
		fileName2 = uploadedFile2.getName();
		fileSize2 = uploadedFile2.getSize();
		fileLocation2 = uploadedFile2.getStoreLocation();
		if (isTextContent(uploadedFile2)) {
			fileContent2 = uploadedFile2.getString();
		}
		fileName3 = uploadedFile3.getName();
		fileSize3 = uploadedFile3.getSize();
		fileLocation3 = uploadedFile3.getStoreLocation();
		if (isTextContent(uploadedFile3)) {
			fileContent3 = "" + uploadedFile3.getString();
		}

		return null;
	}

	protected boolean isTextContent(UploadedFile uploadedFile) {
		String contentType = uploadedFile.getContentType();
		return contentType != null && contentType.startsWith("text/");
	}
}
