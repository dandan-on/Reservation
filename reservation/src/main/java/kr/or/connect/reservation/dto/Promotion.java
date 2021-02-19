package kr.or.connect.reservation.dto;

public class Promotion {
	private Long id;
	private Long productId;
	
	public Promotion(Long productId) {
		this.productId = productId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "Promotion [id=" + id + ", productId=" + productId + "]";
	}
	
	
}
