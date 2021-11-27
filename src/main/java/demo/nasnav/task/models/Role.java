package demo.nasnav.task.models;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column
	private RoleEnum name;

	public Role() {

	}

	public Role(RoleEnum name) {
		this.name = name;
	}

}