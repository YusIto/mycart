package app;

import java.io.Serializable;

/**
 * 社員情報を保持するクラス
 *
 * @author mano
 *
 */
public class Employee implements Serializable {

	public Employee() {
		super();
	}

	/** 社員ID */
	private String id;

	/** 社員名 */
	private String name;

	private String age;

	private String sex;

	private String postalcode;

	private String prefecture;

	/** 住所 */
	private String address;

	private String departmentId;

	/** 部署 */
//	//これいる？いる！！いらん
//
//	private String departmentName;

	/** 入社年度 */
	private String nyuusya;

	private String taisya;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

//	public String getDepartmentName() {
//		return departmentName;
//	}
//
//	public void setDepartmentName(String departmentName) {
//		this.departmentName = departmentName;
//	}

	public String getNyuusya() {
		return nyuusya;
	}

	public void setNyuusya(String nyuusya) {
		this.nyuusya = nyuusya;
	}

	public String getTaisya() {
		return taisya;
	}

	public void setTaisya(String taisya) {
		this.taisya = taisya;
	}
}
