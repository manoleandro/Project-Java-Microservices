package br.org.ons.bdt.test.data;

import java.util.Date;

public class Evento {

	private String eo;
	private String co;
	private String or;
	private Date dtVerif;
	private double disp;
	public String getEo() {
		return eo;
	}
	public void setEo(String eo) {
		this.eo = eo;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getOr() {
		return or;
	}
	public void setOr(String or) {
		this.or = or;
	}
	public Date getDtVerif() {
		return dtVerif;
	}
	public void setDtVerif(Date dtVerif) {
		this.dtVerif = dtVerif;
	}
	public double getDisp() {
		return disp;
	}
	public void setDisp(double disp) {
		this.disp = disp;
	}
}
