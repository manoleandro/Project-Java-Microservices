package br.org.ons.sager.read.web.rest.exceptions;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String desc;
	
	public BadRequestException(String desc, String msg){
		super(msg);
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
