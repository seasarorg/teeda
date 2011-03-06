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
package examples.teeda.web.upload;

import java.io.File;

import org.seasar.teeda.extension.annotation.convert.UploadedFileFileConverter;
import org.seasar.teeda.extension.annotation.convert.UploadedFileStringConverter;
import org.seasar.teeda.extension.annotation.validator.Required;
import org.seasar.teeda.extension.util.UploadedFile;

public class UploadPage {

	@Required
	public String name;

	@Required
	public UploadedFile uploadedFile1;

	public String fileName1 = "";

	public long fileSize1;

	public File fileLocation1;

	public String fileContent1;

	@Required
	@UploadedFileStringConverter
	public String uploadedFile2;

	@Required
	@UploadedFileFileConverter
	public File uploadedFile3;

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

		fileName3 = uploadedFile3.getName();
		// fileContent3 = new String(FileUtil.getBytes(uploadedFile3));

		return null;
	}

	protected boolean isTextContent(UploadedFile uploadedFile) {
		String contentType = uploadedFile.getContentType();
		return contentType != null && contentType.startsWith("text/");
	}
}
