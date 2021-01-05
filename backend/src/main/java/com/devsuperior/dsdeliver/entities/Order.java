package com.devsuperior.dsdeliver.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable{
	
	/*
	 * Mapeamento objeto relacional
	 *   Configurações feitas nas entidades para que os objetos possam ser convertidos
	 *   de forma transparente para as tabelas do banco de dados relacional 
	 *     Qual tabela do banco corresponde a cada classe
	 *     Qual atributo da classe corresponde a cada atributo da tabela
	 * 
	 * @Entity = a classe Product corresponde a uma entidade que será gerenciada pelo JPA (ferramento ORM), ou seja,
	 * a classe Product irá corresponder a uma tabela no BD
	 *   Na importação: utilizar o javax e não o hibernate, pois sempre se utiliza a especificação
	 *     javax = especificação
	 *     hibernate = implementação
	 *     
	 * @Table = definir o nome da tabela no banco
	 * 
	 * @Id = chave primária
	 * @GeneratedValue(strategy = GenerationType.IDENTITY) = número autoincrementável pelo banco
	 * 
	 * @ManyToMany = associação muitos para muitos => um produto contem muitos produtos e um produto pode estar em muitos pedidos
	 * 
	 * @JoinTable = Criação de uma tabela de associação
	 *   joinColumns = @JoinColumn(name = "order_id") => Chave estrangeira que referencia a classe onde se está (no caso, Order)
	 *   inverseJoinColumns = @JoinColumn(name = "product_id")) => Chave estrangeira que referencia a outra classe da associação (no caso, Product)
	 * 
	 */

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;
	private Double latitude;
	private Double longitude;
	private Instant moment;
	private OrderStatus status;
	
	//Associação entre entidades => um pedido pode ter vários produtos
	//"Vários" será representado por uma coleção => set 
	//Set e não list => Para evitar repetições de uma mesmo produto dentro de um pedido
	//Além disso => no momento do mapeamento objeto relacional utilizando o JPA, já é criado a tabela de associação muitos pra muitos
	@ManyToMany
	@JoinTable(name = "tb_order_product",
		joinColumns = @JoinColumn(name = "order_id"),
		inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<Product> products = new HashSet<>();
	
	public Order() {
	}

	public Order(Long id, String address, Double latitude, Double longitude, Instant moment, OrderStatus status) {
		super();
		this.id = id;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.moment = moment;
		this.status = status;
		//Não se utiliza coleções em construtores
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Set<Product> getProducts() {
		return products;
	}
	
	//Remove-se o setProduct porque nunca se irá querer trocar a coleção => nunca irá trocar o objeto da coleção em si
	//Em coleções são adicionados e removidos elementos

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}
