import util.SqlRequest;
import util.TxtToStringConvertor;

public class Application {
    public static void main(String[] args) {
        System.out.println("Salary of all developers on secondProject");
        SqlRequest.request(TxtToStringConvertor.readFile("SQLrequest3.1"));
        System.out.println("List of developers on second project");
        SqlRequest.request(TxtToStringConvertor.readFile("SQLrequest3.2"));
        System.out.println("List of all java developers");
        SqlRequest.request(TxtToStringConvertor.readFile("SQLrequest3.3"));
        System.out.println("List of all middle developers");
        SqlRequest.request(TxtToStringConvertor.readFile("SQLrequest3.4"));
        System.out.println("List of project like date-name-numOfMembers");
        SqlRequest.requestForLastTask(TxtToStringConvertor.readFile("SQLrequest3.5"));
    }
}
