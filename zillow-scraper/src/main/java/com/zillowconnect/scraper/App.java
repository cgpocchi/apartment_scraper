package com.zillowconnect.scraper;

public class App 
{
    public static void main( String[] args )
    {
        try 
        {
            String url = "jdbc:mysql://localhost:3306/bos_move";
            String user = "root";
            String pass = "Occhi16183!";

            MySQLConnector con = new MySQLConnector(url, user, pass);
            con.export_query("select * from Agent", "/Users/ChristianPolydor/scrap/test2.csv");
            con.close_connection();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
