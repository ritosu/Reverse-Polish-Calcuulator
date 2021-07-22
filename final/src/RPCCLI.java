import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class RPCCLI {
	private BufferedReader in;
	 
    RPCCLI() {
        in = new BufferedReader(new InputStreamReader(System.in));
    }
 
//    public static void main(String[] args) {
//        (new RPCCLI()).mainLoop();
//    }
    
    public String calstart(String input) {
    	String value = callcal(input);
    	return value;
    }
    private String callcal(String input) {
        ReversePolishCalclator rpc = new ReversePolishCalclator();
	    if (input == null || input.equals("!"))
	        return null;
	    String value = rpc.calc(input.split("[_ ]"));
	    if (value != null)
	        System.out.println(Double.parseDouble(value));
	    else
	        System.out.println("Error");
	    return value;
    }
 
    /**
     * 標準入力から一行読み込み返す
     * 
     * @return 入力された文字列
     */
    private String readLine() {
        String str = null;
        try {
            str = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return str;
    }
 
    /**
     * プロンプトを表示し入力を受け付け計算結果を表示する !が入力されるまでループ
     */
    private void mainLoop() {
        ReversePolishCalclator rpc = new ReversePolishCalclator();
        while (true) {
            String input = readLine();
            if (input == null || input.equals("!"))
                return;
            String value = rpc.calc(input.split("\\s+"));
            if (value != null)
                System.out.println(Double.parseDouble(value));
            else
                System.out.println("Error");
        }
    }
}
