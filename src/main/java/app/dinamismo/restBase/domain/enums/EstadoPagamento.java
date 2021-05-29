/**
 *  @author jefersonlpsilva
 *  @since Apr 30, 2021
 */
package app.dinamismo.restBase.domain.enums;


public enum EstadoPagamento {
	
	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cancelado");
	
	private int cod;
	private String descricao;
	
		
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		
		if(cod == null) 
			return null;
		
		for (EstadoPagamento x : EstadoPagamento.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+cod);
		
	}
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


}
