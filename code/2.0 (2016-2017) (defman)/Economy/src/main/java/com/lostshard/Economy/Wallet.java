package com.lostshard.Economy;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import com.lostshard.Core.Utils.DecimalFormater;

@Entity
public class Wallet extends Number implements Comparable<Wallet> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private final int id;
	@Type(type = "uuid-char")
	private final UUID uuid;

	@Audited
	private Transaction value;
	
	public Wallet() {
		this.uuid = UUID.randomUUID();
		this.value = new Transaction(null, this, 0, "Clean wallet");
		this.id = 0;
	}

	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public float floatValue() {
		return value.floatValue();
	}

	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public long longValue() {
		return value.longValue();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}
	
	public boolean subtract(Wallet to, int value, String note) {
		if(this.intValue() < value)
			return false;
		if(to != null)
			to.add(this, value, note);
		this.setValue(new Transaction(this, to, value, note));
		return true;
	}
	
	public boolean add(Wallet from, int value, String note) {
		if(from != null && from.intValue() < value)
			return false;
		if(from != null)
			from.subtract(this, value, note);
		this.setValue(new Transaction(from, this, value, note));
		return true;
	}
	
	/**
	 * @return the value
	 */
	public Transaction getValue() {
		return this.value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Transaction value) {
		this.value = value;
	}

	public boolean contains(int value) {
		return intValue() >= value;
	}
	
	@Override
	public String toString() {
		return DecimalFormater.format(value.intValue());
	}

	@Override
	public int compareTo(Wallet o) {
		return this.intValue() - o.intValue();
	}
}
