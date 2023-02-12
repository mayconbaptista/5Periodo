package com.oracle.tutorial.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.oracle.tutorial.jdbc.JDBCTutorialUtilities;


public class MyQueries2 {

    Connection con;
    JDBCUtilities settings;

    public MyQueries2(Connection connArg, JDBCUtilities settingsArg) {
        this.con = connArg;
        this.settings = settingsArg;
    }

    public static void getMyData(Connection con) throws SQLException {
        Statement stmt = null;
        String query = "select c.nome_cliente as nome " +
                "from cliente c " +
                "where c.nome_cliente in (select d.nome_cliente from deposito d) " +
                "and c.nome_cliente not in (select e.nome_cliente  from emprestimo e) ";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Clientes");
            while (rs.next()) {
                String nome = rs.getString("nome");

                System.out.println(" Nome: " + nome);
            }
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public static List<String> readTXT () throws IOException { 
        BufferedReader inputStream = null;
        Scanner scanned_line = null;
        String line;
        String[] value;
        value = new String[7];

        int countv;
        List<String> lista = new ArrayList<>();
        try { 
            inputStream = new BufferedReader(new FileReader("debito-populate-table.txt"));
            while ((line = inputStream.readLine()) != null) { 
                countv=0;
                
                // System.out.println("<<");
                // split fields separated by tab delimiters
                scanned_line = new Scanner(line);
                scanned_line.useDelimiter("\t");
                while (scanned_line.hasNext()) {
                    value[countv++]=scanned_line.next();
                } //while
                
                if (scanned_line != null) { scanned_line.close();}
                //System.out.println(">>");
                lista.add("insert into debito (numero_debito, valor_debito,  motivo_debito, data_debito, numero_conta, nome_agencia, nome_cliente) " 
                    + "values (" + value[0] +", "+ value[1] +", "+ value[2] +", '"+ value[3] +"', "+ value[4] +", '"+ value[5] +"', '"+ value[6] + "');" );
            } //while
        }
        finally { if (inputStream != null) {
            inputStream.close();
        }} //if & finally

        return lista;
    } //main

    public static void populateTable (Connection con)throws SQLException{
        Statement stmt = null;
        
        try{
            stmt = con.createStatement();
            System.out.println("Executando DDL/DML");
            stmt.executeUpdate("truncate table debito;");
            List <String> querys = readTXT();
            
            if(querys.size() > 0){
                for(String item : querys){
                    stmt.executeUpdate(item);
                }
            }
        }catch(SQLException e){
            JDBCUtilities.printSQLException(e);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(stmt != null) {stmt.close();}
        }
    } 

    public static void getMyData3(Connection connection) throws SQLException{
        Statement stmt = null;
        String query = "select d.nome_cliente as nome_cliente, sum(d.saldo_deposito) as soma_deposito, sum(e.valor_emprestimo) as soma_emprestimo "
            +"from deposito as d, emprestimo as e "
            +"where d.nome_cliente = e.nome_cliente "
            +"group by d.nome_cliente, d.nome_agencia, d.numero_conta;";

        try{
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Nomes de todos os clientes do banco, com suas respectivas somas de depósitos e empréstimos: ");
            while(rs.next()){
                String nome_cliente = rs.getString("nome_cliente");
                Float soma_deposito = rs.getFloat("soma_deposito");
                Float soma_emprestimo = rs.getFloat("soma_emprestimo");

                System.out.println(nome_cliente + " soma_deposito: " + soma_deposito + " soma_emprestimo: " + soma_emprestimo);
            }
        }catch(SQLException e){
            JDBCUtilities.printSQLException(e);
        }finally{
            if(stmt != null){stmt.close();}
        }
    }

    public static void cursorHoldabilitySupport (Connection connection) throws SQLException{
        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println("ResultSet.HOLD_CURSORS_OVER_COMMIT = " + ResultSet.HOLD_CURSORS_OVER_COMMIT);
        System.out.println("ResultSet.CLOSE_CURSORS_AT_COMMIT = " + ResultSet.CLOSE_CURSORS_AT_COMMIT);
        System.out.println("Defalt cursor Holdability: " + dbMetaData.getResultSetHoldability());
        System.out.println("Supports HOLD_CURSORS_OVER_COMMIT ? " + dbMetaData.supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
        System.out.println("Supports CLOSE_CURSORS_AT_COMMIT ? " + dbMetaData.supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT));

        System.out.println(dbMetaData.supportsResultSetConcurrency(0, 0));
    }

    /**
     * @param connection
     * @throws SQLException
     */
    public static void modifyPrinces (Connection connection) throws SQLException{

        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet uprs = stmt.executeQuery("select * from COFFEES");

            while(uprs.next()){
                float f = uprs.getFloat("PRICE");
                uprs.updateFloat("PRICE", (float) (f * 1.005));
                uprs.updateRow();
            }
        }catch(SQLException e){
            JDBCTutorialUtilities.printSQLException(e);
        }finally{
            if(stmt != null){
                stmt.close();
            }
        }
    }

    public static void main(String[] args) {
        JDBCUtilities myJDBCUtilities;
        Connection myConnection = null;
        if (args[0] == null) {
            System.err.println("Properties file not specified at command line");
            return;
        } else {
            try {
                myJDBCUtilities = new JDBCUtilities(args[0]);
            } catch (Exception e) {
                System.err.println("Problem reading properties file " + args[0]);
                e.printStackTrace();
                return;
            }
        }

        try {
            myConnection = myJDBCUtilities.getConnection();

            //MyQueries2.getMyData(myConnection);
            //MyQueries2.populateTable(myConnection);
            //MyQueries2.getMyData3(myConnection);
            //MyQueries2.cursorHoldabilitySupport(myConnection);
            MyQueries2.modifyPrinces(myConnection);

        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
        } finally {
            JDBCUtilities.closeConnection(myConnection);
        }

    }
}
