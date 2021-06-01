import javax.swing.*;

import static java.lang.Math.pow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.Stack;  

public class Calculator implements ActionListener {  
    JLabel l1;
    String ch ,ch2 = "";
    Stack<Character> s = new Stack<>();
    String str = "";
    Calculator(){  
    JFrame f=new JFrame("Simple Calculator");  
      
    l1=new JLabel();
    JButton bp=new JButton("<");JButton b0=new JButton("0");JButton bdot=new JButton(".");JButton bequal=new JButton("="); 
    JButton b1=new JButton("1");JButton b2=new JButton("2");JButton b3=new JButton("3");JButton bplus=new JButton("+");
    JButton b4=new JButton("4");JButton b5=new JButton("5");JButton b6=new JButton("6");JButton binto=new JButton("*");
    JButton b7=new JButton("7");JButton b8=new JButton("8");JButton b9=new JButton("9");JButton bminus=new JButton("-"); 
    JButton bac=new JButton("AC");JButton broot=new JButton("~");JButton bpower=new JButton("^");JButton bmod=new JButton("%"); 
    
    l1.setBounds(5,10,320,50);
    bp.setBounds(5, 315, 70, 50);b0.setBounds(80, 315, 70, 50);bdot.setBounds(155, 315, 70, 50);bequal.setBounds(230, 315, 70, 50);
    b1.setBounds(5, 260, 70, 50);b2.setBounds(80, 260, 70, 50);b3.setBounds(155, 260, 70, 50);bplus.setBounds(230, 260, 70, 50);
    b4.setBounds(5, 205, 70, 50);b5.setBounds(80, 205, 70, 50);b6.setBounds(155, 205, 70, 50);binto.setBounds(230, 205, 70, 50);
    b7.setBounds(5, 150, 70, 50);b8.setBounds(80, 150, 70, 50);b9.setBounds(155, 150, 70, 50);bminus.setBounds(230, 150, 70, 50);
    bac.setBounds(5, 95, 70, 50);broot.setBounds(80, 95, 70, 50);bpower.setBounds(155, 95, 70, 50);bmod.setBounds(230, 95, 70, 50);

    bp.addActionListener(this);b0.addActionListener(this);bdot.addActionListener(this);bequal.addActionListener(this);
    b1.addActionListener(this);b2.addActionListener(this);b3.addActionListener(this);bplus.addActionListener(this);
    b4.addActionListener(this);b5.addActionListener(this);b6.addActionListener(this);binto.addActionListener(this);
    b7.addActionListener(this);b8.addActionListener(this);b9.addActionListener(this);bminus.addActionListener(this);
    bac.addActionListener(this);broot.addActionListener(this);bpower.addActionListener(this);bmod.addActionListener(this);


    f.add(l1);
    f.add(b1);f.add(b2);f.add(b3);f.add(bequal);   
    f.add(b4);f.add(b5);f.add(b6);f.add(bplus); 
    f.add(b7);f.add(b8);f.add(b9);f.add(bminus); 
    f.add(bp);f.add(b0);f.add(bdot);f.add(binto); 
    f.add(bac);f.add(broot);f.add(bpower);f.add(bmod); 
    
    f.setSize(320,405);  
    f.setLayout(null);  
    f.setVisible(true);   
}  
    public void actionPerformed(ActionEvent e){  
        ch = String.valueOf(e.getActionCommand());
        if(ch == "AC"){str = "";}
        else if(ch == "="){
                try{
                    str = String.valueOf(EvaluateString.evaluate(str));
                }
                catch(Exception except){
                    
                }
            }
        else if(ch == "<"){
            str = str.substring(0, str.length()-1);
        }
        else{
            str += ch;
        }
        l1.setText(str);
        l1.setFont(new Font("Verdana", Font.PLAIN, 20));
        l1.setBackground(new Color(100, 20, 70));
    } 
    
    public static void main(String[] args){
        new Calculator();
    }
}

 
class EvaluateString
{
    public static int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();
 
        Stack<Integer> values = new
                              Stack<Integer>();
 
        Stack<Character> ops = new
                              Stack<Character>();
 
        for (int i = 0; i < tokens.length; i++)
        {
             
            if (tokens[i] == ' ')
                continue;
 
            if (tokens[i] >= '0' &&
                 tokens[i] <= '9')
            {
                StringBuffer sbuf = new
                            StringBuffer();
                 
                
                while (i < tokens.length &&
                        tokens[i] >= '0' &&
                          tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.
                                      toString()));
               
              
                  i--;
            }
 
            else if (tokens[i] == '(')
                ops.push(tokens[i]);
 
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                  values.push(applyOp(ops.pop(),
                                   values.pop(),
                                 values.pop()));
                ops.pop();
            }
 
            else if (tokens[i] == '+' || tokens[i] == '^' ||
                     tokens[i] == '-' || tokens[i] == '%' ||
                     tokens[i] == '*' || tokens[i] == '~' ||
                        tokens[i] == '/')
            {
                while (!ops.empty() &&
                       hasPrecedence(tokens[i],
                                    ops.peek()))
                  values.push(applyOp(ops.pop(),
                                   values.pop(),
                                 values.pop()));
 
                ops.push(tokens[i]);
            }
        }
 
        while (!ops.empty())
            values.push(applyOp(ops.pop(),
                             values.pop(),
                           values.pop()));
 
        return values.pop();
    }
 
    public static boolean hasPrecedence(
                           char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') &&
            (op2 == '+' || op2 == '-') && 
            (op2 == '%' || op2 == '^' ||  op2 == '~'))
            return false;
        else
            return true;
    }
 
    public static int applyOp(char op,
                           int b, int a)
    {
        switch (op)
        {
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
        case '^':
            return (int) pow(a, b);
        case '~':
            return (int) (pow(a, 2) + pow(b, 2)) ;
        case '%':   
            if (b == 0)
                throw new
                UnsupportedOperationException(
                      "Cannot divide by zero"); 
            return a % b;
        case '/':
            if (b == 0)
                throw new
                UnsupportedOperationException(
                      "Cannot divide by zero");
            return a / b;
        }
        return 0;
    }
}