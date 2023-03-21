import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Term {
    public static String[] getExpression() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вход:");
        String userInput = scanner.nextLine();
        if(Objects.equals(userInput, "quit")){
            throw new Exception("Завершение работы.");
        }
        return userInput.split(" ");
    }
}
class Math {
    public static Object[] Expression(String[] Expr) {
        ArrayList currentUser = new ArrayList();;
        Stack<String> stack = new Stack<>();
        int first;
        for (int i =0; i < Expr.length; i++) {
            first = getPriority(Expr[i]);
            if (first ==0) currentUser.add(Expr[i]);
            if (first ==1) stack.push(Expr[i]);
            if (first > 1) {
                while(!stack.empty()) {
                    if(getPriority(stack.peek()) >= first)currentUser.add(stack.pop());
                    else break;

                }
                stack.push(Expr[i]);
            }
            if (first == -1) {
                while (getPriority(stack.peek()) != 1) currentUser.add(stack.pop());
                stack.pop();
            }
        }
        while(!stack.empty())currentUser.add(stack.pop());
        return currentUser.toArray();
    }
    public static String Calc(Object[] st) throws Exception {
        String operand = new String();
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < st.length; i++) {
            if (getPriority(String.valueOf(st[i])) == 0){
                operand = (String) st[i];
                stack.push(operand);
            }
            if(getPriority(String.valueOf(st[i])) > 1){
                drob dr1 = new drob(stack.pop());
                drob dr2 = new drob(stack.pop());
                if (st[i].toString().equals("+")) stack.push(drob.summa(dr1,dr2));
                if (st[i].toString().equals("-")) stack.push(drob.diff(dr1,dr2));
                if (st[i].toString().equals("*")) stack.push(drob.prod(dr1,dr2));
                if (st[i].toString().equals(":")) stack.push(drob.quot(dr1,dr2));
            }
        }
        return stack.pop();
    }
    public static int getPriority(String token) {
        if (Objects.equals(token, "*" ) || Objects.equals(token, ":")) return 3;
        else if (Objects.equals(token, "+" ) || Objects.equals(token, "-")) return 2;
        else if (Objects.equals(token, "(" )) return 1;
        else if (Objects.equals(token, ")" )) return -1;
        else return 0;
    }
}
class drob {
    public int chislitel, znamenatel;
    public String drob;
    public drob(String drob)throws Exception {
        int len = drob.length();
        StringBuilder chislitel0 = new StringBuilder();
        StringBuilder znamenatel0 = new StringBuilder();
        String pattern="-?[0-9]+/-?[1-9]+|-[0-9]+/-[1-9]+";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(drob);
        int index = drob.indexOf('/');
        if (m.matches()) {
            for (int i = 0; i < index; i++) {
                chislitel0.append(drob.charAt(i));
            }
            for (int i = index + 1; i < len; i++) {
                znamenatel0.append(drob.charAt(i));
            }
        } else { throw new Exception("Ошибка. Некорректное выражение или деление на ноль.");
        }
        this.znamenatel = Integer.parseInt(znamenatel0.toString());
        this.chislitel = Integer.parseInt(chislitel0.toString());
    }
    public static String summa(drob drob0, drob drob1) {
        return (drob0.znamenatel * drob1.chislitel + drob0.chislitel * drob1.znamenatel) + "/" + drob0.znamenatel * drob1.znamenatel;
    }
    public static String diff(drob drob0, drob drob1) {
        return (drob0.znamenatel * drob1.chislitel - drob0.chislitel * drob1.znamenatel) + "/" + drob0.znamenatel * drob1.znamenatel;
    }
    public static String prod(drob drob0, drob drob1) {
        return drob0.chislitel * drob1.chislitel + "/" + drob0.znamenatel * drob1.znamenatel;
    }
    public static String quot(drob drob0, drob drob1) {
        return drob0.chislitel * drob1.znamenatel + "/" + drob0.znamenatel * drob1.chislitel;
    }
}
public class finallabjava {
    public static void main(String[] args) throws Exception {
        System.out.println("Калькулятор простых дробей.");
        while (true) {
            System.out.println(Math.Calc(Math.Expression(Term.getExpression())));
        }
    }
}
