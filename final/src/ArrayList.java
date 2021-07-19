
public class ArrayList {
    private String elements[];
    private int size;
    
    public ArrayList() {
        elements = new String[10];
        size = 0;
    }
    
    private void growList() {
        int newsize = elements.length * 2;
        String newelements[] = new String[newsize];
        for(int i = 0; i < elements.length; ++i)
            newelements[i] = elements[i];
        elements = newelements;
    }
    
    public void add(int p, String x) {
        if(p < 0 || p > size)
            return;
        if(size <= elements.length)
            growList();
        if(p < 0) {
            return;
        }
        for(int i = size; i > p; --i) 
            elements[i] = elements[i-1];
        elements[p] = x;
        size++;
    }
    
    public void add(String x) {
        add(size, x);
    }
    
    public String remove(int p) {
        if(p < 0 || p >= size)
            return null;
        else {
            String ret = elements[p];
            for(int i = p; i < size-1; ++i) 
                elements[i] = elements[i+1];
            elements[size] = null;
            size--;
            return ret;
        }
    }
    
    public String set(int p, String x) {
        if(p < 0 || p >= size)
            return null;
        String ret = elements[p];
        elements[p] = x;
        return ret;
    }
    
    public String get(int p) {
        if(p < 0 || p >= size) 
            return null;
        else 
            return elements[p];
    }
    
    public int size() {
        return size;
    }
}
