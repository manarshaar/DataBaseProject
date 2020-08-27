/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro_db;

import java.awt.Color;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
import java.lang.String;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Manar
 */
public class Fhome extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String sql = "";
    String sql_from_search = "";
    String sql_from_search1 = "";
    String sql_from_search4 = "";
    String sql_from_search2 = "";
    
    int flag = 1; //For the order to determine if i'm coming after searching (2) or after displaying all data (1)
    int flag1 = 1; //products
    int flag4 = 1; //departments
    int flag2 = 1; //exhibitions
    /**
     * Creates new form Fhome
     */
   
    public Fhome() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(870, 720);//725
        jTabbedPane1.setVisible(false);
        CBorder.setEnabled(false);
        Bedit.setEnabled(false);
        Bdelete.setEnabled(false);
        
        CBorder1.setEnabled(false);
        Bedit1.setEnabled(false);
        Bdelete1.setEnabled(false);
        
        CBorder4.setEnabled(false);
        Bedit4.setEnabled(false);
        Bdelete4.setEnabled(false);
        Bshow_products.setEnabled(false);
        
        CBorder2.setEnabled(false);
        Bedit2.setEnabled(false);
        Bdelete2.setEnabled(false);
    }
    
    public void Refresh_employees() {
        sql = "Select * from EMPLOYEE";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_emp.getModel();
            tm.setColumnCount(10);
            String s[]={"Name","SSN","Address","Birth date","Phone number","Department working in",
                "Certification","Years of expirement", "Manages department:","Date of managing" };
            //"Exhibtion Name","Exhibtion date",
            for(int col_index = 0; col_index < 10; col_index++)
            {
                Table_emp.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);

            }
            for(int i = 0; i<10; i++){
                TableColumn a = Table_emp.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }

            ArrayList<Long> ssn = new ArrayList<Long>();
            tm.setRowCount(0);
            while(rs.next()){
                ssn.add(rs.getLong("SSN"));
                Object o[]= {rs.getString("FNAME")+ " " +rs.getString("SNAME")+ " " +rs.getString("LNAME")
                    ,rs.getLong("SSN"),rs.getString("ADDRESS"), rs.getDate("BIRTH_DATE")
                    ,rs.getString("PHONE_NO"),""
                        //,rs.getString("EXNAME"),rs.getDate("EXDATE")
                             };
                //To get the department name from it's number
                String sql_dname = "select dep_name\n" +
                                  "from department, employee\n" +
                                  "where department.dep_number = '"+rs.getInt("DEP_NO")+"'";
                PreparedStatement ps_dname = con.prepareStatement(sql_dname);
                //System.out.println(sql_dname);
                ResultSet rs_dname = ps_dname.executeQuery();
                rs_dname.next();
                o[5] = rs_dname.getString("DEP_NAME");
                
                tm.addRow(o);
            }

            sql = "select essn,certification, years_of_expirement\n" +
            "from procurement_engineer, employee\n" +
            "where procurement_engineer.essn = employee.ssn";
            ps = con.prepareStatement(sql);
            //System.out.println(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if (ssn.contains(rs.getLong("ESSN"))){
                    int ri = ssn.indexOf(rs.getLong("ESSN"));
                    tm.setValueAt(rs.getString("CERTIFICATION"), ri, 6);
                    tm.setValueAt(rs.getString("YEARS_OF_EXPIREMENT"), ri, 7);
                }
            }

            sql = "select essn, department.dep_name, start_date\n" +
                  "from employee, department\n" +
                  "where employee.ssn = department.essn";
            ps = con.prepareStatement(sql);
            //System.out.println(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if (ssn.contains(rs.getLong("ESSN")))
                {
                    int ri = ssn.indexOf(rs.getLong("ESSN"));
                    tm.setValueAt(rs.getString("DEP_NAME"), ri, 8);//Manages department:
                    tm.setValueAt(rs.getDate("START_DATE"), ri, 9);//Date of managing
                }

            }

            CBorder.setEnabled(true);
            CBorder.setSelectedIndex(0);
            CBsearch.setSelectedIndex(0);
            Tsearch.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void Refresh_products()  {
        
        sql = "Select * from PRODUCTS";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
            //System.out.println(sql);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_pro.getModel();
            tm.setColumnCount(11);
            
            String s[]={"Code","Name","Manufacturer","Country of origin", "Decription", "Buying Price", "Selling Price"
                    ,"Years of Waranty","Quantity", "Sold Amount", "In Department"};
            for(int col_index = 0; col_index < 11; col_index++)
            {
                Table_pro.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);

            }
            
            for(int i = 0; i<11; i++){
                TableColumn a = Table_pro.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }
            
            ArrayList<String> code = new ArrayList<String>();
            tm.setRowCount(0);
            while(rs.next()){
                code.add(rs.getString("PRODUCT_CODE"));
                Object o[]= {rs.getString("PRODUCT_CODE"),rs.getString("NAME"),rs.getString("MANUFACTURER")
                        , rs.getString("COUNTRY_OF_ORIGIN"), rs.getString("DESCRIPTION"),rs.getLong("BUYING_PRICE")
                        ,rs.getLong("SELLING_PRICE"),rs.getLong("WARANTY"),rs.getLong("QUANTITY"),rs.getLong("SOLED_AMOUNT")
                    ,""
                             };
                //To get the department name from it's number
                String sql_dname = "select dep_name\n" +
                                  "from department, employee\n" +
                                  "where department.dep_number = '"+rs.getInt("DEP_NO")+"'";
                PreparedStatement ps_dname = con.prepareStatement(sql_dname);
                ResultSet rs_dname = ps_dname.executeQuery();
                rs_dname.next();
                o[10] = rs_dname.getString("DEP_NAME");
                
                tm.addRow(o);
            }

            CBorder1.setEnabled(true);
            CBorder1.setSelectedIndex(0);
            CBsearch1.setSelectedIndex(0);
            Tsearch1.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void Refresh_departments()
    {
        sql = "Select * from DEPARTMENT";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_dep.getModel();
            tm.setColumnCount(8);

            String s[]={"Dep Number","Name","Telephone","Location","Manager","Opening Time","Closing Time", "Date Of Managing"};
            
            for(int col_index = 0; col_index < 8; col_index++)
            {
                Table_dep.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);
            }
            
            Table_dep.getColumnModel().getColumn(0).setPreferredWidth(70);
            for(int i = 1; i<5; i++){
                TableColumn a = Table_dep.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }
            Table_dep.getColumnModel().getColumn(5).setPreferredWidth(85);
            Table_dep.getColumnModel().getColumn(6).setPreferredWidth(85);
            Table_dep.getColumnModel().getColumn(7).setPreferredWidth(150);
            
            ArrayList<Integer> no = new ArrayList<Integer>();
            tm.setRowCount(0);
            int DEPNO =0;
            
            while(rs.next()){
                DEPNO = rs.getInt("DEP_NUMBER");
                no.add(DEPNO);
                
                Object o[]= {rs.getInt("DEP_NUMBER"),rs.getString("DEP_NAME"),rs.getString("TELEPHONE_NO")
                        ,rs.getString("LOCATION"),""    ,"","", rs.getDate("START_DATE")
                        };
                
                //to represent times 
                String sf[] = rs.getTimestamp("OPENING_TIME").toString().split(" ");
                String ss[] = sf[1].split(":");
                o[5] = ss[0] + ":" + ss[1];
                
                String sf1[] = rs.getTimestamp("CLOSING_TIME").toString().split(" ");
                String ss1[] = sf1[1].split(":");
                o[6] = ss1[0] + ":" + ss1[1];
                
                //To get the manager
                String sql_manager = "select fname, sname, lname\n" +
                                 "from employee,department\n" +
                                 " where employee.ssn = DEPARTMENT.essn "
                                 + "and dep_number = '"+DEPNO+"'";
                
                PreparedStatement ps_manager = con.prepareStatement(sql_manager);
                ResultSet rs_manager = ps_manager.executeQuery();
            
                while(rs_manager.next()){
                    o[4] = rs_manager.getString("FNAME")+" "+rs_manager.getString("SNAME")
                         +" "+rs_manager.getString("LNAME");
                }
                
             
                tm.addRow(o); 
                
            }
            
            CBorder4.setEnabled(true);
            CBorder4.setSelectedIndex(0);
            CBsearch4.setSelectedIndex(0);
            Tsearch4.setText("");
            Bshow_products.setEnabled(false);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    public void Refresh_exhibitions()
    {
                sql = "Select * from INTERNATIONAL_EXHIBITION";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_ex.getModel();
            tm.setColumnCount(5);

            String s[]={"NAME","COUNTRY","DATE","COST_OF_TRAVEL", "Employee going"};
            for(int col_index = 0; col_index < 5; col_index++)
            {
                Table_ex.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);
            }
            
            Table_ex.getColumnModel().getColumn(0).setPreferredWidth(150);
            for(int i = 1; i<5; i++){
                TableColumn a = Table_ex.getColumnModel().getColumn(i);
                a.setPreferredWidth(100);
            }
            Table_ex.getColumnModel().getColumn(4).setPreferredWidth(150);
            
            ArrayList<String> pk1 = new ArrayList<String>();
            ArrayList<String> pk2 = new ArrayList<String>();
            
            tm.setRowCount(0);
            
            String exName ="";
            String exDate ="";
            while(rs.next()){
                
                exName = rs.getString("EXNAME");
                exDate = rs.getDate("EXDATE").toString();
                pk1.add(exName);
                pk2.add(exDate);
                
                Object o[]= {rs.getString("EXNAME"),rs.getString("COUNTRY"),rs.getDate("EXDATE")
                        ,rs.getInt("COST_OF_TRAVEL"), ""
                        };
                
                //To get the employee
                String sql_emp = "select fname, sname, lname\n" +
                                 "from employee,international_exhibition\n" +
                                 " where employee.SSN = international_exhibition.ESSN"+
                                 " and international_exhibition.EXNAME = '"+ exName +"'" +
                                 " and international_exhibition.EXDATE = TO_DATE('"+ exDate +"','YYYY-MM-dd')";
                
                //System.out.println(sql_emp);
                PreparedStatement ps_emp = con.prepareStatement(sql_emp);
                ResultSet rs_emp = ps_emp.executeQuery();
                
                while(rs_emp.next()){
                    o[4] = rs_emp.getString("FNAME")+" "+rs_emp.getString("SNAME")
                         +" "+rs_emp.getString("LNAME");
                }
                
                tm.addRow(o);  
            }
            CBorder2.setEnabled(true);
            CBorder2.setSelectedIndex(0);
            CBsearch2.setSelectedIndex(0);
            Tsearch2.setText("");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        LEXH = new javax.swing.JLabel();
        LEMP = new javax.swing.JLabel();
        LPRO = new javax.swing.JLabel();
        LDEP = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tab1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        CBsearch = new javax.swing.JComboBox();
        Tsearch = new javax.swing.JTextField();
        CBorder = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_emp = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex)
            { return false; }
        }
        ;
        Bshowall = new javax.swing.JButton();
        Badd = new javax.swing.JButton();
        Bedit = new javax.swing.JButton();
        Bdelete = new javax.swing.JButton();
        Bsearch = new javax.swing.JButton();
        refresh = new javax.swing.JLabel();
        tab2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        CBsearch1 = new javax.swing.JComboBox();
        Tsearch1 = new javax.swing.JTextField();
        CBorder1 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_pro = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex)
            { return false; }
        }

        ;
        Bshowall1 = new javax.swing.JButton();
        Badd1 = new javax.swing.JButton();
        Bedit1 = new javax.swing.JButton();
        Bdelete1 = new javax.swing.JButton();
        Bsearch1 = new javax.swing.JButton();
        refresh1 = new javax.swing.JLabel();
        tab3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        CBsearch4 = new javax.swing.JComboBox();
        Tsearch4 = new javax.swing.JTextField();
        CBorder4 = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Table_dep = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex)
            { return false; }
        }

        ;
        Bshowall4 = new javax.swing.JButton();
        Badd4 = new javax.swing.JButton();
        Bedit4 = new javax.swing.JButton();
        Bdelete4 = new javax.swing.JButton();
        Bsearch4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tdep_pro = new javax.swing.JTable();
        refresh4 = new javax.swing.JLabel();
        Bshow_products = new javax.swing.JButton();
        tab4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        CBsearch2 = new javax.swing.JComboBox();
        Tsearch2 = new javax.swing.JTextField();
        CBorder2 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table_ex = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex)
            { return false; }
        }
        ;
        Bshowall2 = new javax.swing.JButton();
        Badd2 = new javax.swing.JButton();
        Bedit2 = new javax.swing.JButton();
        Bdelete2 = new javax.swing.JButton();
        Bsearch2 = new javax.swing.JButton();
        refresh2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        LEXH.setBackground(new java.awt.Color(51, 51, 51));
        LEXH.setForeground(new java.awt.Color(255, 255, 255));
        LEXH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LEXH.setText("International Exhibitions");
        LEXH.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LEXH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LEXH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LEXHMouseClicked(evt);
            }
        });

        LEMP.setBackground(new java.awt.Color(51, 51, 51));
        LEMP.setForeground(new java.awt.Color(255, 255, 255));
        LEMP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LEMP.setText("Employees");
        LEMP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LEMP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LEMP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LEMPMouseClicked(evt);
            }
        });

        LPRO.setBackground(new java.awt.Color(51, 51, 51));
        LPRO.setForeground(new java.awt.Color(255, 255, 255));
        LPRO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LPRO.setText("Products");
        LPRO.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LPRO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LPRO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LPROMouseClicked(evt);
            }
        });

        LDEP.setBackground(new java.awt.Color(51, 51, 51));
        LDEP.setForeground(new java.awt.Color(255, 255, 255));
        LDEP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LDEP.setText("Departments");
        LDEP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LDEP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LDEP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LDEPMouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setText("OPEN REPORT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(LEMP, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LPRO, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LDEP, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LEXH, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(LDEP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LEMP, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LPRO, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LEXH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        tab1.setBackground(new java.awt.Color(255, 255, 255));
        tab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1MouseClicked(evt);
            }
        });

        jLabel4.setText("Search");
        jLabel4.setToolTipText("Search by employees' name");

        CBsearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "By", "Name", "SSN", "Phone Number", "Department Name", " " }));

        CBorder.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Name", "Department Number" }));
        CBorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBorderActionPerformed(evt);
            }
        });

        jLabel6.setText("Order by:");

        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        Table_emp.setAutoCreateRowSorter(true);
        Table_emp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_emp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Table_emp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Table_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_empMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_emp);
        Table_emp.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Bshowall.setText("Show all data");
        Bshowall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BshowallActionPerformed(evt);
            }
        });

        Badd.setText("ADD");
        Badd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BaddActionPerformed(evt);
            }
        });

        Bedit.setText("EDIT");
        Bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeditActionPerformed(evt);
            }
        });

        Bdelete.setText("DELETE");
        Bdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BdeleteActionPerformed(evt);
            }
        });

        Bsearch.setText("GO");
        Bsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsearchActionPerformed(evt);
            }
        });

        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pro_db/refresh-icon (1).png"))); // NOI18N
        refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Bshowall, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(Badd, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(Bedit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Bdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(Tsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Bsearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(8, 8, 8)
                                .addComponent(CBsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addComponent(CBorder, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refresh)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(CBsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(1, 1, 1)
                .addComponent(refresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bshowall)
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Badd, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bedit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("All", jPanel3);

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Employees", tab1);

        tab2.setBackground(new java.awt.Color(255, 255, 255));
        tab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2MouseClicked(evt);
            }
        });

        jLabel5.setText("Search");
        jLabel5.setToolTipText("Search by employees' name");

        CBsearch1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "By", "Name", "Code", "Manufacturer", "Country of origin", "Department Name", " ", " ", " ", " ", " " }));

        CBorder1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Name", "Manufacturer", "Buying Price", "Selling Price", "Soled Amount", "Quantity", "Waranty" }));
        CBorder1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBorder1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Order by:");

        jScrollPane3.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        Table_pro.setAutoCreateRowSorter(true);
        Table_pro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_pro.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Table_pro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_proMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table_pro);
        Table_pro.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Bshowall1.setText("Show all data");
        Bshowall1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bshowall1ActionPerformed(evt);
            }
        });

        Badd1.setText("ADD");
        Badd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Badd1ActionPerformed(evt);
            }
        });

        Bedit1.setText("EDIT");
        Bedit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bedit1ActionPerformed(evt);
            }
        });

        Bdelete1.setText("DELETE");
        Bdelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bdelete1ActionPerformed(evt);
            }
        });

        Bsearch1.setText("GO");
        Bsearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bsearch1ActionPerformed(evt);
            }
        });

        refresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pro_db/refresh-icon (1).png"))); // NOI18N
        refresh1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refresh1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Badd1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Bedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Bdelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(Tsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Bsearch1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(refresh1))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(8, 8, 8)
                                .addComponent(CBsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Bshowall1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(CBsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Tsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Bsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(refresh1)
                        .addGap(8, 8, 8)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bshowall1)
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Bdelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Badd1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Products", tab2);

        tab3.setBackground(new java.awt.Color(255, 255, 255));
        tab3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3MouseClicked(evt);
            }
        });

        jLabel14.setText("Search");
        jLabel14.setToolTipText("Search by employees' name");

        CBsearch4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "By", "Department Number", "Department Name", "Telephone Number", "Manager", "Location", " ", " " }));

        CBorder4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Department Name", "Location", " ", " " }));
        CBorder4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBorder4ActionPerformed(evt);
            }
        });

        jLabel15.setText("Order by:");

        jScrollPane6.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        Table_dep.setAutoCreateRowSorter(true);
        Table_dep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_dep.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Table_dep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_depMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(Table_dep);
        Table_dep.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Bshowall4.setText("Show all data");
        Bshowall4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bshowall4ActionPerformed(evt);
            }
        });

        Badd4.setText("ADD");
        Badd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Badd4ActionPerformed(evt);
            }
        });

        Bedit4.setText("EDIT");
        Bedit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bedit4ActionPerformed(evt);
            }
        });

        Bdelete4.setText("DELETE");
        Bdelete4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bdelete4ActionPerformed(evt);
            }
        });

        Bsearch4.setText("GO");
        Bsearch4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bsearch4ActionPerformed(evt);
            }
        });

        Tdep_pro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tdep_pro);

        refresh4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pro_db/refresh-icon (1).png"))); // NOI18N
        refresh4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refresh4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh4MouseClicked(evt);
            }
        });

        Bshow_products.setText("Show Products");
        Bshow_products.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bshow_productsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(8, 8, 8)
                        .addComponent(CBsearch4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(Tsearch4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Bsearch4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CBorder4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(refresh4))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(Bshowall4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Badd4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Bedit4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bdelete4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Bshow_products))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(CBsearch4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Tsearch4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Bsearch4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CBorder4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(refresh4)))
                .addGap(11, 11, 11)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Badd4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Bedit4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Bdelete4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Bshowall4)
                            .addComponent(Bshow_products))))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
            .addGroup(tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
            .addGroup(tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Departments", tab3);

        tab4.setBackground(new java.awt.Color(255, 255, 255));
        tab4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4MouseClicked(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Search");
        jLabel8.setToolTipText("Search by employees' name");

        CBsearch2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "By", "Name", "Country" }));

        CBorder2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Cost", "Employees" }));
        CBorder2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBorder2ActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(0, 153, 153));
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Order by:");

        jScrollPane4.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        Table_ex.setAutoCreateRowSorter(true);
        Table_ex.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Table_ex.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        Table_ex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_exMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(Table_ex);
        Table_ex.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Bshowall2.setText("Show all data");
        Bshowall2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bshowall2ActionPerformed(evt);
            }
        });

        Badd2.setText("ADD");
        Badd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Badd2ActionPerformed(evt);
            }
        });

        Bedit2.setText("EDIT");
        Bedit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bedit2ActionPerformed(evt);
            }
        });

        Bdelete2.setText("DELETE");
        Bdelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bdelete2ActionPerformed(evt);
            }
        });

        Bsearch2.setText("GO");
        Bsearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bsearch2ActionPerformed(evt);
            }
        });

        refresh2.setForeground(new java.awt.Color(0, 102, 102));
        refresh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pro_db/refresh-icon (1).png"))); // NOI18N
        refresh2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        refresh2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refresh2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(Bshowall2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(Tsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Bsearch2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addGap(33, 33, 33)
                                        .addComponent(CBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(CBsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27))))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Badd2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Bedit2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Bdelete2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(63, 63, 63))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(refresh2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CBsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(CBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(refresh2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(Badd2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(Bedit2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(Bdelete2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bshowall2)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tab4Layout = new javax.swing.GroupLayout(tab4);
        tab4.setLayout(tab4Layout);
        tab4Layout.setHorizontalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
            .addGroup(tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab4Layout.setVerticalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
            .addGroup(tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("International Exhibitions", tab4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void LEMPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LEMPMouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab1);
        Refresh_employees(); 
        flag = 1;
    }//GEN-LAST:event_LEMPMouseClicked

    private void LPROMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LPROMouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab2);
        Refresh_products(); 
        flag1 = 1;
    }//GEN-LAST:event_LPROMouseClicked

    private void LDEPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LDEPMouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab3);
        Refresh_departments(); 
        flag4 = 1;
    }//GEN-LAST:event_LDEPMouseClicked

    private void LEXHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LEXHMouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab4);
        Refresh_exhibitions(); 
        flag2 = 1;
    }//GEN-LAST:event_LEXHMouseClicked

    
    
    //TAB EMPLOYEES
    private void BaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BaddActionPerformed
        Fadd_emp add = new Fadd_emp();
        add.setVisible(true);
        
    }//GEN-LAST:event_BaddActionPerformed

    private void BshowallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BshowallActionPerformed
        Refresh_employees();
        flag = 1;
    }//GEN-LAST:event_BshowallActionPerformed

    private void CBorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBorderActionPerformed

        String orderClicked = (String) CBorder.getSelectedItem();
        String sql = "";
        if(!orderClicked.equals("-")){
            if(flag == 1){
            if(orderClicked.equals("Name"))
                sql = "Select * from EMPLOYEE\n" + "order by FNAME";
            else if(orderClicked.equals("Department Number"))
                sql = "Select * from EMPLOYEE\n" + "order by DEP_NO";
            }
        else if(flag == 2){
            if(orderClicked.equals("Name"))
                sql = sql_from_search + " order by FNAME";
            else if(orderClicked.equals("Department Number")){
                sql = sql_from_search + " order by DEP_NO";
            }
        }
            
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
           // System.out.println(sql);
            rs = ps.executeQuery();
            
            DefaultTableModel tm = (DefaultTableModel) Table_emp.getModel();
            tm.setColumnCount(10);
            String s[]={"Name","SSN","Address","Birth date","Phone number","Department working in",
                "Certification","Years of expirement", "Manages department:","Date of managing" };
            for(int col_index = 0; col_index < 10; col_index++)
            {
                Table_emp.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);
            }
            for(int i = 0; i<10; i++){
                TableColumn a = Table_emp.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }

            ArrayList<Long> ssn = new ArrayList<Long>();
            tm.setRowCount(0);
            while(rs.next()){
                ssn.add(rs.getLong("SSN"));
                Object o[]= {rs.getString("FNAME")+ " " +rs.getString("SNAME")+ " " +rs.getString("LNAME")
                    ,rs.getLong("SSN"),rs.getString("ADDRESS"), rs.getDate("BIRTH_DATE")
                    ,rs.getString("PHONE_NO"),""
                };
                //To get the department name from it's number
                String sql_dname = "select dep_name\n" +
                                  "from department, employee\n" +
                                  "where department.dep_number = '"+rs.getInt("DEP_NO")+"'";
                PreparedStatement ps_dname = con.prepareStatement(sql_dname);
                ResultSet rs_dname = ps_dname.executeQuery();
                rs_dname.next();
                o[5] = rs_dname.getString("DEP_NAME");
                
                tm.addRow(o);
                
            }

            sql = "select essn,certification, years_of_expirement\n" +
            "from procurement_engineer, employee\n" +
            "where procurement_engineer.essn = employee.ssn";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if (ssn.contains(rs.getLong("ESSN"))){
                    int ri = ssn.indexOf(rs.getLong("ESSN"));
                    tm.setValueAt(rs.getString("CERTIFICATION"), ri, 6);
                    tm.setValueAt(rs.getString("YEARS_OF_EXPIREMENT"), ri, 7);
                }
            }
            
            sql = "select essn, department.dep_name, start_date\n" +
                  "from employee, department\n" +
                  "where employee.ssn = department.essn";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if (ssn.contains(rs.getLong("ESSN")))
                {
                    int ri = ssn.indexOf(rs.getLong("ESSN"));
                    tm.setValueAt(rs.getString("DEP_NAME"), ri, 8);//Manages department:
                    tm.setValueAt(rs.getDate("START_DATE"), ri, 9);//Date of managing
                }
            }

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
        }
        
    }//GEN-LAST:event_CBorderActionPerformed

    private void BsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsearchActionPerformed

        String orderBy = "";
        String orderByClicked = (String) CBsearch.getSelectedItem();
        //System.out.println(orderByClicked);
        if((!Tsearch.getText().isEmpty()) && orderByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please select field to search by");
        else if(Tsearch.getText().isEmpty() && !orderByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please write what you're searching for");
        else if(!Tsearch.getText().isEmpty() && !orderByClicked.equals("By"))
        {
            String searchFor = (String) Tsearch.getText();
            if(orderByClicked.equals("Name"))
                orderBy = "FNAME";
            if(!orderBy.isEmpty())
                sql_from_search = "Select * from EMPLOYEE\n" + "where " + orderBy + " like '%" + searchFor +"%'";
            else 
            {
                if(orderByClicked.equals("Phone Number"))
                sql_from_search = "Select * from EMPLOYEE\n" + "where PHONE_NO" + " like '" + searchFor +"'";
                
                else if(orderByClicked.equals("SSN"))
                sql_from_search = "Select * from EMPLOYEE\n" + "where SSN" + " like '" + searchFor +"'";
                
                else if(orderByClicked.equals("Department Name"))
                sql_from_search ="Select * from EMPLOYEE, department\n" +
                                   "where DEPARTMENT.DEP_NAME" + " like '%" + searchFor +"%' "+
                                   "and department.dep_number = employee.dep_no";
                
            }

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql_from_search);
           // System.out.println(sql_from_search);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_emp.getModel();
            tm.setColumnCount(10);
            String s[]={"Name","SSN","Address","Birth date","Phone number","Department working in",
                "Certification","Years of expirement", "Manages department:","Date of managing" };
            for(int col_index = 0; col_index < 10; col_index++)
            {
                Table_emp.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);

            }
            for(int i = 0; i<10; i++){
                TableColumn a = Table_emp.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }

            ArrayList<Long> ssn = new ArrayList<Long>();
            tm.setRowCount(0);
            while(rs.next()){
                ssn.add(rs.getLong("SSN"));
                Object o[]= {rs.getString("FNAME")+ " " +rs.getString("SNAME")+ " " +rs.getString("LNAME")
                    ,rs.getLong("SSN"),rs.getString("ADDRESS"), rs.getDate("BIRTH_DATE")
                    ,rs.getString("PHONE_NO"),""
                };
                //To get the department name from it's number
                String sql_dname = "select dep_name\n" +
                                  "from department, employee\n" +
                                  "where department.dep_number = '"+rs.getInt("DEP_NO")+"'";
                PreparedStatement ps_dname = con.prepareStatement(sql_dname);
                ResultSet rs_dname = ps_dname.executeQuery();
                rs_dname.next();
                o[5] = rs_dname.getString("DEP_NAME");
                
                tm.addRow(o);
            }

            sql = "select essn,certification, years_of_expirement\n" +
            "from procurement_engineer, employee\n" +
            "where procurement_engineer.essn = employee.ssn";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if (ssn.contains(rs.getLong("ESSN"))){
                    int ri = ssn.indexOf(rs.getLong("ESSN"));
                    tm.setValueAt(rs.getString("CERTIFICATION"), ri, 6);
                    tm.setValueAt(rs.getString("YEARS_OF_EXPIREMENT"), ri, 7);
                }
            }

            sql = "select essn, department.dep_name, start_date\n" +
                  "from employee, department\n" +
                  "where employee.ssn = department.essn";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if (ssn.contains(rs.getLong("ESSN")))
                {
                    int ri = ssn.indexOf(rs.getLong("ESSN"));
                    tm.setValueAt(rs.getString("DEP_NAME"), ri, 8);//Manages department:
                    tm.setValueAt(rs.getDate("START_DATE"), ri, 9);//Date of managing
                }

            }
        CBorder.setEnabled(true);
        flag = 2;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        }
    }//GEN-LAST:event_BsearchActionPerformed

    ArrayList<Object> rowvalues = new ArrayList<Object>();
    private void BeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeditActionPerformed
      Fedit_emp edit = new Fedit_emp(rowvalues.get(1).toString());
      edit.setVisible(true);
      
      String name[] = rowvalues.get(0).toString().split(" ");
      edit.Tfname.setText(name[0]);
      edit.Tsname.setText(name[1]);
      edit.Tlname.setText(name[2]);
      
      //for(int i = 1; i < 11 ; i++){
      //    System.out.println(rowvalues.get(i));
      //}
      edit.Tssn.setText(rowvalues.get(1).toString());
      edit.Taddress.setText(rowvalues.get(2).toString());
        String date1 = rowvalues.get(3).toString();
        java.util.Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(date1); /////////////////////////////////////////////
            edit.DCbirth_date.setDate(date);
        }
        catch (ParseException ex) {
            Logger.getLogger(Fhome.class.getName()).log(Level.SEVERE, null, ex);
        }
      edit.Tphone.setText(rowvalues.get(4).toString());
      edit.CBdep_name.setSelectedItem(rowvalues.get(5).toString());
      
      //if engineer
      if(rowvalues.get(6) != null){
          edit.Tcertification.setText(rowvalues.get(6).toString());
          edit.Tyears.setText(rowvalues.get(7).toString());
        }

    }//GEN-LAST:event_BeditActionPerformed
    
    private void Table_empMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_empMouseClicked
      Bedit.setEnabled(true);
      Bdelete.setEnabled(true);
      //store selected row in array of strings
      int i = Table_emp.getSelectedRow();
      TableModel model=Table_emp.getModel(); 
      for (int j=0;j<8;j++)
      {
          rowvalues.add(j, model.getValueAt(i,j)); 
      }
    }//GEN-LAST:event_Table_empMouseClicked

    private void BdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BdeleteActionPerformed

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            int row =Table_emp.getSelectedRow();
            String value =(Table_emp.getModel().getValueAt(row, 1).toString()) ;//will get SSN
            //System.out.println(value);
            
            //if manager then can't delete
            String sql_if_manager = "select * from employee, department "
                    + "where employee.ssn = department.essn"
                    + " and employee.ssn = '" + value + "'";
            //System.out.println(sql_if_manager);
            PreparedStatement ps_if_manager = con.prepareStatement(sql_if_manager);
            ResultSet rs_if_manager = ps_if_manager.executeQuery();
            if(rs_if_manager.next()){
                JOptionPane.showMessageDialog(null, "Cannot delete a manager\nYou should select another manager for the department first.");
            }
            else {
                
              String query="DELETE FROM EMPLOYEE WHERE SSN='"+value+"'";      
            pst = con.prepareStatement(query);
            //System.out.println(sql_if_manager);
            pst.executeQuery();
            JOptionPane.showMessageDialog(null,"Deleted Successfully!");
            }
            
            Refresh_employees();
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_BdeleteActionPerformed

   
    
    
    
    
    //TAB PRODUCTS
    private void CBorder1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBorder1ActionPerformed

        String orderClicked = (String) CBorder1.getSelectedItem();
        String sql = "";
      if(!orderClicked.equals("-")){
            if(flag1 == 1){
            if(orderClicked.equals("Name"))
                sql = "Select * from PRODUCTS\n" + "order by NAME";
            else if(orderClicked.equals("Department")){
                sql = "Select \n" +
                       "NAME, PRODUCT_CODE, buying_price,WARANTY, manufacturer, country_of_origin, dep_no, selling_price, soled_amount, quantity, description\n" +
                       "from  products,department\n" +
                       "where DEPARTMENT.DEP_NUMBER = PRODUCTS.DEP_NO \n" +
                       " order by DEP_NAME " ; 
                                   //+"and department.dep_number = employee.dep_no";////////////
            }
                
            else if(orderClicked.equals("Manufacturer"))
                sql = "Select * from PRODUCTS\n" + "order by MANUFACTURER";
            else if(orderClicked.equals("Buying Price"))
                sql = "Select * from PRODUCTS\n" + "order by BUYING_PRICE";
            else if(orderClicked.equals("Selling Price"))
                sql = "Select * from PRODUCTS\n" + "order by SELLING_PRICE";
            else if(orderClicked.equals("Soled Amount"))
                sql = "Select * from PRODUCTS\n" + "order by SOLED_AMOUNT";
            else if(orderClicked.equals("Quantity"))
                sql = "Select * from PRODUCTS\n" + "order by QUANTITY";
            else if(orderClicked.equals("Waranty"))
                sql = "Select * from PRODUCTS\n" + "order by WARANTY";
            }
            
            
        else if(flag1 == 2){           
            if(orderClicked.equals("Name"))
                sql = sql_from_search1 + " order by NAME";
            else if(orderClicked.equals("Manufacturer"))
                sql = sql_from_search1 + " order by MANUFACTURER";
            else if(orderClicked.equals("Buying Price"))
                sql = sql_from_search1 + " order by BUYING_PRICE";
            else if(orderClicked.equals("Selling Price"))
                sql = sql_from_search1 + " order by SELLING_PRICE";
            else if(orderClicked.equals("Soled Amount"))
                sql = sql_from_search1 + " order by SOLED_AMOUNT";
            else if(orderClicked.equals("Quantity"))
                sql = sql_from_search1 + " order by QUANTITY";
            else if(orderClicked.equals("Waranty"))
                sql = sql_from_search1 + " order by WARANTY";
        }
        
            try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.println(sql);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_pro.getModel();
            tm.setColumnCount(11);
            
            String s[]={"Code","Name","Manufacturer","Country of origin", "Decription", "Buying Price", "Selling Price"
                    ,"Years of Waranty","Quantity", "Sold Amount", "In Department"};
            for(int col_index = 0; col_index < 11; col_index++)
            {
                Table_pro.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);

            }
            
            for(int i = 0; i<11; i++){
                TableColumn a = Table_pro.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }
            
            ArrayList<String> code = new ArrayList<String>();
            tm.setRowCount(0);
            while(rs.next()){
                code.add(rs.getString("PRODUCT_CODE"));
                Object o[]= {rs.getString("PRODUCT_CODE"),rs.getString("NAME"),rs.getString("MANUFACTURER")
                        , rs.getString("COUNTRY_OF_ORIGIN"), rs.getString("DESCRIPTION"),rs.getLong("BUYING_PRICE")
                        ,rs.getLong("SELLING_PRICE"),rs.getLong("WARANTY"),rs.getLong("QUANTITY"),rs.getLong("SOLED_AMOUNT")
                    ,""
                             };
                //To get the department name from it's number
                String sql_dname = "select dep_name\n" +
                                  "from department, employee\n" +
                                  "where department.dep_number = '"+rs.getInt("DEP_NO")+"'";
                PreparedStatement ps_dname = con.prepareStatement(sql_dname);
                ResultSet rs_dname = ps_dname.executeQuery();
                rs_dname.next();
                o[10] = rs_dname.getString("DEP_NAME");
                
                tm.addRow(o);
            }

            CBorder1.setEnabled(true);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        
        }
        
    }//GEN-LAST:event_CBorder1ActionPerformed
    
    ArrayList<Object> rowvalues1 = new ArrayList<Object>();
    private void Table_proMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_proMouseClicked

        //store selected row in array of strings
        int i = Table_pro.getSelectedRow();
        TableModel model=Table_pro.getModel(); 
        for (int j=0;j<11;j++)
        {
          rowvalues1.add(j, model.getValueAt(i,j)); 
        }
        
        Bedit1.setEnabled(true);
        Bdelete1.setEnabled(true);
        
 //       for(int o=0; o<11; o++){
   //         if(rowvalues1.get(o) == null)
     //           System.out.println(" ");
       //     else
         //       System.out.println(rowvalues1.get(o).toString());
       // }
    }//GEN-LAST:event_Table_proMouseClicked

    private void Bshowall1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bshowall1ActionPerformed
        Refresh_products(); 
        flag1 = 1;
    }//GEN-LAST:event_Bshowall1ActionPerformed

    private void Badd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Badd1ActionPerformed
        Fadd_pro add = new Fadd_pro();
        add.setVisible(true);
        add.Tsoled_amount.setText("0");
    }//GEN-LAST:event_Badd1ActionPerformed

    private void Bedit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bedit1ActionPerformed
        Fedit_pro edit = new Fedit_pro(rowvalues1.get(0).toString());
        edit.setVisible(true);

      // PRODUCT_CODE     ,NAME,             MANUFACTURER,     COUNTRY_OF ORIGIN,    DESCRIPTION
        // BUYING_PRICE,    SELLING_PRICE,     QUANTITY,         WARANTY,              SOLED_AMOUNT ,     DEP_NO
   
      edit.Tcode.setText(rowvalues1.get(0).toString());
      edit.Tname.setText(rowvalues1.get(1).toString());
      edit.Tmanufacturer.setText(rowvalues1.get(2).toString());
      edit.Tcountry.setText(rowvalues1.get(3).toString());
      if(rowvalues1.get(4) == null)
                edit.Tdescription.setText("");
            else
                edit.Tdescription.setText(rowvalues1.get(4).toString());
      edit.Tbprice.setText(rowvalues1.get(5).toString());
      edit.Tsprice.setText(rowvalues1.get(6).toString());
      edit.Tquantity.setText(rowvalues1.get(7).toString());
      edit.Twaranty.setText(rowvalues1.get(8).toString());
      edit.Tsoled_amount.setText(rowvalues1.get(9).toString());
      edit.CBdep.setSelectedItem(rowvalues1.get(10).toString());
      
      
    }//GEN-LAST:event_Bedit1ActionPerformed

    private void Bdelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bdelete1ActionPerformed
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            int row =Table_pro.getSelectedRow();
            String value =(Table_pro.getModel().getValueAt(row, 0).toString()) ;//will get product code
            //System.out.println(value);
            String query="DELETE FROM PRODUCTS WHERE PRODUCT_CODE ='"+value+"'";      
            pst = con.prepareStatement(query);
            pst.executeQuery();
            JOptionPane.showMessageDialog(null,"Deleted Successfully!");
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_Bdelete1ActionPerformed

    private void Bsearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bsearch1ActionPerformed
        
        
        String searchBy = "";
        String searchByClicked = (String) CBsearch1.getSelectedItem();
        //System.out.println(orderByClicked);
        if((!Tsearch1.getText().isEmpty()) && searchByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please select field to search by");
        else if(Tsearch1.getText().isEmpty() && !searchByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please write what you're searching for");
        else if(!Tsearch1.getText().isEmpty() && !searchByClicked.equals("By"))
        {
            String searchFor = (String) Tsearch1.getText();
         
            if(searchByClicked.equals("Name"))
                searchBy = "NAME";
            else if (searchByClicked.equals("Manufacturer"))
                searchBy = "MANUFACTURER";
            else if (searchByClicked.equals("Country of origin"))
                searchBy = "COUNTRY_OF_ORIGIN";
            
            if(!searchBy.isEmpty())
                sql_from_search1 = "Select * from PRODUCTS\n" + "where " + searchBy + " like '%" + searchFor +"%'";
            else if(searchByClicked.equals("Department Name")) 
                sql_from_search1 ="Select * from PRODUCTS, department\n" +
                                   "where DEPARTMENT.DEP_NAME" + " like '%" + searchFor +"%' "+
                                   "and department.dep_number = PRODUCTS.dep_no";
                
            try  
            {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql_from_search1);
            System.out.println(sql_from_search1);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_pro.getModel();
            tm.setColumnCount(11);
            
            String s[]={"Code","Name","Manufacturer","Country of origin", "Decription", "Buying Price", "Selling Price"
                    ,"Years of Waranty","Quantity", "Sold Amount", "In Department"};
            for(int col_index = 0; col_index < 11; col_index++)
            {
                Table_pro.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);

            }
            
            for(int i = 0; i<11; i++){
                TableColumn a = Table_pro.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }
            
            ArrayList<String> code = new ArrayList<String>();
            tm.setRowCount(0);
            while(rs.next()){
                code.add(rs.getString("PRODUCT_CODE"));
                Object o[]= {rs.getString("PRODUCT_CODE"),rs.getString("NAME"),rs.getString("MANUFACTURER")
                        , rs.getString("COUNTRY_OF_ORIGIN"), rs.getString("DESCRIPTION"),rs.getLong("BUYING_PRICE")
                        ,rs.getLong("SELLING_PRICE"),rs.getLong("WARANTY"),rs.getLong("QUANTITY"),rs.getLong("SOLED_AMOUNT")
                    ,""
                             };
                //To get the department name from it's number
                String sql_dname = "select dep_name\n" +
                                  "from department, employee\n" +
                                  "where department.dep_number = '"+rs.getInt("DEP_NO")+"'";
                PreparedStatement ps_dname = con.prepareStatement(sql_dname);
                ResultSet rs_dname = ps_dname.executeQuery();
                rs_dname.next();
                o[10] = rs_dname.getString("DEP_NAME");
                
                tm.addRow(o);
            }

            CBorder1.setEnabled(true);
            flag1 = 2;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        }

    }//GEN-LAST:event_Bsearch1ActionPerformed

    
      
    //TAB INTERNATIONAL EXHIBITIONS
    private void CBorder2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBorder2ActionPerformed
        String orderClicked = (String) CBorder2.getSelectedItem();
        String sql = "";
      if(!orderClicked.equals("-")){
        if(flag2 == 1){
            if(orderClicked.equals("Name"))
                sql = "Select * from INTERNATIONAL_EXHIBITION\n" + "order by EXNAME";
            else if(orderClicked.equals("Employees"))
                sql = "Select \n" +
                       "EXNAME, COUNTRY, COST_OF_TRAVEL, EXDATE, ESSN from  INTERNATIONAL_EXHIBITION,employee\n" +
                       "where INTERNATIONAL_EXHIBITION.ESSN = EMPLOYEE.SSN \n" +
                       "order by FNAME " ; 
                                   //+"and department.dep_number = employee.dep_no";////////////
            else if(orderClicked.equals("Cost"))
                sql = "Select * from INTERNATIONAL_EXHIBITION\n" + "order by COST_OF_TRAVEL";
        }
        else if(flag2 == 2){           
            if(orderClicked.equals("Name"))
                sql = sql_from_search2 + "order by NAME";
            if(orderClicked.equals("Country"))
                sql = sql_from_search2 + "order by COUNTRY";
        }
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_ex.getModel();
            tm.setColumnCount(5);

            String s[]={"NAME","COUNTRY","DATE","COST_OF_TRAVEL", "Employee going"};
            for(int col_index = 0; col_index < 5; col_index++)
            {
                Table_ex.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);
            }
            
            Table_ex.getColumnModel().getColumn(0).setPreferredWidth(150);
            for(int i = 1; i<5; i++){
                TableColumn a = Table_ex.getColumnModel().getColumn(i);
                a.setPreferredWidth(100);
            }
            Table_ex.getColumnModel().getColumn(4).setPreferredWidth(150);
            
            ArrayList<String> pk1 = new ArrayList<String>();
            ArrayList<String> pk2 = new ArrayList<String>();
            
            tm.setRowCount(0);
            
            String exName ="";
            String exDate ="";
            while(rs.next()){
                
                exName = rs.getString("EXNAME");
                exDate = rs.getDate("EXDATE").toString();
                pk1.add(exName);
                pk2.add(exDate);
                
                Object o[]= {rs.getString("EXNAME"),rs.getString("COUNTRY"),rs.getDate("EXDATE")
                        ,rs.getInt("COST_OF_TRAVEL"), ""
                        };
                
                //To get the employee
                String sql_emp = "select fname, sname, lname\n" +
                                 "from employee,international_exhibition\n" +
                                 " where employee.SSN = international_exhibition.ESSN"+
                                 " and international_exhibition.EXNAME = '"+ exName +"'" +
                                 " and international_exhibition.EXDATE = TO_DATE('"+ exDate +"','YYYY-MM-dd')";
                
                System.out.println(sql_emp);
                PreparedStatement ps_emp = con.prepareStatement(sql_emp);
                ResultSet rs_emp = ps_emp.executeQuery();
                
                while(rs_emp.next()){
                    o[4] = rs_emp.getString("FNAME")+" "+rs_emp.getString("SNAME")
                         +" "+rs_emp.getString("LNAME");
                }
                
                tm.addRow(o);  
            }
            CBorder2.setEnabled(true);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
        
      }
    }//GEN-LAST:event_CBorder2ActionPerformed

    ArrayList<Object> rowvalues2 = new ArrayList<Object>();
    private void Table_exMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_exMouseClicked
        Bedit2.setEnabled(true);
        Bdelete2.setEnabled(true);
        //store selected row in array of strings
        int i = Table_ex.getSelectedRow();
        TableModel model=Table_ex.getModel(); 
        for (int j=0;j<5;j++)
        {
          rowvalues2.add(j, model.getValueAt(i,j)); 
        }
        for (int j=0;j<5;j++)
        {
          System.out.println(rowvalues2.get(j)); 
        }
        
    }//GEN-LAST:event_Table_exMouseClicked

    private void Bshowall2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bshowall2ActionPerformed
        Refresh_exhibitions();
        flag2 = 1;
    }//GEN-LAST:event_Bshowall2ActionPerformed

    private void Badd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Badd2ActionPerformed
        Fadd_ex add = new Fadd_ex();
        add.setVisible(true);
    }//GEN-LAST:event_Badd2ActionPerformed

    private void Bedit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bedit2ActionPerformed
    
        Fedit_ex edit = new Fedit_ex(rowvalues2.get(0).toString(), rowvalues2.get(2).toString());
        edit.setVisible(true);
        
      edit.Tname.setText(rowvalues2.get(0).toString());
      edit.Tcountry.setText(rowvalues2.get(1).toString());
      String date1 = rowvalues2.get(2).toString();
      java.util.Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(date1); /////////////////////////////////////////////
            edit.DCdate.setDate(date);
        }
        catch (ParseException ex) {
            Logger.getLogger(Fhome.class.getName()).log(Level.SEVERE, null, ex);
        }
      edit.Tcost.setText(rowvalues2.get(3).toString());
      String name = rowvalues2.get(4).toString();
      String s[] = name.split(" ");
      edit.Tfname.setText(s[0]);
      edit.Tsname.setText(s[1]);
      edit.Tlname.setText(s[2]);
      
    }//GEN-LAST:event_Bedit2ActionPerformed

    private void Bdelete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bdelete2ActionPerformed
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            int row =Table_ex.getSelectedRow();
            String value1 =(Table_ex.getModel().getValueAt(row, 0).toString()) ;//will get exname
            String value2 =(Table_ex.getModel().getValueAt(row, 2).toString()) ;// exdate
            //System.out.println(value);
            String query="DELETE FROM INTERNATIONAL_EXHIBITION WHERE EXNAME ='"+value1+"' "
                    + "and EXDATE =" +"TO_DATE('"+ value2 +"','YYYY-MM-dd')" ;      
            pst = con.prepareStatement(query);
            pst.executeQuery();
            JOptionPane.showMessageDialog(null,"Deleted Successfully!");
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_Bdelete2ActionPerformed

    private void Bsearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bsearch2ActionPerformed
        String searchBy = "";
        String searchByClicked = (String) CBsearch2.getSelectedItem();
        //System.out.println(orderByClicked);
        if((!Tsearch2.getText().isEmpty()) && searchByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please select field to search by");
        else if(Tsearch2.getText().isEmpty() && !searchByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please write what you're searching for");
        else if(!Tsearch2.getText().isEmpty() && !searchByClicked.equals("By"))
        {
            String searchFor = (String) Tsearch2.getText();
         
            if(searchByClicked.equals("Name"))
                searchBy = "EXNAME";
            else if (searchByClicked.equals("Country"))
                searchBy = "COUNTRY";
            
            if(!searchBy.isEmpty())
                sql_from_search2 = "Select * from INTERNATIONAL_EXHIBITION\n" + "where " + searchBy + " like '%" + searchFor +"%'";
         
            try{
                
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql_from_search2);
            System.out.println(sql_from_search2);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_ex.getModel();
            tm.setColumnCount(5);

            String s[]={"Name","Country","Date","Cost Of Travel", "Employee Going"};
            for(int col_index = 0; col_index < 5; col_index++)
            {
                Table_ex.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);
            }
            
            Table_ex.getColumnModel().getColumn(0).setPreferredWidth(150);
            for(int i = 1; i<5; i++){
                TableColumn a = Table_ex.getColumnModel().getColumn(i);
                a.setPreferredWidth(100);
            }
            Table_ex.getColumnModel().getColumn(4).setPreferredWidth(150);
            
            ArrayList<String> pk1 = new ArrayList<String>();
            ArrayList<String> pk2 = new ArrayList<String>();
            
            tm.setRowCount(0);
            
            String exName ="";
            String exDate ="";
            while(rs.next()){
                
                exName = rs.getString("EXNAME");
                exDate = rs.getDate("EXDATE").toString();
                pk1.add(exName);
                pk2.add(exDate);
                
                Object o[]= {rs.getString("EXNAME"),rs.getString("COUNTRY"),rs.getDate("EXDATE")
                        ,rs.getInt("COST_OF_TRAVEL"), ""
                        };
                
                //To get the employee
                String sql_emp = "select fname, sname, lname\n" +
                                 "from employee,international_exhibition\n" +
                                 " where employee.SSN = international_exhibition.ESSN"+
                                 " and international_exhibition.EXNAME = '"+ exName +"'" +
                                 " and international_exhibition.EXDATE = TO_DATE('"+ exDate +"','YYYY-MM-dd')";
                
                System.out.println(sql_emp);
                PreparedStatement ps_emp = con.prepareStatement(sql_emp);
                ResultSet rs_emp = ps_emp.executeQuery();
                
                while(rs_emp.next()){
                    o[4] = rs_emp.getString("FNAME")+" "+rs_emp.getString("SNAME")
                         +" "+rs_emp.getString("LNAME");
                }
                
                tm.addRow(o);  
            }
            
            CBorder2.setEnabled(true);
            flag2 = 2;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
            
        }   
    }//GEN-LAST:event_Bsearch2ActionPerformed

    
    
    
    //TAB DEPARTMENTS
    private void CBorder4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBorder4ActionPerformed
/*-
Department Name
Location
        */
        String orderClicked = (String) CBorder4.getSelectedItem();
        String sql = "";
        if(!orderClicked.equals("-")){
            if(flag4 == 1){
            if(orderClicked.equals("Department Name"))
                sql = "Select * from DEPARTMENT\n" + "order by DEP_NAME";
            else if(orderClicked.equals("Location"))
                sql = "Select * from DEPARTMENT\n" + "order by LOCATION";
            }
        else if(flag4 == 2){
            if(orderClicked.equals("Department Name"))
                sql = sql_from_search4 + " order by DEP_NAME";
            else if(orderClicked.equals("Location"))
                sql = sql_from_search4 + " order by LOCATION";
        }
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.println(sql);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_dep.getModel();
            tm.setColumnCount(8);

            String s[]={"Dep Number","Name","Telephone","Location","Manager","Opening Time","Closing Time", "Date Of Managing"};
            
            for(int col_index = 0; col_index < 8; col_index++)
            {
                Table_dep.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);
            }
            
            Table_dep.getColumnModel().getColumn(0).setPreferredWidth(70);
            for(int i = 1; i<5; i++){
                TableColumn a = Table_dep.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }
            Table_dep.getColumnModel().getColumn(5).setPreferredWidth(85);
            Table_dep.getColumnModel().getColumn(6).setPreferredWidth(85);
            Table_dep.getColumnModel().getColumn(7).setPreferredWidth(150);
            
            ArrayList<Integer> no = new ArrayList<Integer>();
            tm.setRowCount(0);
            int DEPNO =0;
            
            while(rs.next()){
                DEPNO = rs.getInt("DEP_NUMBER");
                no.add(DEPNO);
                
                Object o[]= {rs.getInt("DEP_NUMBER"),rs.getString("DEP_NAME"),rs.getString("TELEPHONE_NO")
                        ,rs.getString("LOCATION"),""    ,"","", rs.getDate("START_DATE")
                        };
                
                //to represent times 
                String sf[] = rs.getTimestamp("OPENING_TIME").toString().split(" ");
                String ss[] = sf[1].split(":");
                o[5] = ss[0] + ":" + ss[1];
                
                String sf1[] = rs.getTimestamp("CLOSING_TIME").toString().split(" ");
                String ss1[] = sf1[1].split(":");
                o[6] = ss1[0] + ":" + ss1[1];
                
                //To get the manager
                String sql_manager = "select fname, sname, lname\n" +
                                 "from employee,department\n" +
                                 " where employee.ssn = DEPARTMENT.essn "
                                 + "and dep_number = '"+DEPNO+"'";
                
                PreparedStatement ps_manager = con.prepareStatement(sql_manager);
                ResultSet rs_manager = ps_manager.executeQuery();
            
                while(rs_manager.next()){
                    o[4] = rs_manager.getString("FNAME")+" "+rs_manager.getString("SNAME")
                         +" "+rs_manager.getString("LNAME");
                }
                
             
                tm.addRow(o);  
            }
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
        }
    }//GEN-LAST:event_CBorder4ActionPerformed

    ArrayList<Object> rowvalues4 = new ArrayList<Object>();
    private void Table_depMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_depMouseClicked
        Bedit4.setEnabled(true);
        Bdelete4.setEnabled(true);
        //store selected row in array of strings
        int i = Table_dep.getSelectedRow();
        TableModel model=Table_dep.getModel(); 
        for (int j=0;j<8;j++)
        {
          rowvalues4.add(j, model.getValueAt(i,j)); 
        }
        Bshow_products.setEnabled(true);
        
        String S = "select NAME from products, department where products.DEP_NO = DEPARTMENT.DEP_NUMBER "
                + "and products.DEP_NO = '" + rowvalues4.get(0) +"'";
        try{
            PreparedStatement P = con.prepareStatement(S);
            ResultSet R = P.executeQuery();
            while(R.next()){
                productRows.add(R.getString("NAME"));
            }
            int count = 0;
            while(count < productRows.size()){
                productRows.get(count);
                System.out.println(count);
                count++;
            }
        }
        catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
                }
        //for(int o=0; o<8; o++){
        //System.out.println(rowvalues4.get(o).toString());
        //}
        
    }//GEN-LAST:event_Table_depMouseClicked

    private void Bshowall4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bshowall4ActionPerformed
        Refresh_departments();
        flag4 = 1;
    }//GEN-LAST:event_Bshowall4ActionPerformed

    private void Badd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Badd4ActionPerformed
        Fadd_dep_inf add = new Fadd_dep_inf();
        add.setVisible(true);
    }//GEN-LAST:event_Badd4ActionPerformed

    private void Bedit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bedit4ActionPerformed
        Fedit_dep_inf edit = new Fedit_dep_inf((int)rowvalues4.get(0));
        edit.setVisible(true);
      //{"Dep Number","Name","Telephone","Location","Manager" ,"Opening Time","Closing Time", "Start date"}
      edit.Tdep_no.setText(rowvalues4.get(0).toString());
      edit.Tname.setText(rowvalues4.get(1).toString());
      edit.Tphone_no.setText(rowvalues4.get(2).toString());
      edit.Tlocation.setText(rowvalues4.get(3).toString());
      String manager_name = rowvalues4.get(4).toString();
      String sp[] = manager_name.split(" ");
      edit.Tfmanager.setText(sp[0]);
      edit.Tsmanager.setText(sp[1]);
      edit.Tlmanager.setText(sp[2]);
      edit.Topening_time.setText(rowvalues4.get(5).toString());
      edit.Tclosing_time.setText(rowvalues4.get(6).toString());
      Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(rowvalues4.get(7).toString());
        } catch (ParseException ex) {
            Logger.getLogger(Fhome.class.getName()).log(Level.SEVERE, null, ex);
        }
      edit.DCdate_manage.setDate(date);
      
      
    }//GEN-LAST:event_Bedit4ActionPerformed

    private void Bdelete4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bdelete4ActionPerformed
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            int row =Table_dep.getSelectedRow();
            String value =(Table_dep.getModel().getValueAt(row, 0).toString()) ;//will get department number
            System.out.println(value);
            String query="DELETE FROM DEPARTMENT WHERE DEP_NUMBER ='"+value+"'";      
            pst = con.prepareStatement(query);
            pst.executeQuery();
            JOptionPane.showMessageDialog(null,"Deleted Successfully!");
            
            Refresh_departments();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_Bdelete4ActionPerformed

    private void Bsearch4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bsearch4ActionPerformed
       
        String searchBy = "";
        String searchByClicked = (String) CBsearch4.getSelectedItem();
        //System.out.println(orderByClicked);
        if((!Tsearch4.getText().isEmpty()) && searchByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please select field to search by");
        else if(Tsearch4.getText().isEmpty() && !searchByClicked.equals("By"))
            JOptionPane.showMessageDialog(null,"Please write what you're searching for");
        else if(!Tsearch4.getText().isEmpty() && !searchByClicked.equals("By"))
        {
            String searchFor = (String) Tsearch4.getText();
            if(searchByClicked.equals("Department Name"))
                searchBy = "DEP_NAME";
            if(searchByClicked.equals("Location"))
                searchBy = "LOCATION";
            
            if(!searchBy.isEmpty())
                sql_from_search4 = "Select * from DEPARTMENT\n" + "where " + searchBy + " like '%" + searchFor +"%'";
            else 
            {
                if(searchByClicked.equals("Telephone Number"))
                sql_from_search4 = "Select * from DEPARTMENT\n" + "where TELEPHONE_NO" + " like '" + searchFor +"'";
                
                else if(searchByClicked.equals("Department Number"))
                sql_from_search4 = "Select * from DEPARTMENT\n" + "where DEP_NUMBER" + " like '" + searchFor +"'";
                
                else if(searchByClicked.equals("Manager"))
                sql_from_search4 ="Select DEP_NAME, TELEPHONE_NO,DEP_NUMBER,LOCATION, OPENING_TIME,CLOSING_TIME,ESSN,START_DATE\n" +
                                 "from department, employee\n" +
                                 "where employee.fname like '%" + searchFor +"%' "+
                                 "and department.essn = employee.ssn";
            }
            
            try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            PreparedStatement ps = con.prepareStatement(sql_from_search4);
            System.out.println(sql_from_search4);
            rs = ps.executeQuery();

            DefaultTableModel tm = (DefaultTableModel) Table_dep.getModel();
            tm.setColumnCount(8);

            String s[]={"Dep Number","Name","Telephone","Location","Manager","Opening Time","Closing Time", "Date Of Managing"};
            
            for(int col_index = 0; col_index < 8; col_index++)
            {
                Table_dep.getColumnModel().getColumn(col_index).setHeaderValue(s[col_index]);
            }
            
            Table_dep.getColumnModel().getColumn(0).setPreferredWidth(70);
            for(int i = 1; i<5; i++){
                TableColumn a = Table_dep.getColumnModel().getColumn(i);
                a.setPreferredWidth(150);
            }
            Table_dep.getColumnModel().getColumn(5).setPreferredWidth(85);
            Table_dep.getColumnModel().getColumn(6).setPreferredWidth(85);
            Table_dep.getColumnModel().getColumn(7).setPreferredWidth(150);
            
            ArrayList<Integer> no = new ArrayList<Integer>();
            tm.setRowCount(0);
            int DEPNO =0;
            
            while(rs.next()){
                DEPNO = rs.getInt("DEP_NUMBER");
                no.add(DEPNO);
                
                Object o[]= {rs.getInt("DEP_NUMBER"),rs.getString("DEP_NAME"),rs.getString("TELEPHONE_NO")
                        ,rs.getString("LOCATION"),""    ,"","", rs.getDate("START_DATE")
                        };
                
                //to represent times 
                String sf[] = rs.getTimestamp("OPENING_TIME").toString().split(" ");
                String ss[] = sf[1].split(":");
                o[5] = ss[0] + ":" + ss[1];
                
                String sf1[] = rs.getTimestamp("CLOSING_TIME").toString().split(" ");
                String ss1[] = sf1[1].split(":");
                o[6] = ss1[0] + ":" + ss1[1];
                
                //To get the manager
                String sql_manager = "select fname, sname, lname\n" +
                                 "from employee,department\n" +
                                 " where employee.ssn = DEPARTMENT.essn "
                                 + "and dep_number = '"+DEPNO+"'";
                
                PreparedStatement ps_manager = con.prepareStatement(sql_manager);
                ResultSet rs_manager = ps_manager.executeQuery();
            
                while(rs_manager.next()){
                    o[4] = rs_manager.getString("FNAME")+" "+rs_manager.getString("SNAME")
                         +" "+rs_manager.getString("LNAME");
                }
                
             
                tm.addRow(o);  
            }
            
            CBorder4.setEnabled(true);
            flag4 = 2;
         }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
               
        }
    }//GEN-LAST:event_Bsearch4ActionPerformed

    
    
    private void refresh1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh1MouseClicked
        Refresh_products();
        Bedit1.setEnabled(false);
        Bdelete1.setEnabled(false);
    }//GEN-LAST:event_refresh1MouseClicked

    private void refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshMouseClicked
        Refresh_employees();
        Bdelete.setEnabled(false);
        Bedit.setEnabled(false);
    }//GEN-LAST:event_refreshMouseClicked

    private void refresh4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh4MouseClicked
        Refresh_departments();
        Tdep_pro.removeAll();
        Bdelete4.setEnabled(false);
        Bedit4.setEnabled(false);
    }//GEN-LAST:event_refresh4MouseClicked
   
    private void tab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab1);
        Refresh_employees(); 
        flag = 1;
    }//GEN-LAST:event_tab1MouseClicked

    private void tab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab2);
        Refresh_products(); 
        flag1 = 1;
    }//GEN-LAST:event_tab2MouseClicked

    private void tab3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab3);
        Refresh_departments(); 
        flag4 = 1;
    }//GEN-LAST:event_tab3MouseClicked

    private void tab4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseClicked
        jTabbedPane1.setVisible(true);
        jTabbedPane1.setSelectedComponent(tab4);
        Refresh_exhibitions(); 
        flag2 = 1;
    }//GEN-LAST:event_tab4MouseClicked

    
    
    ArrayList<String> productRows = new ArrayList<String>();
    private void Bshow_productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bshow_productsActionPerformed

            
            DefaultTableModel tpd = (DefaultTableModel) Tdep_pro.getModel();
            
            int count = 0;
            String column[];
            column = new String[productRows.size()];
            
            while(count < productRows.size()){
                column[count] = productRows.get(count);
                //tpd.setValueAt(productRows.get(count), count, 0);
                System.out.println(productRows.get(count));
                count++;
            }
            tpd.addColumn("Products Stored In", column);
                
    }//GEN-LAST:event_Bshow_productsActionPerformed

    private void refresh2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh2MouseClicked
        Refresh_exhibitions();
        Bdelete2.setEnabled(false);
        Bedit2.setEnabled(false);
    }//GEN-LAST:event_refresh2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
           Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","manar","123456");
            JasperReport jasperReport = null;
            InputStream path = this.getClass().getResourceAsStream("report.jrxml");
            JasperPrint jasperPrint = null;
            
           //String path ="report.jrxml";
            jasperReport  =JasperCompileManager.compileReport(path);
            HashMap parameters = new HashMap();
            jasperPrint =JasperFillManager.fillReport(jasperReport, parameters,con);
            JasperViewer.viewReport(jasperPrint,false);
            
            con.close();
        } 
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
           } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(Fhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(Fhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(Fhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Fhome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fhome().setVisible(true);
            }
        });
        
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Badd;
    private javax.swing.JButton Badd1;
    private javax.swing.JButton Badd2;
    private javax.swing.JButton Badd4;
    public static javax.swing.JButton Bdelete;
    public static javax.swing.JButton Bdelete1;
    public static javax.swing.JButton Bdelete2;
    public static javax.swing.JButton Bdelete4;
    public static javax.swing.JButton Bedit;
    public static javax.swing.JButton Bedit1;
    public static javax.swing.JButton Bedit2;
    public static javax.swing.JButton Bedit4;
    private javax.swing.JButton Bsearch;
    private javax.swing.JButton Bsearch1;
    private javax.swing.JButton Bsearch2;
    private javax.swing.JButton Bsearch4;
    public javax.swing.JButton Bshow_products;
    private javax.swing.JButton Bshowall;
    private javax.swing.JButton Bshowall1;
    private javax.swing.JButton Bshowall2;
    private javax.swing.JButton Bshowall4;
    private javax.swing.JComboBox CBorder;
    private javax.swing.JComboBox CBorder1;
    private javax.swing.JComboBox CBorder2;
    private javax.swing.JComboBox CBorder4;
    private javax.swing.JComboBox CBsearch;
    private javax.swing.JComboBox CBsearch1;
    private javax.swing.JComboBox CBsearch2;
    private javax.swing.JComboBox CBsearch4;
    private javax.swing.JLabel LDEP;
    private javax.swing.JLabel LEMP;
    private javax.swing.JLabel LEXH;
    private javax.swing.JLabel LPRO;
    private javax.swing.JTable Table_dep;
    private javax.swing.JTable Table_emp;
    private javax.swing.JTable Table_ex;
    private javax.swing.JTable Table_pro;
    private javax.swing.JTable Tdep_pro;
    private javax.swing.JTextField Tsearch;
    private javax.swing.JTextField Tsearch1;
    private javax.swing.JTextField Tsearch2;
    private javax.swing.JTextField Tsearch4;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel refresh;
    private javax.swing.JLabel refresh1;
    private javax.swing.JLabel refresh2;
    private javax.swing.JLabel refresh4;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JPanel tab4;
    // End of variables declaration//GEN-END:variables
}
