package project.mini.demo.app.components;

/**
 * @author Raghav
 *
 */
public class StudentDetail {

	private long id;
	private String studentName;
	private String collegeName;
	private String department;
	private long contactNumber;
	private long registrationNo;
	private long year;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public long getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(long registrationNo) {
		this.registrationNo = registrationNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StudentDetail [id=");
		builder.append(id);
		builder.append(", studentName=");
		builder.append(studentName);
		builder.append(", collegeName=");
		builder.append(collegeName);
		builder.append(", department=");
		builder.append(department);
		builder.append(", contactNumber=");
		builder.append(contactNumber);
		builder.append(", registrationNo=");
		builder.append(registrationNo);
		builder.append(", year=");
		builder.append(year);
		builder.append("]");
		return builder.toString();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getYear() {
		return year;
	}
	public void setYear(long year) {
		this.year = year;
	}
	
	
}
