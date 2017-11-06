package contact;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String company;

    private Byte[] profileImage;

    private String email;

    private Date birthDate;

    private String workPhone;

    private String personalPhone;

    private String address;

  /* Getters and Setters for this Contact Object */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
  }
  
  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public Byte[] getProfileImage() {
    return profileImage;
  }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
  }

  public Date getBirthdate() {
    return birthDate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthDate = birthdate;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public void setWorkPhone(String workPhone) {
    this.workPhone = workPhone;
  }

  public String getPersonalPhone() {
    return personalPhone;
  }

  public void setPersonalPhone(String personalPhone) {
    this.personalPhone = personalPhone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


}