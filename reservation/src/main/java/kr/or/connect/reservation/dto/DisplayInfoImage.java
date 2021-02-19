package kr.or.connect.reservation.dto;

public class DisplayInfoImage {
	private Long id;
	private Long displayInfoId;
	private Long fileId;
	
	public DisplayInfoImage(Long displayInfoId, Long fileId) {
		this.displayInfoId = displayInfoId;
		this.fileId = fileId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(Long displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "DisplayInfoImage [id=" + id + ", displayInfoId=" + displayInfoId + ", fileId=" + fileId + "]";
	}
	
	
}
