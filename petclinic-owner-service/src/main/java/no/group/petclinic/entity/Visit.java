package no.group.petclinic.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="visits")
@ToString
public class Visit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="visit_date")
	Date visitDate;
	
	String description;
	
	@ManyToOne
	@JoinColumn(name="pet_id")
	@ToString.Exclude
	@JsonIgnore
	Pet pet;
}
