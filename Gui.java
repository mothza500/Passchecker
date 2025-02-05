import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

interface PasswordChecker {
    String checkPassword(char[] password);
    boolean passwordInWordlist(String password); //คราสที่นำมาเป็น interface
}

public class Gui extends JFrame implements ActionListener, PasswordChecker {

    Container cp;
    JLabel pass, doc1, doc2;
    JPasswordField passT;
    JButton b1_check, b2_check; 

    public Gui() {
        Initial();
        setComponent();
        Finally(); //guiของโปรแกรม
    }

    public void Initial() {
        cp = this.getContentPane();
        cp.setLayout(null);
    }

    public void setComponent() {
        pass = new JLabel("Your Password : ");
        doc1 = new JLabel("1:Check method is normal security check");
        doc2 = new JLabel("2:Check_wlist is scan your password with Bigestwordlist name is rockyou.txt");
        passT = new JPasswordField("");
        b1_check = new JButton("Check");
        b2_check = new JButton("Check_wlist"); //หน้าต่างของโปรแกรม

        pass.setBounds(50, 20, 120, 25);
        doc1.setBounds(50, 120, 300, 25);
        doc2.setBounds(50, 134, 500, 25);
        passT.setBounds(170, 20, 300, 25);
        b1_check.setBounds(100, 70, 140, 35);
        b2_check.setBounds(290, 70, 140, 35);

        cp.add(pass);
        cp.add(doc1);
        cp.add(doc2);
        cp.add(passT);
        cp.add(b1_check);
        cp.add(b2_check);

        b1_check.addActionListener(this);
        b2_check.addActionListener(this);
    }

    public void Finally() {
        this.setSize(550, 200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    public void actionPerformed(ActionEvent e) { //กำหนดการทำงานถ้าuserกดปุ่มไหน
        if (e.getActionCommand().equals("Check")) {
            char[] passChar = passT.getPassword();
            String resultPass = checkPassword(passChar);
            JDialog d = new JDialog();
            d.getContentPane();
            d.setLayout(new FlowLayout());
            JLabel result = new JLabel(resultPass);
            d.add(result);
            d.setVisible(true);
            d.pack();
            d.setLocationRelativeTo(null);
        }

        if (e.getActionCommand().equals("Check_wlist")) {
            char[] passChar = passT.getPassword();
            String passwordString = new String(passChar);
            boolean found = passwordInWordlist(passwordString);
            JDialog d = new JDialog();
            d.getContentPane();
            d.setLayout(new FlowLayout());
            JLabel result = new JLabel(found ? "Your password is in rockyou.txt." : "Your password is safe."); //ถ้าตัวแปรfound มีค่าเป็น true จะแสดงตัวหนังสือนี้
            d.add(result);
            d.setVisible(true);
            d.pack();
            d.setLocationRelativeTo(null);
        }
    }

    public String checkPassword(char[] password) {
        String pass = new String(password);
        StringBuilder stringBuilder = new StringBuilder();

        if (pass.length() < 8) {
            stringBuilder.append("<html><body>You need to add more 8 digit password.<br></body></html>");
            //เช็คว่ารหัสมี8ตัวหรือไม่
        }
        if (!pass.matches(".*\\d.*")) {
            stringBuilder.append("<html><body>Your password must have a number.<br></body></html>");
            //เช็คว่ารหัสมีตัวเลขไหม
        }
        if (!pass.matches(".*[A-Z].*") || !pass.matches(".*[a-z].*")) {
            stringBuilder.append("<html><body>Your password must contain both uppercase and lowercase letters.<br></body></html>");
            //เช็คว่ามีตัวใหญ่ไหม
        }
        return stringBuilder.toString();
    }

    public boolean passwordInWordlist(String password) {
        boolean found = false; //สร้างตัวแปรและกำหนดให้เป็นค่าfalse
        try {
            BufferedReader rockyouRead = new BufferedReader(new FileReader("rockyou.txt"));
            String line;
            while ((line = rockyouRead.readLine()) != null) { //text ใน rockyou จะเก็ยไว้ในตัวแปร line
                if (line.equals(password)) {
                    found = true;//ถ้าข้อความในตัวแปรline ตรงกับรหัสfoundจะ = true ส่งไปให้บรรทัด86 และloopจะหยุด
                    break;
                }
            }
            rockyouRead.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return found;
    }
}