package br.org.ons.sager.exception;

public class BadRequestException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private final String desc;
	
	public BadRequestException(String desc, String msg){
		super(msg);
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}