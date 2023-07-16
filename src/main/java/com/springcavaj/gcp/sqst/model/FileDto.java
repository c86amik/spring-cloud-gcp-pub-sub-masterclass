/**
 * 
 */
package com.springcavaj.gcp.sqst.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author c86am
 *
 */
public class FileDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523481957379393884L;
	
	private String fileName;
	private String fileUrl;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	@Override
	public int hashCode() {
		return Objects.hash(fileName, fileUrl);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FileDto))
			return false;
		FileDto other = (FileDto) obj;
		return Objects.equals(fileName, other.fileName) && Objects.equals(fileUrl, other.fileUrl);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileDto [fileName=");
		builder.append(fileName);
		builder.append(", fileUrl=");
		builder.append(fileUrl);
		builder.append("]");
		return builder.toString();
	}
	
}
