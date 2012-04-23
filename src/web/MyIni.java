package web;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import app.Pair;

public class MyIni {
	private static Ini ini;
	private static String file;
	public static void open(String pfile) {
		file = pfile;
		try {
			ini = new Ini(new File(file));
		} catch (Exception e) {
			ini = new Ini();
			try {
				ini.store(new File(file));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
	}
	
	public static void load() {
		try {
			ini.load(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void store() {
		try {
			ini.store(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Pair<String,String> get(String user) {
		//load();
		Section s = ini.get(user);
		if (s==null)
			return null;
		String pass = s.get("pass");
		String email = s.get("email");
		//TODO decriptare pass
		return new Pair<String, String>(email, pass);
	}
	
	public static void put(String user, String email, String pass) {
		try {
			Section s = ini.get(user);
			if (s==null)
				s = ini.add(user);
			//TODO criptare pass
			s.put("email", email);
			s.put("pass", pass);
			store();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		MyIni.open("file.ini");
		Pair<String,String> p = MyIni.get("user");
		if (p!=null) {
			System.out.println(p.getK());
			System.out.println(p.getV());
		}
		MyIni.put("user", "abc@mail.com", "pa$$2");
		Pair<String,String> p1 = MyIni.get("user");
		if (p1!=null) {
			System.out.println(p1.getK());
			System.out.println(p1.getV());
		}
	}
	
}
