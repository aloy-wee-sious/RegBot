import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aloysius on 9/19/16.
 */
public class User implements Serializable{

    private ArrayList<String> PRequest;
    private String name;
    private long userId;

    public User(){};

    public User(String firstName, String lastName, long userId){
        this.name = firstName;
        if(lastName != null){
            this.name = this.name + " " + lastName;
        }
        this.PRequest = new ArrayList<String>();
        this.userId = userId;
    }

    public boolean addRequest(String request){
        return this.PRequest.add(request);
    }

    public void clearRequest(){
        PRequest.clear();
    }

    public boolean haveRequest(){
        return !PRequest.isEmpty();
    }

    public void deleteRequest(int num){
        this.PRequest.remove(num-1);
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

    public ArrayList<String> getPRequest() {
        return PRequest;
    }

    public void setPRequest(ArrayList<String> PRequest) {
        this.PRequest = PRequest;
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
        for(String request: PRequest){
            result = result + "    " + count + ". " + request + "\n";
            count++;
        }

        if(PRequest.isEmpty()){
            result = result + "    No request submitted";
        }
        return result;
    }
}
