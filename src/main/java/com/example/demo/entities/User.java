package com.example.demo.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int u_id;
	private String uname;

	@Column(unique = true)
	private String uemail;

	private String upassword;
	private Long unumber;
	private String uaddress;
	private String role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Orders> orders;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartItems;

	public User() {
		this.role = "ROLE_USER";
	}

	public User(String uemail, String upassword) {
		this.uemail = uemail;
		this.upassword = upassword;
		this.role = "ROLE_USER";
	}

	// Getters and Setters
	public int getU_id() { return u_id; }
	public void setU_id(int u_id) { this.u_id = u_id; }

	public String getUname() { return uname; }
	public void setUname(String uname) { this.uname = uname; }

	public String getUemail() { return uemail; }
	public void setUemail(String uemail) { this.uemail = uemail; }

	public String getUpassword() { return upassword; }
	public void setUpassword(String upassword) { this.upassword = upassword; }

	public Long getUnumber() { return unumber; }
	public void setUnumber(Long unumber) { this.unumber = unumber; }

	public String getUaddress() { return uaddress; }
	public void setUaddress(String uaddress) { this.uaddress = uaddress; }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }

	public List<Orders> getOrders() { return orders; }
	public void setOrders(List<Orders> orders) { this.orders = orders; }

	public List<CartItem> getCartItems() { return cartItems; }
	public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }

	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", uname=" + uname + ", uemail=" + uemail
				+ ", unumber=" + unumber + ", uaddress=" + uaddress + ", role=" + role + "]";
	}
}