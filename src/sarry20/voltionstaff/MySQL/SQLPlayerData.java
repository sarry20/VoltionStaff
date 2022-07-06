package sarry20.voltionstaff.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import sarry20.voltionstaff.util.Convert;


public class SQLPlayerData {

	public static boolean staffExist(Connection connection, UUID uuid) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Staff WHERE (UUID=?)");
			statement.setString(1, uuid.toString());
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void createStaff(Connection connection, UUID uuid, String name) {
		String gap = "";
		try {
			if (!staffExist(connection, uuid)) {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO Staff VALUE(?,?,?,?,?,?,?,?,?)");
				statement.setString(1, uuid.toString());
				statement.setString(2, name);
				statement.setString(3, gap);
				statement.setString(4, gap);
				statement.setString(5, gap);
				statement.setString(6, gap);
				statement.setString(7, gap);
				statement.setString(8, gap);
				statement.setBoolean(9, false);

				statement.executeUpdate();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean playerExist(Connection connection, UUID uuid) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Player WHERE (UUID=?)");
			statement.setString(1, uuid.toString());
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void createPlayer(Connection connection, UUID uuid, String name) {
		String gap = "";
		try {
			if (!playerExist(connection, uuid)) {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO Player VALUE(?,?,?,?)");
				statement.setString(1, uuid.toString());
				statement.setString(2, name);
				statement.setString(3, gap);
				statement.setString(4, gap);



				statement.executeUpdate();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void setInventory(Connection connection, UUID uuid, int number, ItemStack[] items,ItemStack[] armor) {
		String a = "";
		try {
			switch (number) {
			case 1:
				
//				Bukkit.getConsoleSender().sendMessage(items_1.length + "");
				a = Convert.ConvertItemStack2ByteArray(items);
				
				PreparedStatement statement1 = connection
						.prepareStatement("UPDATE Staff SET NormalInv = ? WHERE (UUID = ?)");
				statement1.setString(1, a);
				statement1.setString(2, uuid.toString());
				statement1.execute();
				break;
			case 2:
				a = Convert.ConvertItemStack2ByteArray(items);

				PreparedStatement statement2 = connection
						.prepareStatement("UPDATE Staff SET LeaveInv = ? WHERE (UUID = ?)");
				statement2.setObject(1, a);
				statement2.setString(2, uuid.toString());
				statement2.execute();
				break;
			case 3:
				a = Convert.ConvertItemStack2ByteArray(items);

				PreparedStatement statement3 = connection
						.prepareStatement("UPDATE Player SET DieInv = ? WHERE (UUID = ?)");
				statement3.setObject(1, a);
				statement3.setString(2, uuid.toString());
				statement3.execute();
				break;
			case 4:
				a = Convert.ConvertItemStack2ByteArray(armor);

				PreparedStatement statement4 = connection
						.prepareStatement("UPDATE Staff SET ArmorNormal = ? WHERE (UUID = ?)");
				statement4.setObject(1, a);
				statement4.setString(2, uuid.toString());
				statement4.execute();
				break;
			case 5:
				a = Convert.ConvertItemStack2ByteArray(armor);

				PreparedStatement statement5 = connection
						.prepareStatement("UPDATE Staff SET ArmorLeave = ? WHERE (UUID = ?)");
				statement5.setObject(1, a);
				statement5.setString(2, uuid.toString());
				statement5.execute();
				break;
			case 6:
				a = Convert.ConvertItemStack2ByteArray(armor);

				PreparedStatement statement6 = connection
						.prepareStatement("UPDATE Player SET DieArmor = ? WHERE (UUID = ?)");
				statement6.setObject(1, a);
				statement6.setString(2, uuid.toString());
				statement6.execute();
				break;
			}
			
//			PreparedStatement statement = connection.prepareStatement("UPDATE Player SET EnderChest_1 = ?, EnderChest_2 = ?, EnderChest_3 = ?, EnderChest_4 = ? WHERE (UUID = ?)");
//			statement.setString(1, uuid.toString());
//			statement.executeQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static ItemStack[] getInventory(Connection connection, UUID uuid, int number) {
		String devolver = "";
		ItemStack[] stack = null;
		try {
			switch (number) {
			case 1:
				PreparedStatement statement1 = connection
						.prepareStatement("SELECT * FROM Staff WHERE (UUID = ?)");
				statement1.setString(1, uuid.toString());
				ResultSet resultado1 = statement1.executeQuery();
				
				if(resultado1.next()) {
					devolver = resultado1.getString("NormalInv").toString();
					//Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"--AL LEER: " + devolver + "$$");
					//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"-----");

				}
				break;

			case 2:
				PreparedStatement statement2 = connection
						.prepareStatement("SELECT * FROM Staff WHERE (UUID = ?)");
				statement2.setString(1, uuid.toString());
				ResultSet resultado2 = statement2.executeQuery();
				if(resultado2.next()) {
					devolver = resultado2.getString("LeaveInv").toString();

				}				
				break;
			case 3:
				PreparedStatement statement3 = connection
						.prepareStatement("SELECT * FROM Player WHERE (UUID = ?)");
				statement3.setString(1, uuid.toString());
				ResultSet resultado3 = statement3.executeQuery();
				if(resultado3.next()) {
					devolver = resultado3.getString("DieInv").toString();

				}				
				break;
			

		case 4:
			PreparedStatement statement4 = connection
					.prepareStatement("SELECT * FROM Staff WHERE (UUID = ?)");
			statement4.setString(1, uuid.toString());
			ResultSet resultado4 = statement4.executeQuery();
			if(resultado4.next()) {
				devolver = resultado4.getString("ArmorNormal").toString();

			}				
			break;
		case 5:
			PreparedStatement statement5 = connection
					.prepareStatement("SELECT * FROM Staff WHERE (UUID = ?)");
			statement5.setString(1, uuid.toString());
			ResultSet resultado5 = statement5.executeQuery();
			if(resultado5.next()) {
				devolver = resultado5.getString("ArmorLeave").toString();

			}				
			break;
		case 6:
			PreparedStatement statement6 = connection
					.prepareStatement("SELECT * FROM Player WHERE (UUID = ?)");
			statement6.setString(1, uuid.toString());
			ResultSet resultado6 = statement6.executeQuery();
			if(resultado6.next()) {
				devolver = resultado6.getString("DieArmor").toString();

			}				
			break;
			}
			stack = Convert.ConvertByteArray2ItemStack(devolver);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return stack;
	}
	public static Boolean getStopValue(Connection connection, UUID uuid) {
		Boolean devolver = false;
		try {
				PreparedStatement statement1 = connection
						.prepareStatement("SELECT * FROM Staff WHERE (UUID = ?)");
				statement1.setString(1, uuid.toString());
				ResultSet resultado1 = statement1.executeQuery();
				
				if(resultado1.next()) {
					devolver = resultado1.getBoolean(8);
				}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return devolver;
	}
	
	public static void setStopValue(Connection connection, UUID uuid,Boolean stop ) {
		try {
				PreparedStatement statement1 = connection
						.prepareStatement("UPDATE Staff SET HasStop = ? WHERE (UUID = ?)");
				statement1.setBoolean(1, stop);
				statement1.setString(2, uuid.toString());
				statement1.execute();
				
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
