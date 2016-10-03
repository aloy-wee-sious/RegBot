import java.util.ArrayList;

/**
 * Created by aloysius on 9/19/16.
 */
public class User {

    private ArrayList<String> PRequest;

    public String getName() {
        return name;
    }

    private String name;
    private long userId;

    public User(String firstName, String lastName, long userId){
        this.name = firstName;
        if(lastName != null){
            this.name = this.name + " " + lastName;
        }
        this.PRequest = new ArrayList<String>();
        this.userId = userId;
    }

    public long getUserId(){
        return this.userId;
    }

    public boolean addRequest(String request){
        return this.PRequest.add(request);
    }

    public void clearRequest(){
        PRequest.clear();
    }

    public void deleteRequest(int num){
        this.PRequest.remove(num-1);
    }

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

    public boolean removeRequest(int input){
        if(input > PRequest.size()){
            return false;
        }else{
            PRequest.remove(input-1);
            return true;
        }
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
