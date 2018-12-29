package ma.younes.hamdane.batch.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ma.younes.hamdane.batch.jaxb.adapters.XmlJaveDobAdapter;

@XmlRootElement
public class Personne {
	private int refId;
	private String name;
	private Date dob;

	public Personne() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Personne(int refId, String name, Date dob) {
		super();
		this.refId = refId;
		this.name = name;
		this.dob = dob;
	}
    @XmlAttribute(name="refId")
	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}
    @XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlJavaTypeAdapter(XmlJaveDobAdapter.class)
    @XmlElement
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
