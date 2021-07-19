
public class Stack {
	private ArrayList list;
    
    public Stack() {
        list = new ArrayList();
    }
    
    void push(String x) {
        list.add(x);
    }
    
    String pop() {
        return list.remove(list.size()-1);
    }
    
    String peek() {
        return list.get(list.size()-1);
    }
    
    boolean empty() {
        if(list.size() == 0)
            return true;
        else
            return false;
//        return list.size()==0 ? true:false;
    }
    
    
}
