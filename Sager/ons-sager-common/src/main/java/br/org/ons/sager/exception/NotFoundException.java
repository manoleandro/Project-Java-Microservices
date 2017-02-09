package br.org.ons.sager.exception;

public class NotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private final String desc;
	
	public NotFoundException(String desc, String msg){
		super(msg);
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}