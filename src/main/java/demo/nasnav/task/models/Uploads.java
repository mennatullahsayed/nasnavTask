package demo.nasnav.task.models;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "uploads")
public class Uploads {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String description;

	@Type(type="org.hibernate.type.BinaryType")
	@Column
	@Lob
	private byte[] attachment;

	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column
	private String urls;

	@Column
	private String OriginalFilename;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Category category;
	
	
	@ManyToOne
    private User user;
	
	
}
