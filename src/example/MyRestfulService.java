package example;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationPath("/api")
@Path("/restServices")
public class MyRestfulService extends Application {

    // http://localhost:8080/MyWebServices/api/restServices/hello
    @GET
    @Path("/hello")
    public String sayHello(){
		System.out.println("My First Restful Service");
        return "My First Restful Service";
    }
	
	@POST
	@Path("/helloPost")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response sayHelloPost(@FormParam("adminNum") int adminNum,
								 @FormParam("name") String name ){
		return Response.ok("Your message is: " + adminNum + " " + name).build();
	}
    
    @GET
	@Path("/echo")
    public Response getEcho(@QueryParam("message") String msg){
    	return Response.ok("Your message is: " + msg).build();
    
	}
	
	@GET
	@Path("/student")
	public Student getStudent(){
    	return new Student("Peter", "123455X");
	}
	
	@GET
	@Path("/newStudent")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@QueryParam("name") String name, @QueryParam("adminNum") String adminNum){
		return new Student(name, adminNum);
	}
	
	
	@GET
	@Path("/listStudents")
	@Produces(MediaType.APPLICATION_JSON) //text_xml for returning xml
	public List<Student> getAllStudents(){
		List<Student> students = new ArrayList<Student>();
		students.add(new Student("Peter Lim", "123456X"));
		students.add(new Student("Tan Ling Chong", "123456S"));
		students.add(new Student("Wong Peng San", "4554451R"));
		students.add(new Student("Ng Hui Ling", "178988R"));
		students.add(new Student("Johnson Phua", "5466661R"));
		students.add(new Student("John Doe", "8787343R"));
		return students;
	}

	@GET
	@Path("/deletingStudent")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> deleteOneStudent() {
		Logger logger = Logger.getLogger("myLogger");
		//List<Student> listOfStudentsToDelete = getAllStudents();
        List<Student> listOfStudentsToDelete = deleteStudent("8787343R");
		System.out.println("deleting student");
		System.out.println(listOfStudentsToDelete);
		System.err.println("cannot enter path lol...........");
		return listOfStudentsToDelete;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postJson(@Context HttpHeaders httpHeaders, Student student){
    	List<Student> students = new ArrayList<Student>();
    	students.add(student);
		
		return Response.ok("Successfully added").build();
 
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createStudent(@FormParam("adminNum") int adminNum,
							 @FormParam("name") String name) {
    	return Response.status(200).entity("Admin Num: " + adminNum + " name: " + name).build();
		
	}

	@POST
	@Path("/delete/{deleteId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Student> deleteStudent(@PathParam("deleteId") String deleteId) {

    	System.out.println("admin num print " + deleteId);
		ArrayList<Student> listOfStudentsToDelete = (ArrayList<Student>) getAllStudents();
		for (int i=0; i< listOfStudentsToDelete.size(); i++) {
			System.out.println("students " + i + " name " + listOfStudentsToDelete.get(i).getAdminNum());
		}
		Iterator<Student> it = listOfStudentsToDelete.iterator();
		while (it.hasNext()) {
			Student student = it.next();
			if (student.getAdminNum().equals(deleteId)) {
				it.remove();
			}
		}
    	//return Response.status(200).entity("Deleted admin num : " + deleteId).build();
        return listOfStudentsToDelete;
	}


	@GET
	@Path("/plain")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
    	return "Hello Jersey Plain";
	}

	// This method is called if XML is requested
	@GET
	@Path("/xml")
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
    	return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}

	// This method is called if HTML is requested
	@GET
	@Path("/html")
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>"
				+ "<body><h1>" + "Hello Jersey HTML" + "</h1></body>" + "</html> ";
	}
}
