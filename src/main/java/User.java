import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aloysius on 9/19/16.
 */
public class User implements Serializable{

    private ArrayList<String> requests;
    private String name;
    private long userId;

    public User(){};

    public User(String firstName, String lastName, long userId){
        this.name = firstName;
        if(lastName != null){
            this.name = this.name + " " + lastName;
        }
        this.requests = new ArrayList<>();
        this.userId = userId;
    }

    public boolean addRequest(String request){
        return this.requests.add(request);
    }

    public void clearRequest(){
        requests.clear();
    }

    public boolean haveRequest(){
        return !requests.isEmpty();
    }

    public void deleteRequest(int num) throws InvalidParameterException {
        try{
            this.requests.remove(num-1);
        }catch (IndexOutOfBoundsException e){
            throw new InvalidParameterException("There is not number " + num);
        }
    }


    public String printRequest (){
        String result = "";
        for (int i = 0; i < requests.size(); i++) {
            result = result + (i + 1) + ". " + requests.get(i) + "\n";
        }
        return result;
    }

    ///////////////////////////////////
    /////// Setters and Getters ///////
    ///////////////////////////////////

    public long getUserId(){
        return this.userId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    ///////////////////////////////////
    /////// Overloading methods ///////
    ///////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return userId == user.userId;

    }

    @Override
    public int hashCode() {
        return (int) (userId ^ (userId >>> 32));
    }

    @Override
    public String toString() {
        int count = 1;
        String result = name + "\n";
        for(String request: this.requests){
            result = result + "    " + count + ". " + request + "\n";
            count++;
        }

        if(requests.isEmpty()){
            result = result + "    No requests submitted";
        }
        return result;
    }
}
