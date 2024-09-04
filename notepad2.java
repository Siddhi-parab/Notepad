import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;



public class notepad2 {
    
 
    static JFrame NotePad = new JFrame("NotePad");
    static JTextArea txtArea = new JTextArea();
    static UndoManager undoManager=new UndoManager();
    static JLabel statusBar=new JLabel("Line:1 Column:1 | Characters: 0");

    
    public static JFrame createNotepad2(){
        JScrollPane scrollPane = new JScrollPane(txtArea);
        NotePad.add(scrollPane);
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        NotePad.add(statusBar,BorderLayout.SOUTH);
        
       
        txtArea.setBounds(0, 0, 1350, 650);
        NotePad.add(txtArea);
        createmenu(NotePad);
        NotePad.setSize(400, 400);
        NotePad.setLayout(new BorderLayout());
        NotePad.setVisible(true);

        txtArea.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e){
                updatestatusBar();
            }
        });
        
         return NotePad;
        }
        
        static public void createmenu(JFrame f) {
            JMenuBar mb = new JMenuBar();
            JMenu Homemenu = new JMenu("File");
            JMenuItem m1 = new JMenuItem("New");
            m1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (txtArea.getText().length() > 0) {
                        newFile();
                    }
                }
            });
            
            JMenuItem m2 = new JMenuItem("New window");
            m2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    newWindow();
                }
            });
            

        JMenuItem m3 = new JMenuItem("Open");
        m3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtArea.getText().length() > 0) {
                    open();
                } else {
                    openFile();
                }
            }
        });

        JMenuItem m4 = new JMenuItem("Save");
        m4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        });

        JMenuItem m5 = new JMenuItem("Save as");
        m5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        });
        JMenuItem m6 = new JMenuItem("Page Setup");
        m6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JMenuItem m7 = new JMenuItem("Print");
        m7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                print();
            }
        });

        JMenuItem m8 = new JMenuItem("Exit");
        m8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            if(txtArea.getText().length()>0){
                exit();
            }else {
              NotePad.dispose();
            }
                
                
                
            }
        });

        Homemenu.add(m1);
        Homemenu.add(m2);
        Homemenu.add(m3);
        Homemenu.add(m4);
        Homemenu.add(m5);
        Homemenu.add(m6);
        Homemenu.add(m7);
        Homemenu.add(m8);

        JMenu h0memMenu = new JMenu("Edit");
        JMenuItem e1 = new JMenuItem("Cut");
        e1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                txtArea.cut();
            }
        });
        JMenuItem e2 = new JMenuItem("Copy");
        e2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                txtArea.copy();
            }
        });
        JMenuItem e3 = new JMenuItem("Paste");
        e3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                txtArea.paste();
            }
        });
        JMenuItem e4 = new JMenuItem("Delete");
        e4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                txtArea.replaceSelection("");
            }
        });
        JMenuItem e5 = new JMenuItem("Undo");
        txtArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e){
                undoManager.addEdit(e.getEdit());
            }
        });
        e5.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e){
                if(undoManager.canUndo()){
                    undoManager.undo();
                }else{
                    System.out.println("no more undo operation left .");
                }
            }
        });
        JMenuItem e6 = new JMenuItem("Redo");
        e6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(undoManager.canRedo()){
                    undoManager.redo();
                }else{
                    System.out.println("No more Redo operation left .");
                }
            }
        });

        h0memMenu.add(e1);
        h0memMenu.add(e2);
        h0memMenu.add(e3);
        h0memMenu.add(e4);
        h0memMenu.add(e5);
        h0memMenu.add(e6);

        JMenu HomeMenu = new JMenu("Format");
        JMenuItem ff1 = new JMenuItem("Word Wrap");
        JMenuItem ff2 = new JMenuItem("Font");

        HomeMenu.add(ff1);
        HomeMenu.add(ff2);

        JMenu HOmemenu = new JMenu("View");
        JMenuItem v1 = new JMenuItem("Status bar");
        v1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                statusBar.setVisible(!statusBar.isVisible());
            }
        });
        
        JMenuItem v2 = new JMenuItem("Zoom in ");
        v2.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                Font currentFont=txtArea.getFont();
                float newFontsize=currentFont.getSize()+2;
                txtArea.setFont(currentFont.deriveFont(newFontsize));
            }
        });
        JMenuItem v3 = new JMenuItem("Zoom out ");
        v3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Font currentFont1=txtArea.getFont();
                float newFontsize1=currentFont1.getSize() -2;
                txtArea.setFont(currentFont1.deriveFont(newFontsize1));

            }
        });
        JMenuItem v4 = new JMenuItem("Restore Default Zoom");
        v4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
              restoreDefaultFontSize();
            }
        });

        JMenu HOmeSubmenu = new JMenu("Zoom");
        HOmeSubmenu.add(v2);
        HOmeSubmenu.add(v3);
        HOmeSubmenu.add(v4);

        HOmemenu.add(v1);
        HOmemenu.add(HOmeSubmenu);

        JMenu homemenu = new JMenu("Help");
        JMenuItem h1 = new JMenuItem("View Help");
        h1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Openwebpage("https://www.bing.com/search?q=get+help+with+notepad+in+windows&filters=guid:%224466414-en-dia%22%20lang:%22en%22&form=T00032&ocid=HelpPane-BingIA");
            }
        });

        JMenuItem h2 = new JMenuItem("Send FeedBack");
        h2.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                sendfeedback();
            }
        });

        JMenuItem h3 = new JMenuItem("About NotePad");
        h3.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                 aboutnotepad();
            }
        });
        homemenu.add(h1);
        homemenu.add(h2);
        homemenu.add(h3);

        mb.add(Homemenu);
        mb.add(h0memMenu);
        mb.add(HomeMenu);
        mb.add(HOmemenu);
        mb.add(homemenu);

        f.setJMenuBar(mb);
    }

    static void newFile() {
        JDialog d = new JDialog();
        d.setLayout(new FlowLayout());
        JButton b1 = new JButton("Save");
        b1.setSize(30, 30);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
                txtArea.setText("");
                d.setVisible(false);
            }
        });
        JButton b2 = new JButton("Don't Save");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                d.setVisible(false);
            }
        });
        JButton b3 = new JButton("Cancel");
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
            }
        });
        
        d.add(new JLabel("Do you want to save changes in Notepad ?"));
        d.add(b1);
        d.add(b2);
        d.add(b3);
        d.setSize(300, 300);
        d.setVisible(true);
    }

    static void saveFile() {
        FileDialog fileDialog = new FileDialog(NotePad, "Save File", FileDialog.SAVE);
        fileDialog.setVisible(true);

        String file = fileDialog.getFile();
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(fileDialog.getDirectory() + file);
                fileWriter.write(txtArea.getText());
                fileWriter.close();
                System.out.println("File saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Save command cancelled by the user.");
        }
    }

    static void newWindow() {
        JFrame newNotePad = new JFrame("NotePad");
        JTextArea newTxtArea = new JTextArea();
        newTxtArea.setBounds(0, 0, 400, 400);
        newNotePad.add(newTxtArea);
        createmenu(newNotePad);
        newNotePad.setSize(400, 400);
        newNotePad.setLayout(null);
        newNotePad.setVisible(true);
    }

    static void open() {
        JDialog d1 = new JDialog();
        d1.setLayout(new FlowLayout());
        d1.setSize(300, 300);

        JButton o1 = new JButton("save");
        o1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        });
        JButton o2 = new JButton("Don't save");
        o2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                openFile();
            }
        });
        JButton o3 = new JButton("Cancel");
        o3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d1.setVisible(false);
            }
        });

        d1.add(new JLabel("Do you want to save changes in Notepad ?"));
        d1.add(o1);
        d1.add(o2);
        d1.add(o3);
        d1.setVisible(true);
    }

    static void saveAsFile() {
        FileDialog fileDialog = new FileDialog(NotePad, "Save As", FileDialog.SAVE);
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(fileDialog.getDirectory() + file);
                fileWriter.write(txtArea.getText());
                fileWriter.close();
                System.out.println("File saved successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Save as command cancelled ny the user .");
        }
    }

    static void openFile() {
        FileDialog fileDialog = new FileDialog(NotePad, "Open", FileDialog.LOAD);
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        if (file != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileDialog.getDirectory() + file));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    txtArea.append(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Open command cancelled by the user.");
        }
    }

    static void exit() {
        JDialog d2 = new JDialog();
        d2.setLayout(new FlowLayout());
        d2.setSize(300, 300);
        JButton e1 = new JButton("Save");
        e1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        });
        JButton e2 = new JButton("Don't Save");
        e2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NotePad.dispose();
                d2.setVisible(false);
            }
        });
        JButton e3=new JButton("cancel");
        e3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                d2.setVisible(false);
            }
        });

        d2.add(new JLabel("Do you want to save changes in Notepad ?"));
        d2.add(e1);
        d2.add(e2);
        d2.add(e3);
        d2.setVisible(true);

    }
    
    static void print(){
        try{
            boolean complete=txtArea.print();
            if(complete){
                System.out.println("printing successfully");
            }else{
                System.out.println("printing unsucessfully");
            }
        }catch(PrinterException ex){
            System.out.println("print failed :"+ex.getMessage());
        }
    }
   
    static void updatestatusBar(){
        int caretposition=txtArea.getCaretPosition();
        int linenumber=0;
        int columnnumber=0;
        int totalchars=txtArea.getText().length();

        try{
            linenumber=txtArea.getLineOfOffset(caretposition);
            columnnumber=caretposition-txtArea.getLineStartOffset(linenumber);
            linenumber+=1;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        statusBar.setText("line: "+linenumber+" column: "+columnnumber+" |characters:" +totalchars);
    }

    static void Openwebpage(String urlString){
        try{
            Desktop.getDesktop().browse(new URI(urlString));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void sendfeedback(){
        JFrame feedbackDialog=new JFrame("Notepad");
        feedbackDialog.setLayout(null);
        JLabel l1=new JLabel("Enter your name :");
        l1.setBounds(10, 20, 100, 30);
        JLabel l2=new JLabel("Enter Your Feedback :");
        l2.setBounds(10, 60, 150, 20);
        JTextField t1=new JTextField();
        t1.setBounds(200, 25, 200, 30);
        JTextField t2=new JTextField();
        t2.setBounds(200, 60, 200, 30);;

        JButton b1=new JButton("send");
        b1.setBounds(100, 200, 80, 40);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
System.out.println("FeedBack send succesfully .");
            }
        });
        feedbackDialog.add(l1);
        feedbackDialog.add(l2);
        feedbackDialog.add(t1);
        feedbackDialog.add(t2);
        feedbackDialog.add(b1);
        feedbackDialog.setSize(450,300);
        feedbackDialog.setVisible(true);
    }

    static void aboutnotepad(){
        JFrame f1=new JFrame("About Notepad");
        JTextArea l1=new JTextArea("Microsoft Windows \n \n Version 22h2(OS Bulid 19045,477)\n\n Microsoft Corporation. All right reserved. \n\n\n The Window 10pro operating system and its user interface are \n protected by trademark. \n\n\n This product is Licensed under the Microsoft License Terms to.");
        l1.setBounds(10, 10, 150, 100);

        JButton b1=new JButton("OK");
       
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                f1.setVisible(false);
            }
        });

        f1.add(l1);
        f1.add(b1,BorderLayout.SOUTH);
        f1.setSize(500, 500);
        f1.setVisible(true);
        f1.setLayout(null);

    }

    static void restoreDefaultFontSize() {
        Font defaultFont = UIManager.getFont("TextArea.font");
        txtArea.setFont(defaultFont);
    }

    public static void main(String[] args) {
        createNotepad2();
    }
}
