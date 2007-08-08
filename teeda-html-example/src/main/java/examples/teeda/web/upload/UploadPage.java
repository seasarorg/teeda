package examples.teeda.web.upload;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadPage {
	public static final String name_TRequiredValidator = null;

	public static final String uploadedFile1_TRequiredValidator = null;

	public static final String uploadedFile2_TRequiredValidator = null;

	public static final String uploadedFile3_TRequiredValidator = null;

	private String name;

	private String fileName1;

	private long fileSize1;

	private UploadedFile uploadedFile1;

	private String fileName2;

	private long fileSize2;

	private UploadedFile uploadedFile2;

	private String fileName3;

	private long fileSize3;

	private UploadedFile uploadedFile3;

	public Class doUpload() {
		fileName1 = uploadedFile1.getName();
		fileSize1 = uploadedFile1.getSize();
		fileName2 = uploadedFile2.getName();
		fileSize2 = uploadedFile2.getSize();
		fileName3 = uploadedFile3.getName();
		fileSize3 = uploadedFile3.getSize();

		return null;
	}

	public UploadedFile getUploadedFile1() {
		return uploadedFile1;
	}

	public void setUploadedFile1(UploadedFile upliadFile) {
		this.uploadedFile1 = upliadFile;
	}

	public UploadedFile getUploadedFile2() {
		return uploadedFile2;
	}

	public void setUploadedFile2(UploadedFile uploadedFile2) {
		this.uploadedFile2 = uploadedFile2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UploadedFile getUploadedFile3() {
		return uploadedFile3;
	}

	public void setUploadedFile3(UploadedFile uploadedFile3) {
		this.uploadedFile3 = uploadedFile3;
	}

	public String getFileName1() {
		return fileName1;
	}

	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}

	public String getFileName2() {
		return fileName2;
	}

	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}

	public String getFileName3() {
		return fileName3;
	}

	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}

	public long getFileSize1() {
		return fileSize1;
	}

	public void setFileSize1(long fileSize1) {
		this.fileSize1 = fileSize1;
	}

	public long getFileSize2() {
		return fileSize2;
	}

	public void setFileSize2(long fileSize2) {
		this.fileSize2 = fileSize2;
	}

	public long getFileSize3() {
		return fileSize3;
	}

	public void setFileSize3(long fileSize3) {
		this.fileSize3 = fileSize3;
	}

}
