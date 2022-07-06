package sarry20.voltionstaff.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseCreation {
	public static boolean createTables(Connection connection) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("CREATE TABLE IF NOT EXISTS `Player` (\n"
							+ "	`UUID` VARCHAR(80) NOT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`Nombre` VARCHAR(40) NOT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`DieInv` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`DieArmor` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci'\n"
							+ ") COLLATE='utf8mb4_general_ci'ENGINE=InnoDB;");
			statement.execute();
			
			PreparedStatement statement2 = connection
					.prepareStatement("CREATE TABLE IF NOT EXISTS `Staff` (\n"
							+ "	`UUID` VARCHAR(80) NOT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`Nombre` VARCHAR(40) NOT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`NormalInv` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`ArmorNormal` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`LeaveInv` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`ArmorLeave` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`DieInv` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',\n"
							+ "	`DieArmor` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci'"+")\n"
							+ "COLLATE='utf8mb4_general_ci' ENGINE=InnoDB;");
			statement2.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
