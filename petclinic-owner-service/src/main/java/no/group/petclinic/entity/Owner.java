package no.group.petclinic.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "owners")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Owner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;
	
	private String email;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	private String city;
	
	private String address;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,
				cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Pet> pets;
}
