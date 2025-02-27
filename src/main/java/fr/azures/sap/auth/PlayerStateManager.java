package fr.azures.sap.auth;

import com.password4j.Hash;
import com.password4j.Password;

import fr.azures.sap.SimpleAuthPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerStateManager {

    private Connection conn;

    public PlayerStateManager() throws SQLException, ClassNotFoundException {
    	conn = DriverManager.getConnection("jdbc:sqlite:" + SimpleAuthPlugin.pluginFolder + "/players.db");
        if (this.conn != null) {
            Statement stmt = this.conn.createStatement();
            if (stmt != null) {
                String createPlayerTableIfMissing = "CREATE TABLE IF NOT EXISTS players(" 
                + "    username text PRIMARY KEY," 
                + "    password text NOT NULL"
                + ");";
                stmt.execute(createPlayerTableIfMissing);
                String createNotLoggedPlayerTableIfMissing = "CREATE TABLE IF NOT EXISTS notlogged(" 
                + "username text PRIMARY KEY);";
                stmt.execute(createNotLoggedPlayerTableIfMissing);
            }
        }
    }

    public void addPlayerToNotLoggedTable(String username) throws SQLException {
        if (this.conn != null) {
            String addPlayerToNotLoggedTable = "INSERT INTO notlogged(username) VALUES(?)";
            PreparedStatement pstmt = this.conn.prepareStatement(addPlayerToNotLoggedTable);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        }
    }

    public void removePlayerFromNotLoggedTable(String username) throws SQLException {
        if (this.conn != null) {
            String removePlayerFromNotLoggedTable = "DELETE FROM notlogged WHERE username = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(removePlayerFromNotLoggedTable);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        }
    }

    public boolean isPlayerRegistered(String username) throws SQLException {
        if (this.conn != null) {
            String isPlayerRegistered = "SELECT COUNT(*) FROM players where username = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(isPlayerRegistered);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                System.out.println(count);
                return count > 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isPlayerLogged(String username) throws SQLException {
        if (this.conn != null) {
            String isPlayerRegistered = "SELECT COUNT(*) FROM notlogged where username = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(isPlayerRegistered);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                return count > 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean register(String username, String password) throws SQLException {
        if (this.conn != null) {
            Hash hashedPassword = Password.hash(password).withBcrypt();
            String registerPlayer = "INSERT INTO players(username, password) VALUES(?,?)";
            PreparedStatement pstmt = this.conn.prepareStatement(registerPlayer);
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword.toString());
            pstmt.execute(registerPlayer);
            this.removePlayerFromNotLoggedTable(username);
            return true;
        } else {
            return false;
        }
    }

    public boolean login(String username, String password) throws SQLException {
        if (this.conn != null) {
            String selectPlayerPassword = "SELECT password FROM players WEHRE username = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(selectPlayerPassword);
            pstmt.setString(1, username);
            pstmt.execute(selectPlayerPassword);
            ResultSet result = pstmt.executeQuery();
            String hashedPassword = result.getString("password");
            boolean isPasswordCorrect = Password.check(password, hashedPassword).withBcrypt();
            if (isPasswordCorrect) {
                this.removePlayerFromNotLoggedTable(username);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void changePassword(String username, String password) throws SQLException {
        String updatePassword = "UPDATE players SET password = ? WEHRE username = ?";
        Hash hashedPassword = Password.hash(password).withBcrypt();
        PreparedStatement pstmt = this.conn.prepareStatement(updatePassword);
        pstmt.setString(1, hashedPassword.toString());
        pstmt.setString(2, username);
        pstmt.executeUpdate();
    }
}