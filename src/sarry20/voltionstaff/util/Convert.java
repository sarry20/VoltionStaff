package sarry20.voltionstaff.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class Convert {

	public static String ConvertItemStack2ByteArray(ItemStack[] items_1) {
		String a = "";
		
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			BukkitObjectOutputStream data = new BukkitObjectOutputStream(stream);
			
			data.writeInt(items_1.length);
			
			for(int i = 0; i < items_1.length; i++) {
				data.writeObject(items_1[i]);
			}
			
			data.close();
			
			a = Base64Coder.encodeLines(stream.toByteArray());
			}catch(IOException e) {
				System.out.print("FATAL ERROR!");
				e.printStackTrace();
			}
		return a;
	}
	public static ItemStack[] ConvertByteArray2ItemStack(String devolver) {
		try {			
//			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"--DESPUES DE ENTRAR: " + devolver + "$$");
//			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"-----");

			ByteArrayInputStream stream = new ByteArrayInputStream(Base64Coder.decodeLines(devolver));
			BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
			ItemStack[] stack = new ItemStack[data.readInt()];
			
			
			for(int i = 0; i < stack.length; i++) {
				stack[i] = (ItemStack) data.readObject();
				
//				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"--inv " + i + " :" + stack[i] + "$$");
//				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"-----");
			}
			
			data.close();

			return stack;
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(EOFException e) {
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	
	}

}
