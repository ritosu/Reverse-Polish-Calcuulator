
public class ReversePolishCalclator {
	Stack stack = null;
	    
    ReversePolishCalclator(){
        stack = new Stack();
    }
    /**
     * 逆ポーランド記法で記述された文字列を計算 渡された式に空文字列が含まれていた場合は無視する
     * 
     * @param str 式を各項に分割した配列
     * @return 計算結果
     */
    public String calc(String str[]) {
        for(int i = 0; i < str.length; ++i) {
            double[] num = new double[2];
            if(isNumber(str[i])) {
                stack.push(str[i]);
            }
            else if(str[i].equals("+")) {
                if(popnum(num, stack) == -1)return null;
                stack.push(String.valueOf(num[0]+num[1]));
            }
            else if(str[i].equals("*")) {
                if(popnum(num, stack) == -1)return null;
                stack.push(String.valueOf(num[0]*num[1]));
            }
            else if(str[i].equals("-")) {
                if(popnum(num, stack) == -1)return null;
                stack.push(String.valueOf(num[1]-num[0]));
            }
            else if(str[i].equals("/")) {
                if(popnum(num, stack) == -1)return null;
                stack.push(String.valueOf(num[1]/num[0]));
            }else {
                return null;
            }
        }
        String ret = stack.pop();
        if(stack.empty()) {
            return ret;
        }else {
            return null;
        }
    }
    
    private int popnum(double[] num, Stack stack) {
        if(stack.empty())return -1;
        num[0] = Double.parseDouble(stack.pop());
        if(stack.empty())return -1;
        num[1] = Double.parseDouble(stack.pop());
        return 0;
    }
 
    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
