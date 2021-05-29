/**
 *  @author jefersonlpsilva
 *  @since Apr 30, 2021
 */
package app.dinamismo.restBase.domain.enums;


public enum TipoCliente {
	
	PESSOAFISICA(1,"Pessoa Física"),
	PESSOAJURIDICA(2,"Pessoa Júridica");
	
	private int codigo;
	private String descricao;
	
	private TipoCliente(int codigo, String descricao) {
		this.codigo=codigo;
		this.descricao=descricao;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static TipoCliente toEnum(Integer codigo) {
		
		if(codigo == null) 
			return null;
		
		for (TipoCliente x : TipoCliente.values()) {
			if (codigo.equals(x.codigo)) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+codigo);
		
	}	

}
