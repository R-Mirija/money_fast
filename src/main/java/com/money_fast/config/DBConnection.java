package com.money_fast.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection connection = null;

	// Constructeur privé pour empêcher l'instanciation
	private DBConnection() {
	}

	public static Connection getConnection() {
		if (connection == null) {
			try {
				// Chargement du fichier .env
				Dotenv dotenv = Dotenv.load();

				String host = dotenv.get("DB_HOST");
				String port = dotenv.get("DB_PORT");
				String dbName = dotenv.get("DB_NAME");
				String user = dotenv.get("DB_USER");
				String password = dotenv.get("DB_PASSWORD");

				String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&serverTimezone=UTC";

				Class.forName("com.mysql.cj.jdbc.Driver");

				connection = DriverManager.getConnection(url, user, password);
				System.out.println("Connexion à la base de données réussie !!");

			} catch (ClassNotFoundException e) {
				System.err.println("Driver MySQL non trouvé : " + e.getMessage());
			} catch (SQLException e) {
				System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				System.out.println("Connexion fermée avec succès.");
			} catch (SQLException e) {
				System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}
}