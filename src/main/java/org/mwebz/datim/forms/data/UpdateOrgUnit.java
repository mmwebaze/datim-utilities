package org.mwebz.datim.forms.data;

public class UpdateOrgUnit {
	
	private String file;
	private int updateReason;
	public int getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(int updateReason) {
		this.updateReason = updateReason;
	}

	private int beginAtRowNumber;
	public int getBeginAtRowNumber() {
		return beginAtRowNumber;
	}

	public void setBeginAtRowNumber(int beginAtRowNumber) {
		this.beginAtRowNumber = beginAtRowNumber;
	}

	private int idColumnNumber;
	private int nameColumnNumber;

	public int getIdColumnNumber() {
		return idColumnNumber;
	}

	public void setIdColumnNumber(int idColumnNumber) {
		this.idColumnNumber = idColumnNumber;
	}

	public int getNameColumnNumber() {
		return nameColumnNumber;
	}

	public void setNameColumnNumber(int nameColumnNumber) {
		this.nameColumnNumber = nameColumnNumber;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
